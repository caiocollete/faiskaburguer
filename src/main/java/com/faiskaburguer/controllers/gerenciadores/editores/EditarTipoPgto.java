package com.faiskaburguer.controllers.gerenciadores.editores;

import com.faiskaburguer.db.dal.TipoPagamentoDAL;
import com.faiskaburguer.db.entidade.TipoPagamento;
import com.faiskaburguer.db.util.SingletonDB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.*;

public class EditarTipoPgto {

    @FXML
    private TextField nomepgto;

    private TipoPagamento tipoPagamento;
    public EditarTipoPgto(TipoPagamento tipoPagamento){
        this.tipoPagamento= tipoPagamento;
    }

    @FXML
    protected void initialize(){
        nomepgto.setText(tipoPagamento.getNome());
    }

    @FXML
    protected void Salvar(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        if(!nomepgto.getText().isEmpty()){
            tipoPagamento.setNome(nomepgto.getText());
            TipoPagamentoDAL tipoPagamentoDAL = new TipoPagamentoDAL();
            if(tipoPagamentoDAL.alterar(tipoPagamento)){

                alert.setHeaderText(null);
                alert.setContentText("Tipo de Pagamento alterado com sucesso!");
            }
            else{
                alert.setTitle("AVISO");
                alert.setHeaderText("Houve um erro no hora de salvar  o Tipo de Pagamento!");
                alert.setContentText(SingletonDB.getConexao().getMensagemErro());
            }
            alert.showAndWait();
        }
        else{
            alert.setHeaderText("Campo Vazio!");
            alert.setContentText("Digite um nome para alterar.");
        }
    }
}
