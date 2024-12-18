package com.faiskaburguer.controllers.gerenciadores;

import com.faiskaburguer.controllers.gerenciadores.editores.EditarProduto;
import com.faiskaburguer.db.dal.ProdutoDAL;
import com.faiskaburguer.db.entidade.Produtos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
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
        Atualizar(new ActionEvent());
    }

    @FXML
    protected void Pesquisar(ActionEvent actionEvent) {
        ProdutoDAL produtoDAL = new ProdutoDAL();
        if (!filtro.getText().isEmpty()){
            atualizaListaProdutos(produtoDAL.get("pro_nome iLIKE"+"'%" + filtro.getText() + "%'"));
        }
    }

    @FXML
    protected void Atualizar(ActionEvent actionEvent) {
        ProdutoDAL produtoDAL = new ProdutoDAL();
        atualizaListaProdutos(produtoDAL.get(""));

    }


    private void initTable() {
        nome_column.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cat_column.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        valor_column.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }
    private void atualizaListaProdutos(List<Produtos> produtosList) {
        produtosShowing = FXCollections.observableArrayList(produtosList);
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
                FXMLLoader loader;
                Parent novaTela = null;
                try {
                    loader = new FXMLLoader(getClass().getResource("/com/faiskaburguer/gerenciadores/editores/editarProduto.fxml"));
                    loader .setController(new EditarProduto(tabelaProdutos.getSelectionModel().getSelectedItem()));
                    novaTela = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Scene novaTelaScene = new Scene(novaTela);
                Stage stage = new Stage();
                stage.setTitle("Editar Produto");
                stage.setScene(novaTelaScene);
                stage.show();
            }
        });
    }
}
