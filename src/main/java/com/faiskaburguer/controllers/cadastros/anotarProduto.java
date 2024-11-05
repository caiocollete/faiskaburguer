package com.faiskaburguer.controllers.cadastros;

import com.faiskaburguer.db.dal.CategoriaDAL;
import com.faiskaburguer.db.dal.ProdutoDAL;
import com.faiskaburguer.db.entidade.Categoria;
import com.faiskaburguer.db.entidade.Produtos;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class anotarProduto {


    @FXML
    private TextField nome_produto;

    @FXML
    private TextArea descr_produto;

    @FXML
    private TextField valor_produto;

    @FXML
    private SplitMenuButton cat_produto;

    @FXML
    private Button anotar_produto_btn;

    @FXML
    private ListView<String> listaProdutosNoSistema;

    public Categoria categoria;

    public void initialize() {
        atualizaListaProdutos();

        List<Categoria> categoriaList = new CategoriaDAL().get();
        for(Categoria obj : categoriaList) {
            MenuItem item = new MenuItem(obj.getNome());
            cat_produto.getItems().add(item);

            item.setOnAction(event -> {
                this.categoria = obj;
                cat_produto.setText(obj.getNome());
            });
        }
    }

    public void anotar() {
        System.out.println(nome_produto.getText());
        System.out.println(descr_produto.getText());
        System.out.println(Double.parseDouble(valor_produto.getText()));
        System.out.println(categoria.getNome());

        Produtos produto = new Produtos(nome_produto.getText(),descr_produto.getText(), Double.parseDouble(valor_produto.getText()),categoria);
        ProdutoDAL produtoDAL = new ProdutoDAL();
        if(produtoDAL.gravar(produto)){
            System.out.println("done");
        }

        listaProdutosNoSistema.getItems().clear();
        atualizaListaProdutos();
    }

    private void atualizaListaProdutos() {
        List<Produtos> produtosList = new ProdutoDAL().get();

        for (Produtos produto : produtosList) {
            listaProdutosNoSistema.getItems().add(produto.getNome());
        }
    }
}
