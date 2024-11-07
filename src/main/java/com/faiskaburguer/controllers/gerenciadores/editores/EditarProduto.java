package com.faiskaburguer.controllers.gerenciadores.editores;

import com.faiskaburguer.db.dal.CategoriaDAL;
import com.faiskaburguer.db.dal.ProdutoDAL;
import com.faiskaburguer.db.entidade.Categoria;
import com.faiskaburguer.db.entidade.Produtos;
import com.faiskaburguer.db.util.SingletonDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

import java.util.List;

public class EditarProduto {

    @FXML
    private TextField nomeprod;
    @FXML
    private TextField precoprod;
    @FXML
    private SplitMenuButton catprod;

    private Produtos produto;

    public EditarProduto(Produtos produto) {;
        this.produto = produto;
    }

    @FXML
    protected void initialize() {
        this.nomeprod.setText(produto.getNome());
        this.precoprod.setText(produto.getValor().toString());

        CategoriaDAL categoriaDAL = new CategoriaDAL();
        List<Categoria> categoriaList = categoriaDAL.get("");
        for(Categoria categoria : categoriaList) {
            MenuItem item = new MenuItem(categoria.getNome());
            catprod.getItems().add(item);
            if(produto.getCategoria().getNome().equals(categoria.getNome())) {
                catprod.setText(categoria.getNome());
            }

            item.setOnAction(event -> {
                this.produto.setCategoria(categoria);
                catprod.setText(categoria.getNome());
            });
        }
    }


    public void salvarCamposEditados(ActionEvent event) {
        ProdutoDAL produtoDAL = new ProdutoDAL();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        this.produto.setNome(nomeprod.getText());
        this.produto.setValor(Double.parseDouble(precoprod.getText()));
        if(produtoDAL.alterar(this.produto)){
            alert.setTitle("AVISO");
            alert.setHeaderText(null);
            alert.setContentText("Produto alterado com sucesso!");
        }
        else{
            alert.setTitle("AVISO");
            alert.setHeaderText(SingletonDB.getConexao().getMensagemErro());
            alert.setContentText("Houve um erro no hora de salvar  o produto!");
        }
        alert.showAndWait();
    }
}
