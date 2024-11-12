package com.faiskaburguer.controllers.gerenciadores.editores;

import com.faiskaburguer.db.dal.CategoriaDAL;
import com.faiskaburguer.db.dal.TipoPagamentoDAL;
import com.faiskaburguer.db.entidade.Categoria;
import com.faiskaburguer.db.entidade.TipoPagamento;
import com.faiskaburguer.db.util.SingletonDB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EditarCategoria {

    @FXML
    private TextField nomecat;

    private Categoria categoria;
    public EditarCategoria(Categoria categoria){
        this.categoria= categoria;
    }

    @FXML
    protected void initialize(){
        nomecat.setText(categoria.getNome());
    }

    @FXML
    protected void Salvar(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        if(!nomecat.getText().isEmpty()){
            categoria.setNome(nomecat.getText());
            CategoriaDAL categoriaDAL = new CategoriaDAL();
            if(categoriaDAL.alterar(categoria)){

                alert.setHeaderText(null);
                alert.setContentText("Categoria alterada com sucesso!");
            }
            else{
                alert.setTitle("AVISO");
                alert.setHeaderText("Houve um erro no hora de salvar  a Categoria!");
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
