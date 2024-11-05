package com.faiskaburguer.controllers.relatorios;

import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.entidade.Pedido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class relPedidos {

    @FXML
    private ListView<String> pedidosSistema;
    @FXML
    private ListView<String> produtosPedido;
    @FXML
    private TextField nome;
    @FXML
    private TextField telefone;
    @FXML
    private TextField rua;
    @FXML
    private TextField numero;
    @FXML
    private TextField cidade;
    @FXML
    private TextField uf;
    @FXML
    private TextField bairro;

    private List<Pedido> pedidoList;


    @FXML
    protected void initialize() {
        pedidoList = new PedidoDAL().get("");
        for(Pedido p : pedidoList){
            pedidosSistema.getItems().add(p.getClinome() + " - " + p.getData());
        }
    }

    public void BuscarPedidos(ActionEvent event) {
    }
}
