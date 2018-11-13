package com.andradecoder.chat.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andradecoder.chat.R;

public class MensagemHolder extends RecyclerView.ViewHolder {

    public TextView conteudo;
    public TextView nome;
    public TextView data;
    public ImageView imagem;

    public MensagemHolder(@NonNull View view) {
        super(view);
        conteudo = view.findViewById(R.id.textConteudo);
        nome = view.findViewById(R.id.textNome);
        data = view.findViewById(R.id.textData);
        imagem = view.findViewById(R.id.imagemChat);
    }

    /*
    public TextView getConteudo() {
        return conteudo;
    }

    public void setConteudo(TextView conteudo) {
        this.conteudo = conteudo;
    }

    public TextView getNome() {
        return nome;
    }

    public void setNome(TextView nome) {
        this.nome = nome;
    }

    public TextView getData() {
        return data;
    }

    public void setData(TextView data) {
        this.data = data;
    }
    */
}
