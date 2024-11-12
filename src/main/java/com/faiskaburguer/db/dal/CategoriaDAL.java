package com.faiskaburguer.db.dal;

import com.faiskaburguer.db.entidade.Categoria;
import com.faiskaburguer.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAL implements IDAL <Categoria> {

    @Override
    public boolean gravar(Categoria entidade) {
        String sql = """
                    INSERT INTO categoria(cat_nome,cat_id) VALUES ('#1', #2);
                """;

        sql = sql.replace("#1", entidade.getNome());
        sql = sql.replace("#2", ""+SingletonDB.getConexao().getMaxPK("categoria", "cat_id")+1);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Categoria entidade) {
        String sql = """
                    UPDATE categoria SET cat_nome='#1' WHERE cat_id=#2;
                """;

        sql = sql.replace("#1", entidade.getNome()).replace("#2", ""+entidade.getId());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Categoria entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM categoria WHERE cat_id="+entidade.getId());
    }

    @Override
    public Categoria get(int id) {
        Categoria categoria = null;
        String sql="SELECT * FROM categoria WHERE cat_id="+id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            if(resultSet.next())
                categoria = new Categoria(resultSet.getInt("cat_id"),
                        resultSet.getString("cat_nome"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoria;
    }

    @Override
    public List<Categoria> get(String filtro) {
        List<Categoria> categoriaList = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        if(!filtro.isEmpty())
            sql+= " WHERE "+ filtro;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            while(resultSet.next()) {
                Categoria categoria = new Categoria(resultSet.getInt("cat_id"),
                        resultSet.getString("cat_nome"));
                categoriaList.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoriaList;
    }


    public List<Categoria> get() {
        List<Categoria> categoriaList = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            while(resultSet.next()) {
                Categoria categoria = new Categoria(resultSet.getInt("cat_id"),
                        resultSet.getString("cat_nome"));
                categoriaList.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoriaList;
    }
}
