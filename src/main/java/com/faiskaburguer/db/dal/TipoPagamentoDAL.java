package com.faiskaburguer.db.dal;

import com.faiskaburguer.db.entidade.TipoPagamento;
import com.faiskaburguer.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoPagamentoDAL implements IDAL <TipoPagamento>{
    @Override
    public boolean gravar(TipoPagamento entidade) {
        String sql = """
                    INSERT INTO tipo_pagamento(tpg_id) VALUES (#1);
                """;

        sql = sql.replace("#1", ""+entidade.getId());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(TipoPagamento entidade) {
        String sql = """
                    UPDATE tipo_pagamento SET tpg_nome='#1' WHERE tpg_id=#2;
                """;

        sql = sql.replace("#1", entidade.getNome()).replace("#2", ""+entidade.getId());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(TipoPagamento entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM tipo_pagamento WHERE tpg_id="+entidade.getId());
    }

    @Override
    public TipoPagamento get(int id) throws SQLException {
        TipoPagamento tipoPagamento = null;
        String sql="SELECT * FROM tipo_pagamento WHERE tpg_id="+id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            if(resultSet.next())
                tipoPagamento = new TipoPagamento(resultSet.getInt("tpg_id"),
                        resultSet.getString("tpg_nome"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoPagamento;
    }

    @Override
    public List<TipoPagamento> get(String filtro) {
        List<TipoPagamento> tipoPagamentoList = new ArrayList<>();
        String sql = "SELECT * FROM tipo_pagamento";
        if(!filtro.isEmpty())
            sql+= " WHERE "+ filtro;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            while(resultSet.next()) {
                TipoPagamento tpg = new TipoPagamento(resultSet.getInt("tpg_id"),
                        resultSet.getString("tpg_nome"));
                tipoPagamentoList.add(tpg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoPagamentoList;
    }
}
