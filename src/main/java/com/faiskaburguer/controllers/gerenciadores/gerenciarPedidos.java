package com.faiskaburguer.controllers.gerenciadores;

import com.faiskaburguer.controllers.gerenciadores.editores.EditarPedido;
import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.entidade.Pedido;
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
import java.util.Date;
import java.util.List;

public class gerenciarPedidos {

    @FXML
    private TextField filtro;
    @FXML
    private TableView<Pedido> tabelaPedidos; // Adicione o TableView no FXML
    @FXML
    private TableColumn<Pedido, String> cliente_column;
    @FXML
    private TableColumn<Pedido, String> telefone_column;
    @FXML
    private TableColumn<Pedido, String> viagem_column;
    @FXML
    private TableColumn<Pedido, Date> data_column;
    @FXML
    private TableColumn<Pedido, String> valor_column;

    private ObservableList<Pedido> pedidosShowing;

    @FXML
    protected void initialize() {
        initTable();
        Atualizar(new ActionEvent());
    }

    @FXML
    protected void Pesquisar(ActionEvent actionEvent) {
        PedidoDAL pedidoDAL = new PedidoDAL();
        if (!filtro.getText().isEmpty()){
            atualizaListaPedido(pedidoDAL.get("ped_clinome iLIKE"+"'%" + filtro.getText() + "%'"));
        }
    }

    @FXML
    protected void Atualizar(ActionEvent actionEvent) {
        PedidoDAL pedidoDAL = new PedidoDAL();
        atualizaListaPedido(pedidoDAL.get(""));

    }


    private void initTable() {
        cliente_column.setCellValueFactory(new PropertyValueFactory<>("clinome"));
        telefone_column.setCellValueFactory(new PropertyValueFactory<>("clifone"));
        viagem_column.setCellValueFactory(new PropertyValueFactory<>("viagem"));
        valor_column.setCellValueFactory(new PropertyValueFactory<>("total"));
        data_column.setCellValueFactory(new PropertyValueFactory<>("data"));
    }
    private void atualizaListaPedido(List<Pedido> pedidoList) {
        pedidosShowing = FXCollections.observableArrayList(pedidoList);
        tabelaPedidos.setItems(pedidosShowing);
    }

    @FXML
    protected void Apagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA APAGAR?");
        alert.setContentText("Deseja apagar este pedido?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                PedidoDAL pedidoDAL = new PedidoDAL();
                pedidoDAL.apagar(tabelaPedidos.getSelectionModel().getSelectedItem());
            }
        });
        // O produto nao apaga caso tenha sido referenciado em uma tabela Item, estou pensando em adicionar uma flag na tabela produtos e fazer exclusao logica apenas
    }

    @FXML
    protected void Editar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA EDITAR?");
        alert.setContentText("Deseja editar este pedido?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                FXMLLoader loader;
                Parent novaTela = null;
                try {
                    loader = new FXMLLoader(getClass().getResource("/com/faiskaburguer/gerenciadores/editores/editarPedido.fxml"));
                    loader .setController(new EditarPedido(tabelaPedidos.getSelectionModel().getSelectedItem()));
                    novaTela = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Scene novaTelaScene = new Scene(novaTela);
                Stage stage = new Stage();
                stage.setTitle("Editar Pedido");
                stage.setScene(novaTelaScene);
                stage.show();
            }
        });
    }
}
