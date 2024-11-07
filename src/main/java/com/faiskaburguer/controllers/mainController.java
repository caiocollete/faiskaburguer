package com.faiskaburguer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class mainController {
    @FXML
    protected void ControlePedido(ActionEvent event) throws IOException {
        // Carrega o novo FXML
        Parent novaTela = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/cadastros/ctrlPedido.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(novaTela);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Pedidos");
        stage.setScene(novaTelaScene);
        stage.show();
    }

    @FXML
    protected void AnotarProdutos(ActionEvent event) throws IOException {
        // Carrega o novo FXML
        Parent novaTelaRoot = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/cadastros/anotarProduto.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(novaTelaRoot);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Produtos");
        stage.setScene(novaTelaScene);
        stage.show();
    }

    public void CadastrarEmpresa(ActionEvent actionEvent) throws IOException {
        // Carrega o novo FXML
        Parent novaTelaRoot = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/cadastros/cadEmpresa.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(novaTelaRoot);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Empresa");
        stage.setScene(novaTelaScene);
        stage.show();
    }

    public void CadastrarTipoPagamento(ActionEvent actionEvent) throws IOException {
        // Carrega o novo FXML
        Parent novaTelaRoot = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/cadastros/cadTipoPgto.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(novaTelaRoot);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Tipo de Pagamento");
        stage.setScene(novaTelaScene);
        stage.show();
    }

    public void CadastrarCategoria(ActionEvent actionEvent) throws IOException {
        // Carrega o novo FXML
        Parent novaTelaRoot = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/cadastros/cadCategoria.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(novaTelaRoot);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Categoria");
        stage.setScene(novaTelaScene);
        stage.show();
    }

    public void gerenciarProdutos(ActionEvent actionEvent) throws IOException {
        // Carrega o novo FXML
        Parent novaTelaRoot = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/gerenciadores/gerenciarProdutos.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(novaTelaRoot);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = new Stage();
        stage.setTitle("Gerenciamento de Produtos");
        stage.setScene(novaTelaScene);
        stage.show();
    }

    @FXML
    protected void RelatorioPedidos(ActionEvent event) throws IOException {
        // Carrega o novo FXML
        Parent novaTelaRoot = FXMLLoader.load(getClass().getResource("/com/faiskaburguer/relatorios/relPedidos.fxml"));

        // Cria a nova cena
        Scene novaTelaScene = new Scene(novaTelaRoot);

        // Obtém o Stage atual (janela) e configura a nova cena
        Stage stage = new Stage();
        stage.setTitle("Relatório de Pedidos");
        stage.setScene(novaTelaScene);
        stage.show();
    }


    public void Ajuda(ActionEvent actionEvent) {
        //ImageView imageView = new ImageView(new Image(getClass().getResource("/com/faiskaburguer/img/faiskaburguer_logo.png").toExternalForm()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre");
        alert.setHeaderText("Faiska Burguer");
        //alert.setGraphic(imageView);
        alert.setContentText("Desenvolvido por Caio Collete\n");
        alert.show();
    }
}