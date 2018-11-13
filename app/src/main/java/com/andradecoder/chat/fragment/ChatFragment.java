package com.andradecoder.chat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.andradecoder.chat.modelo.Mensagem;
import com.andradecoder.chat.R;
import com.andradecoder.chat.adapter.MensagemAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class ChatFragment extends Fragment {

    private final static int CODIGO_LOGAR = 1;

    //Lista de mensagens
    private List<Mensagem> listaMensagens = new ArrayList<>();
    //Objetos da view
    private ImageButton botaoEnviar;
    private EditText conteudo;
    //Objetos do banco de dados e Firebase
    private FirebaseDatabase mFirebase;
    private DatabaseReference mReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //Listenner que ficará ouvindo todos os filhos
    private ChildEventListener childEventListener;

    private String autor;
    String data;
    Date date;

    RecyclerView recylerMensagens;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflando a view para ser retornada
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        //Instanciando objetos do fragment após a view ser inflada.
        botaoEnviar = view.findViewById(R.id.imageButton);
        conteudo = view.findViewById(R.id.editMensagem);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date();
        data = dateFormat.format(date);

        //Instanciando objetos do Firebase
        mFirebase = FirebaseDatabase.getInstance();
        mReference = mFirebase.getReference().child("mensagens");
        mFirebaseAuth = FirebaseAuth.getInstance();

        recylerMensagens = view.findViewById(R.id.recyclerMensagem);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recylerMensagens.setLayoutManager(layoutManager);
        recylerMensagens.setItemAnimator(new DefaultItemAnimator());


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //onSignInInitialize(user.getDisplayName());
                    autor = user.getDisplayName();
                    anexarListener();
                } else {
                    desanexarListener();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(
                                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                                    new AuthUI.IdpConfig.FacebookBuilder().build()
                                            )
                                    )
                                    .build(),
                            CODIGO_LOGAR);
                }

            }
        };

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        /*
        Mensagem m = new Mensagem("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet semper ante. Vestibulum sollicitudin arcu non neque auctor sodales. Nam in convallis risus. Sed hendrerit, lectus vitae finibus pharetra, arcu ante imperdiet lectus, non tempor nulla turpis nec metus. Etiam sed sem augue. Quisque in consectetur quam. Donec volutpat at sapien ut malesuada. Duis tincidunt posuere ipsum, vel semper ex. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam cursus sit amet dui in viverra. Quisque non mauris vehicula, viverra turpis vel, ornare nisi. Etiam ligula lacus, feugiat lacinia odio sit amet, commodo rhoncus nunc. Integer blandit consequat ipsum, sit amet interdum lorem faucibus id. Fusce rhoncus ac augue ac accumsan. Ut feugiat nulla sit amet blandit volutpat. Suspendisse vehicula felis sed nibh ultricies, at laoreet sapien imperdiet. ","Lucas Andrade","01/11/2018");
        Mensagem m2 = new Mensagem("Pellentesque pretium mi molestie diam suscipit, vel accumsan neque suscipit. Donec posuere porta leo, at ullamcorper enim imperdiet in. Duis maximus eros sed lacinia congue. Phasellus auctor vitae augue sit amet venenatis. Cras sed neque eget justo ullamcorper hendrerit sed vitae dui. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed vehicula dolor a euismod accumsan. Morbi vel tincidunt dui, sit amet luctus augue. Suspendisse pharetra aliquam sodales. Sed lectus nisi, placerat sit amet augue sed, posuere ornare lectus. Integer non rhoncus orci. Pellentesque semper aliquet dignissim. ","Taniro","01/1/2018");
        listaMensagens.add(m);
        listaMensagens.add(m2);
        MensagemAdapter mensagemAdapter = new MensagemAdapter(getContext(), listaMensagens);
        recylerMensagens.setAdapter(mensagemAdapter);
        */

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TESTE", "Clicou no botão de enviar");
                Mensagem mensagem = new Mensagem(conteudo.getText().toString(), null, autor, data);
                mReference.push().setValue(mensagem);
                conteudo.setText("");
            }
        });


        return view;
    }

    private void anexarListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Mensagem mensagem = dataSnapshot.getValue(Mensagem.class);
                    listaMensagens.add(mensagem);
                    MensagemAdapter mensagemAdapter = new MensagemAdapter(getContext(), listaMensagens);
                    recylerMensagens.setAdapter(mensagemAdapter);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
        }


        mReference.addChildEventListener(childEventListener);
    }

    private void desanexarListener() {
        if (childEventListener != null) {
            mReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_LOGAR) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Logado com sucesso!", Toast.LENGTH_SHORT).show();
            } else if (requestCode == RESULT_CANCELED) {

            }
        }


    }
}
