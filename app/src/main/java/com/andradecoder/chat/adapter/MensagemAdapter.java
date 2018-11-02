package com.andradecoder.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andradecoder.chat.modelo.Mensagem;
import com.andradecoder.chat.R;
import com.andradecoder.chat.holder.MensagemHolder;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MensagemHolder holder = (MensagemHolder) viewHolder;
        mensagem = listaMensagens.get(i);

        holder.nome.setText(mensagem.getNome());
        holder.conteudo.setText(mensagem.getConteudo());
        holder.data.setText(mensagem.getData());


    }

    @Override
    public int getItemCount() {
        return listaMensagens == null ? 0 : listaMensagens.size();
    }
}
