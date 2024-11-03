package com.faiskaburguer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class mainController {
    @FXML
    protected void ControlePedido(ActionEvent event) throws IOException {
        // Carrega o novo FXML
        Parent ctrlPedTela = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/ctrlPedido.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(ctrlPedTela);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Controle de Pedidos");
        stage.setScene(novaTelaScene);
        stage.show();
    }

    @FXML
    protected void AnotarProdutos(ActionEvent event) throws IOException {
        // Carrega o novo FXML
        Parent novaTelaRoot = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/anotarProduto.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(novaTelaRoot);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Controle de Pedidos");
        stage.setScene(novaTelaScene);
        stage.show();
    }

}