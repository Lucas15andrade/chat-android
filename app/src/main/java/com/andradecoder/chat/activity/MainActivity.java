package com.andradecoder.chat.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.andradecoder.chat.R;
import com.andradecoder.chat.adapter.TabsPageAdapter;
import com.andradecoder.chat.modelo.Mensagem;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//Trabalho desenvolvido por @AndradeCoder na disciplina de Programação para dispositivos móveis
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private FirebaseDatabase mFirebase;
    private FirebaseStorage mStorage;
    private StorageReference storageReference;
    private DatabaseReference mReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PERMISSAO = 5;

    File fotoTeste = null;

    String dataMensagem;
    Date date;

    final String[] permissoes = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private String fotoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date();
        dataMensagem = dateFormat.format(date);

        Toolbar toolbar = findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter page = new TabsPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(page);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_black_24dp);
        tabLayout.getTabAt(1).select();
        //tabLayout.getTabAt(1).getText().c


        //Autenticação com o Firebase
        mFirebase = FirebaseDatabase.getInstance();
        mReference = mFirebase.getReference().child("mensagens");

        mStorage = FirebaseStorage.getInstance();
        storageReference = mStorage.getReference().child("chat-fotos");

        /*
        if (ContextCompat.checkSelfPermission(this, permissoes[0]) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, permissoes, PERMISSAO);
        }*/


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSAO);
            }
        }



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_background_24dp);
                    capturarFoto();
                    Log.i("TESTE", "TAB CAMERA");
                } else {
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_black_24dp);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.sair:
                Log.i("TESTE", "Clicou em sair!");
                AuthUI.getInstance().signOut(this);
                return true;
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void capturarFoto() {

        Intent capturarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (capturarFoto.resolveActivity(getPackageManager()) != null) {

            File arquivoFoto = null;

            try {
                arquivoFoto = criarArquivoFoto();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (arquivoFoto != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getApplicationContext().getPackageName() + ".provider",
                        arquivoFoto);
                capturarFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(capturarFoto, REQUEST_TAKE_PHOTO);
                //StartActivity caso seja um thubmnail
                //startActivityForResult(capturarFoto,REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    private File criarArquivoFoto() throws IOException {
        String dataFoto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String arquivoImagemNome = "JPGE_" + dataFoto + "_";

        File arquivo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File pasta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        /*File imagem = File.createTempFile(
          arquivoImagemNome,
          ".jpg",
          arquivo
        );*/

        File imagem = new File(arquivo.getPath() + File.separator
                + "JPG_" + dataFoto + ".jpg");


        fotoAtual = imagem.getAbsolutePath();
        fotoTeste = imagem;

        return imagem;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                /*
                Bundle extras = data.getExtras();
                Bitmap imagem = (Bitmap) extras.get("dataMensagem");
                Log.i("TESTE", "Entrou no result");
                */
                //Recebo a imagem. Tratá-la

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String nomeUsuario = user.getDisplayName();

                //final Uri imagemSelecionada = data.getData();
                //Log.i("TESTE", imagemSelecionada.toString());

                File f = new File(fotoAtual);
                final Uri imagemSelecionada = Uri.fromFile(fotoTeste);
                Log.i("FOTO", "uri 1: "+imagemSelecionada.toString());
                StorageReference referenciaFoto = storageReference.child(nomeUsuario + "_" + dataMensagem);
                Log.i("FOTO","uri 2: "+imagemSelecionada.toString());
                Log.i("FOTO","Storage Reference: "+ referenciaFoto.toString());

                referenciaFoto.putFile(imagemSelecionada).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Log.i("FOTO","Caiu no primeiro sucesso");

                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Mensagem mensagem = new Mensagem(null, uri.toString(), nomeUsuario, dataMensagem);
                                mReference.push().setValue(mensagem);
                                Log.i("FOTO", "Caiu no segundo sucesso");
                            }
                        });
                    }
                });

            }

        }
        tabLayout.getTabAt(1).select();
    }


    //
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(mAuthStateListener != null)
//            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//    }
}
