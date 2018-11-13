package com.andradecoder.chat.modelo;

import java.util.Objects;

public class Mensagem {
    private String conteudo;
    private String urlFoto;
    private String nome;
    private String data;

    public Mensagem() {
    }

    public Mensagem(String conteudo, String urlFoto, String nome, String data) {
        this.conteudo = conteudo;
        this.urlFoto = urlFoto;
        this.nome = nome;
        this.data = data;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
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
                Objects.equals(urlFoto, mensagem.urlFoto) &&
                Objects.equals(nome, mensagem.nome) &&
                Objects.equals(data, mensagem.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(conteudo, urlFoto, nome, data);
    }
}
