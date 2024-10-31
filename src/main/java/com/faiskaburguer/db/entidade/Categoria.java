package com.faiskaburguer.db.entidade;

public class Categoria {
    private String nome;
    private int id;

    public Categoria(int id, String nome){
        this.id=id;
        this.nome=nome;
    }
    public Categoria(int id){
        this.id=id;
        this.nome="";
    }

    public Categoria(String nome) {
        this.nome = nome;
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

    @Override
    public String toString() {
        return nome;
    }
}
