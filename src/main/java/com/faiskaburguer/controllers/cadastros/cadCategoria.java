package com.faiskaburguer.controllers.cadastros;

import com.faiskaburguer.db.dal.CategoriaDAL;
import com.faiskaburguer.db.entidade.Categoria;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class cadCategoria {

    @FXML
    private TextField cat_nome;

    @FXML
    protected void CadastrarCategoria(){
        CategoriaDAL categoriaDAL = new CategoriaDAL();
        categoriaDAL.gravar(new Categoria(cat_nome.getText()));

        cat_nome.clear();
    }

}
