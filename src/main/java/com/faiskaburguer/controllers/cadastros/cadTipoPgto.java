package com.faiskaburguer.controllers.cadastros;

import com.faiskaburguer.db.dal.TipoPagamentoDAL;
import com.faiskaburguer.db.entidade.TipoPagamento;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class cadTipoPgto {

    @FXML
    private TextField nomepgto;

    @FXML
    protected void CadastrarTipoPgto(){
        TipoPagamentoDAL tipoPagamentoDAL = new TipoPagamentoDAL();
        tipoPagamentoDAL.gravar( new TipoPagamento(nomepgto.getText()));
        nomepgto.clear();
    }
}
