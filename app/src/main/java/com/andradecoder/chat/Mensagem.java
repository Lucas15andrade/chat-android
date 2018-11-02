package com.andradecoder.chat;

import java.util.Objects;

public class Mensagem {
    private String conteudo;
    private String nome;
    private String data;

    public Mensagem(String conteudo, String nome, String data) {
        this.conteudo = conteudo;
        this.nome = nome;
        this.data = data;
    }

    public Mensagem() {
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return Objects.equals(conteudo, mensagem.conteudo) &&
                Objects.equals(nome, mensagem.nome) &&
                Objects.equals(data, mensagem.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(conteudo, nome, data);
    }
}
