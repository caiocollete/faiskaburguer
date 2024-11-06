package com.faiskaburguer.controllers.gerenciadores;

import com.faiskaburguer.db.dal.ProdutoDAL;
import com.faiskaburguer.db.entidade.Produtos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class gerenciarProdutos {

    @FXML
    private TextField filtro;
    @FXML
    private TableView<Produtos> tabelaProdutos; // Adicione o TableView no FXML
    @FXML
    private TableColumn<Produtos, String> nome_column;
    @FXML
    private TableColumn<Produtos, String> cat_column;
    @FXML
    private TableColumn<Produtos, String> valor_column;

    private ObservableList<Produtos> produtosShowing;

    @FXML
    protected void initialize() {
        initTable();
        Pesquisar(new ActionEvent());
    }

    @FXML
    protected void Pesquisar(ActionEvent actionEvent) {
        ProdutoDAL produtoDAL = new ProdutoDAL();
        if (!filtro.getText().isEmpty()){
            this.produtosShowing = FXCollections.observableArrayList(produtoDAL.get("pro_nome="+"'" + filtro.getText() + "'"));
        }

        else
            this.produtosShowing = FXCollections.observableArrayList(produtoDAL.get(""));

        atualizaListaProdutos();
    }


    private void initTable() {
        nome_column.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cat_column.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        valor_column.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }
    private void atualizaListaProdutos() {
        tabelaProdutos.setItems(produtosShowing);
    }

    @FXML
    protected void Apagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA APAGAR?");
        alert.setContentText("Deseja apagar este produto?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                ProdutoDAL produtoDAL = new ProdutoDAL();
                produtoDAL.apagar(tabelaProdutos.getSelectionModel().getSelectedItem());
            }
        });
        // O produto nao apaga caso tenha sido referenciado em uma tabela Item, estou pensando em adicionar uma flag na tabela produtos e fazer exclusao logica apenas
    }

    @FXML
    protected void Editar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA EDITAR?");
        alert.setContentText("Deseja editar este produto?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                System.out.println("Implementar");
            }
        });
    }
}
