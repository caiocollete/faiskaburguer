package com.faiskaburguer.db.dal;

import com.faiskaburguer.db.entidade.Produtos;
import com.faiskaburguer.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProdutoDAL implements IDAL <Produtos> {

    @Override
    public boolean gravar(Produtos entidade) {
        String sql = """
                    INSERT INTO public.produto(
                    	pro_id, pro_nome, pro_descr, pro_valor, cat_id)
                    	VALUES (#1, '#2', '#3', #4, #5);
                """;

        sql = sql.replace("#1", "" + entidade.getId());
        sql = sql.replace("#2", "" + entidade.getNome());
        sql = sql.replace("#3", "" + entidade.getDescricao());
        sql = sql.replace("#4", "" + entidade.getValor());
        sql = sql.replace("#5", "" + entidade.getCategoria().getId());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Produtos entidade) {
        String sql = """
                    UPDATE public.produto
                    	SET  pro_nome='#1', pro_descr='#2', pro_valor=#3, cat_id=#4
                    	WHERE prod_id=#5
                """;


        sql = sql.replace("#1", "" + entidade.getNome());
        sql = sql.replace("#2", "" + entidade.getDescricao());
        sql = sql.replace("#3", "" + entidade.getValor());
        sql = sql.replace("#4", "" + entidade.getCategoria().getId());
        sql = sql.replace("#5", "" + entidade.getId());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Produtos entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM produto WHERE prod_id=" + entidade.getId());
    }

    @Override
    public Produtos get(int id) {
        Produtos produto = null;
        String sql = "SELECT * FROM produto WHERE prod_id=" + id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            if (resultSet.next()) {
                CategoriaDAL catDAL = new CategoriaDAL();
                produto = new Produtos(resultSet.getInt("prod_id"),
                        resultSet.getString("prod_nome"),
                        resultSet.getString("prod_desc"),
                        resultSet.getDouble("prod_valor"),
                        catDAL.get(resultSet.getInt("cat_id"))

                );

                //int id, String nome, String descricao, Double valor, Categoria categoria
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    @Override
    public List<Produtos> get(String filtro) {
        return null;
    }
}
