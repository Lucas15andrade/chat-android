package com.andradecoder.chat.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andradecoder.chat.modelo.Mensagem;
import com.andradecoder.chat.R;
import com.andradecoder.chat.adapter.MensagemAdapter;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    List<Mensagem> listaMensagens = new ArrayList<>();

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflando a view para ser retornada
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recylerMensagens = view.findViewById(R.id.recyclerMensagem);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recylerMensagens.setLayoutManager(layoutManager);
        recylerMensagens.setItemAnimator(new DefaultItemAnimator());

        Mensagem m = new Mensagem("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet semper ante. Vestibulum sollicitudin arcu non neque auctor sodales. Nam in convallis risus. Sed hendrerit, lectus vitae finibus pharetra, arcu ante imperdiet lectus, non tempor nulla turpis nec metus. Etiam sed sem augue. Quisque in consectetur quam. Donec volutpat at sapien ut malesuada. Duis tincidunt posuere ipsum, vel semper ex. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam cursus sit amet dui in viverra. Quisque non mauris vehicula, viverra turpis vel, ornare nisi. Etiam ligula lacus, feugiat lacinia odio sit amet, commodo rhoncus nunc. Integer blandit consequat ipsum, sit amet interdum lorem faucibus id. Fusce rhoncus ac augue ac accumsan. Ut feugiat nulla sit amet blandit volutpat. Suspendisse vehicula felis sed nibh ultricies, at laoreet sapien imperdiet. ","Lucas Andrade","01/11/2018");
        Mensagem m2 = new Mensagem("Pellentesque pretium mi molestie diam suscipit, vel accumsan neque suscipit. Donec posuere porta leo, at ullamcorper enim imperdiet in. Duis maximus eros sed lacinia congue. Phasellus auctor vitae augue sit amet venenatis. Cras sed neque eget justo ullamcorper hendrerit sed vitae dui. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed vehicula dolor a euismod accumsan. Morbi vel tincidunt dui, sit amet luctus augue. Suspendisse pharetra aliquam sodales. Sed lectus nisi, placerat sit amet augue sed, posuere ornare lectus. Integer non rhoncus orci. Pellentesque semper aliquet dignissim. ","Taniro","01/1/2018");
        listaMensagens.add(m);
        listaMensagens.add(m2);
        recylerMensagens.setAdapter(new MensagemAdapter(getContext(), listaMensagens));

        return view;
    }

}
