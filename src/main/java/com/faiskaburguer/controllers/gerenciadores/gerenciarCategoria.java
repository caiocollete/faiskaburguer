package com.faiskaburguer.controllers.gerenciadores;

import com.faiskaburguer.controllers.gerenciadores.editores.EditarCategoria;
import com.faiskaburguer.db.dal.CategoriaDAL;
import com.faiskaburguer.db.entidade.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class gerenciarCategoria {

    @FXML
    private TextField filtro;
    @FXML
    private TableView<Categoria> tabelaCategoria; // Adicione o TableView no FXML
    @FXML
    private TableColumn<Categoria, String> catNome_column;

    private ObservableList<Categoria> catShowing;

    @FXML
    protected void initialize() {
        initTable();
        Atualizar(new ActionEvent());
    }

    @FXML
    protected void Pesquisar(ActionEvent actionEvent) {
        CategoriaDAL categoriaDAL = new CategoriaDAL();
        if (!filtro.getText().isEmpty()){
            atualizaListaCat(categoriaDAL.get("cat_nome iLIKE"+"'%" + filtro.getText() + "%'"));
        }
    }

    @FXML
    protected void Atualizar(ActionEvent actionEvent) {
        CategoriaDAL categoriaDAL = new CategoriaDAL();
        atualizaListaCat(categoriaDAL.get(""));

    }



    private void initTable() {
        catNome_column.setCellValueFactory(new PropertyValueFactory<>("nome"));

    }
    private void atualizaListaCat(List<Categoria> categoriaList) {
        catShowing = FXCollections.observableArrayList(categoriaList);
        tabelaCategoria.setItems(catShowing);
    }

    @FXML
    protected void Apagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA APAGAR?");
        alert.setContentText("Deseja apagar esta Categoria?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                CategoriaDAL categoriaDAL = new CategoriaDAL();
                categoriaDAL.apagar(tabelaCategoria.getSelectionModel().getSelectedItem());
            }
        });
    }

    @FXML
    protected void Editar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA EDITAR?");
        alert.setContentText("Deseja editar esta Categoria?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                FXMLLoader loader;
                Parent novaTela = null;
                try {
                    loader = new FXMLLoader(getClass().getResource("/com/faiskaburguer/gerenciadores/editores/editarCategoria.fxml"));
                    loader .setController(new EditarCategoria(tabelaCategoria.getSelectionModel().getSelectedItem()));
                    novaTela = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Scene novaTelaScene = new Scene(novaTela);
                Stage stage = new Stage();
                stage.setTitle("Editar Categoria");
                stage.setScene(novaTelaScene);
                stage.show();
            }
        });
    }
}
