package com.faiskaburguer.controllers.gerenciadores;

import com.faiskaburguer.controllers.gerenciadores.editores.EditarEmpresa;
import com.faiskaburguer.db.dal.EmpresaDAL;
import com.faiskaburguer.db.entidade.Empresa;
import com.faiskaburguer.db.util.Mascaras;
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

import static com.faiskaburguer.db.util.Mascaras.mascCnpj;

public class gerenciarEmpresa {

    @FXML
    private TextField filtro;
    @FXML
    private TableView<Empresa> tabelaEmpresa;
    @FXML
    private TableColumn<Empresa, String> razao_column;
    @FXML
    private TableColumn<Empresa, String> telefone_column;
    @FXML
    private TableColumn<Empresa, String> email_column;
    @FXML
    private TableColumn<Empresa, String> cnpj_column;
    @FXML
    private TableColumn<Empresa, String> valor_column;
    @FXML
    private SplitMenuButton opSearch;

    private ObservableList<Empresa> empresaShowing;

    @FXML
    protected void initialize() {
        initTable();
        opSearch.getItems().get(0).setOnAction(event -> opSearch.setText("CNPJ"));
        opSearch.getItems().get(1).setOnAction(event -> opSearch.setText("Razão Social"));
        Atualizar(new ActionEvent());
    }

    @FXML
    protected void Pesquisar(ActionEvent actionEvent) {
        EmpresaDAL empresaDAL = new EmpresaDAL();
        if (!filtro.getText().isEmpty()){
            if(opSearch.getText().equals("CNPJ")){
                atualizaListaPedido(empresaDAL.get("emp_cnpj="+"'"+ Mascaras.mascCnpj(filtro.getText()) +"'"));
            }
            else atualizaListaPedido(empresaDAL.get("emp_razao iLIKE"+"'%" + filtro.getText() + "%'"));
        }
    }

    @FXML
    protected void Atualizar(ActionEvent actionEvent) {
        EmpresaDAL empresaDAL = new EmpresaDAL();
        atualizaListaPedido(empresaDAL.get(""));

    }


    private void initTable() {
        razao_column.setCellValueFactory(new PropertyValueFactory<>("emp_razao"));
        telefone_column.setCellValueFactory(new PropertyValueFactory<>("emp_fone"));
        email_column.setCellValueFactory(new PropertyValueFactory<>("emp_email"));
        valor_column.setCellValueFactory(new PropertyValueFactory<>("emp_vlremb"));
        cnpj_column.setCellValueFactory(new PropertyValueFactory<>("emp_cnpj"));
    }
    private void atualizaListaPedido(List<Empresa> empresaList) {
        empresaShowing = FXCollections.observableArrayList(empresaList);
        tabelaEmpresa.setItems(empresaShowing);
    }

    @FXML
    protected void Apagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA APAGAR?");
        alert.setContentText("Deseja apagar este empresa?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                EmpresaDAL empresaDAL = new EmpresaDAL();
                empresaDAL.apagar(tabelaEmpresa.getSelectionModel().getSelectedItem());
            }
        });
    }

    @FXML
    protected void Editar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA EDITAR?");
        alert.setContentText("Deseja editar esta empresa?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                FXMLLoader loader;
                Parent novaTela = null;
                try {
                    loader = new FXMLLoader(getClass().getResource("/com/faiskaburguer/gerenciadores/editores/editarEmpresa.fxml"));
                    loader.setController(new EditarEmpresa(tabelaEmpresa.getSelectionModel().getSelectedItem()));
                    novaTela = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Scene novaTelaScene = new Scene(novaTela);
                Stage stage = new Stage();
                stage.setTitle("Editar Empresa");
                stage.setScene(novaTelaScene);
                stage.show();
            }
        });
    }
}
