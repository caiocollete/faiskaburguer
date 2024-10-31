package com.faiskaburguer.db.entidade;

public class TipoPagamento {

    private String nome;
    private int id;

    public TipoPagamento(int id, String nome){
        this.id=id;
        this.nome=nome;
    }
    public TipoPagamento(int id){
        this.id=id;
        this.nome="";
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
