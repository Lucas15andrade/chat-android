package com.andradecoder.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andradecoder.chat.modelo.Mensagem;
import com.andradecoder.chat.R;
import com.andradecoder.chat.holder.MensagemHolder;
import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URL;
import java.util.List;

public class MensagemAdapter extends RecyclerView.Adapter {

    Context contexto;
    List<Mensagem> listaMensagens;
    Mensagem mensagem;

    public MensagemAdapter(Context contexto, List<Mensagem> listaMensagens) {
        this.contexto = contexto;
        this.listaMensagens = listaMensagens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(contexto).inflate(R.layout.layout_mensagem, viewGroup, false);

        MensagemHolder holder = new MensagemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MensagemHolder holder = (MensagemHolder) viewHolder;

        mensagem = listaMensagens.get(i);


        holder.nome.setText(mensagem.getNome());
        holder.conteudo.setText(mensagem.getConteudo());
        holder.data.setText(mensagem.getData());
        //holder.imagem.setImageURI(mensagem.getUrlFoto());

        boolean foto = false;

        if (mensagem.getUrlFoto() != null)
            foto = true;


        if (foto) {
            holder.conteudo.setVisibility(View.GONE);
            holder.imagem.setVisibility(View.VISIBLE);
            Glide.with(holder.imagem.getContext()).load(mensagem.getUrlFoto()).into(holder.imagem);
        } else {

            holder.conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TESTE", "Clicou no conteúdo do adapter");
                    Log.i("TESTE", "Posição: " + i);

                    //String conteudo = holder.conteudo.toString();
                    String conteudo = listaMensagens.get(i).getConteudo();

                    Log.i("TESTE", "Conteudo é: " + conteudo);

                    if (conteudo.contains("http://www.google.com/maps/place/")) {
                        Log.i("TESTE", "É um link do google maps");
                        Uri uri = Uri.parse(conteudo);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listaMensagens == null ? 0 : listaMensagens.size();
    }
}
