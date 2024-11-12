package com.faiskaburguer.controllers.gerenciadores;

import com.faiskaburguer.controllers.gerenciadores.editores.EditarPedido;
import com.faiskaburguer.controllers.gerenciadores.editores.EditarTipoPgto;
import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.dal.TipoPagamentoDAL;
import com.faiskaburguer.db.entidade.Pedido;
import com.faiskaburguer.db.entidade.TipoPagamento;
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

public class gerenciarTipoPgto {

    @FXML
    private TextField filtro;
    @FXML
    private TableView<TipoPagamento> tabelaTipoPgto; // Adicione o TableView no FXML
    @FXML
    private TableColumn<TipoPagamento, String> tipopgtoNome_column;

    private ObservableList<TipoPagamento> TipoPgtoShowing;

    @FXML
    protected void initialize() {
        initTable();
        Atualizar(new ActionEvent());
    }

    @FXML
    protected void Pesquisar(ActionEvent actionEvent) {
        TipoPagamentoDAL tipoPagamentoDAL = new TipoPagamentoDAL();
        if (!filtro.getText().isEmpty()){
            atualizaListaPedido(tipoPagamentoDAL.get("cat_nome iLIKE"+"'%" + filtro.getText() + "%'"));
        }
    }

    @FXML
    protected void Atualizar(ActionEvent actionEvent) {
        TipoPagamentoDAL tipoPagamentoDAL = new TipoPagamentoDAL();
        atualizaListaPedido(tipoPagamentoDAL.get(""));

    }



    private void initTable() {
        tipopgtoNome_column.setCellValueFactory(new PropertyValueFactory<>("nome"));

    }
    private void atualizaListaPedido(List<TipoPagamento> tipoPagamentoList) {
        TipoPgtoShowing = FXCollections.observableArrayList(tipoPagamentoList);
        tabelaTipoPgto.setItems(TipoPgtoShowing);
    }

    @FXML
    protected void Apagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA APAGAR?");
        alert.setContentText("Deseja apagar este tipo de pagamento?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                TipoPagamentoDAL tipoPagamentoDAL = new TipoPagamentoDAL();
                tipoPagamentoDAL.apagar(tabelaTipoPgto.getSelectionModel().getSelectedItem());
            }
        });
        // O produto nao apaga caso tenha sido referenciado em uma tabela Item, estou pensando em adicionar uma flag na tabela produtos e fazer exclusao logica apenas
    }

    @FXML
    protected void Editar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("PERIGO!");
        alert.setHeaderText("TEM CERTEZA QUE DESEJA EDITAR?");
        alert.setContentText("Deseja editar este tipo de pagamento?\n Essa ação é irreversível!");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                FXMLLoader loader;
                Parent novaTela = null;
                try {
                    loader = new FXMLLoader(getClass().getResource("/com/faiskaburguer/gerenciadores/editores/editarTipoPgto.fxml"));
                    loader .setController(new EditarTipoPgto(tabelaTipoPgto.getSelectionModel().getSelectedItem()));
                    novaTela = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Scene novaTelaScene = new Scene(novaTela);
                Stage stage = new Stage();
                stage.setTitle("Editar Tipo de Pagamento");
                stage.setScene(novaTelaScene);
                stage.show();
            }
        });
    }
}
