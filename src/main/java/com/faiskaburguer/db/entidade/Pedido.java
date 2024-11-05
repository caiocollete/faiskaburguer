package com.faiskaburguer.db.entidade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    public static Pedido.Item Item;

    public record Item(Produtos produtos, int quant, double valor){}
    private int id;
    private LocalDate data;
    private String clinome;
    private String clifone;
    private double total;
    private char viagem; // S || N
    private TipoPagamento tipoPagamento;
    private List<Item> listItens;
    private Endereco endereco;

    public Pedido() {}

    // usa para criar o pedido
    public Pedido(LocalDate data, String clinome, String clifone, double total, char viagem, TipoPagamento tipoPagamento, List<Item> listItens, Endereco endereco) {
        this.data = data;
        this.clinome = clinome;
        this.clifone = clifone;
        this.total = total;
        this.viagem = viagem;
        this.tipoPagamento = tipoPagamento;
        this.listItens = listItens;
        this.endereco = endereco;
    }

    // usa para resgatar o pedido do db
    public Pedido(int id, LocalDate data, String clinome, String clifone, double total, char viagem, TipoPagamento tipoPagamento) {
        this.id = id;
        this.data = data;
        this.clinome = clinome;
        this.clifone = clifone;
        this.total = total;
        this.viagem = viagem;
        this.tipoPagamento = tipoPagamento;
        this.listItens = new ArrayList<>();
    }

    public Pedido(LocalDate now, String nomeCliente, String numeroCliente, Double finalTotalDouble, char viagem, TipoPagamento tipoPagamento, List<Item> listItens) {
        this.data = data;
        this.clinome = clinome;
        this.clifone = clifone;
        this.total = total;
        this.viagem = viagem;
        this.tipoPagamento = tipoPagamento;
        this.listItens = listItens;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getClinome() {
        return clinome;
    }

    public void setClinome(String clinome) {
        this.clinome = clinome;
    }

    public String getClifone() {
        return clifone;
    }

    public void setClifone(String clifone) {
        this.clifone = clifone;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public char getViagem() {
        return viagem;
    }

    public void setViagem(char viagem) {
        this.viagem = viagem;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public boolean addItem(Produtos produto, int quant){
        return listItens.add(new Item(produto,quant, produto.getValor()));
    }

    public List<Item> getItens(){
        return listItens;
    }

    double tot=0;
    public void totalizar(){
        for(Item item: listItens){
            tot+=item.valor()* item.quant();
        }
        this.total=tot;
    }
}
