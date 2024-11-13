package com.faiskaburguer.db.dal;

import com.faiskaburguer.db.entidade.Endereco;
import com.faiskaburguer.db.entidade.Pedido;
import com.faiskaburguer.db.entidade.Produtos;
import com.faiskaburguer.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAL implements IDAL <Pedido>{
    @Override
    public boolean gravar(Pedido entidade) {
        boolean erro = false;

        String sqlPedido = """ 
                    INSERT INTO pedido (ped_data, ped_clinome, ped_clifone, ped_total, ped_viagem, tpg_id, ped_id, end_id)
                    VALUES ('#1', '#2', '#3', '#4', #5, #6, #7, #8);
                    """;
        sqlPedido = sqlPedido.replace("#1",entidade.getData().toString());
        sqlPedido = sqlPedido.replace("#2",entidade.getClinome());
        sqlPedido = sqlPedido.replace("#3",entidade.getClifone());
        sqlPedido = sqlPedido.replace("#4",""+entidade.getTotal());
        sqlPedido = sqlPedido.replace("#5",""+entidade.getViagem());
        sqlPedido = sqlPedido.replace("#6",""+entidade.getTipoPagamento().getId());
        sqlPedido = sqlPedido.replace("#7",""+(SingletonDB.getConexao().getMaxPK("pedido","ped_id")+1));
        if(entidade.getViagem()==1){
            EnderecoDAL enderecoDAL = new EnderecoDAL();
            enderecoDAL.gravar(entidade.getEndereco());
            sqlPedido = sqlPedido.replace("#8",""+ SingletonDB.getConexao().getMaxPK("endereco","end_id"));
        }
        else{
            sqlPedido = sqlPedido.replace("#8","null");
        }
        if (SingletonDB.getConexao().manipular(sqlPedido)) {
            int id= SingletonDB.getConexao().getMaxPK("pedido","ped_id");
                for(Pedido.Item item : entidade.getItens()){
                    String sqlItem = """
                            INSERT INTO item (pro_id, ped_id, it_quant, it_valor) VALUES (#1, #2, #3, #4);
                            """;
                    sqlItem = sqlItem.replace("#1", ""+item.produtos().getId());
                    sqlItem = sqlItem.replace("#2",""+id);
                    sqlItem = sqlItem.replace("#3",""+item.quant());
                    sqlItem = sqlItem.replace("#4",""+item.valor());
                    if(!SingletonDB.getConexao().manipular(sqlItem)){
                        erro=true;
                    }
                }
        }
        else
            erro = true;
        return erro;

    }

    @Override
    public boolean alterar(Pedido entidade) {
        boolean erro = false;
        String sql = """
                    UPDATE pedido
                    	SET ped_data='#2', ped_clinome='#3', ped_clifone='#4', ped_total=#5, ped_viagem=#6, tpg_id=#7, end_id=#8
                    	WHERE ped_id=#1;
                """;

        sql = sql.replace("#1", ""+entidade.getId());
        sql = sql.replace("#2", ""+entidade.getData().toString());
        sql = sql.replace("#3", ""+entidade.getClinome());
        sql = sql.replace("#4", ""+entidade.getClifone());
        sql = sql.replace("#5", ""+entidade.getTotal());
        sql = sql.replace("#6", ""+entidade.getViagem());
        sql = sql.replace("#7", ""+entidade.getTipoPagamento().getId());
        if(entidade.getViagem()==1){
            EnderecoDAL enderecoDAL = new EnderecoDAL();
            enderecoDAL.gravar(entidade.getEndereco());
            sql = sql.replace("#8",""+ SingletonDB.getConexao().getMaxPK("endereco","end_id"));
        }
        else{
            sql = sql.replace("#8","null");
        }
        if (SingletonDB.getConexao().manipular(sql)) {
            //apaga todos os itens do pedido e cria dnv
            SingletonDB.getConexao().manipular("DELETE FROM item WHERE ped_id="+entidade.getId());
            for(Pedido.Item item : entidade.getItens()){
                String sqlItem = """
                            INSERT INTO item (pro_id, ped_id, it_quant, it_valor) VALUES (#1, #2, #3, #4);
                            """;
                sqlItem = sqlItem.replace("#1", ""+item.produtos().getId());
                sqlItem = sqlItem.replace("#2",""+entidade.getId());
                sqlItem = sqlItem.replace("#3",""+item.quant());
                sqlItem = sqlItem.replace("#4",""+item.valor());
                if(!SingletonDB.getConexao().manipular(sqlItem)){
                    erro=true;
                }
            }
        }
        else
            erro = true;
        return erro;
    }

    @Override
    public boolean apagar(Pedido entidade) {
        int endId=0;
        if(entidade.getViagem()==1)
            endId = entidade.getEndereco().getId();

        SingletonDB.getConexao().manipular("DELETE FROM item WHERE ped_id="+entidade.getId());
        boolean erro = SingletonDB.getConexao().manipular("DELETE FROM pedido WHERE ped_id="+entidade.getId());

        if(entidade.getViagem()==1 && erro)
            erro = SingletonDB.getConexao().manipular("DELETE FROM endereco WHERE end_id="+endId);
        return erro;
    }

    @Override
    public Pedido get(int id) {
        Pedido pedido = null;
        String sql="SELECT * FROM pedido WHERE ped_id="+id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if(resultSet.next()) {
                pedido = new Pedido(resultSet.getInt("ped_id"),
                        resultSet.getDate("ped_data").toLocalDate(),
                        resultSet.getString("ped_clinome"),
                        resultSet.getString("ped_clifone"),
                        resultSet.getDouble("ped_total"),
                        resultSet.getInt("ped_viagem"),
                        new TipoPagamentoDAL().get(resultSet.getInt("tpg_id")));
                Endereco endereco = new EnderecoDAL().get(resultSet.getInt("end_id"));
                pedido.setEndereco(endereco);

                String sql2 = "SELECT * FROM item WHERE ped_id="+pedido.getId();
                ResultSet resultSet2 = SingletonDB.getConexao().consultar(sql2);
                while(resultSet2.next()){
                    Produtos produtos = new ProdutoDAL().get(resultSet2.getInt("prod_id"));
                    produtos.setValor(resultSet2.getDouble("it_valor"));
                    pedido.addItem(produtos,resultSet2.getInt("it_quant"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedido;
    }

    @Override
    public List<Pedido> get(String filtro) {
        List<Pedido> pedidoList = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        if(!filtro.isEmpty())
            sql+= " WHERE "+ filtro;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            while(resultSet.next()) {
                Pedido pedido = new Pedido(resultSet.getInt("ped_id"),
                        resultSet.getDate("ped_data").toLocalDate(),
                        resultSet.getString("ped_clinome"),
                        resultSet.getString("ped_clifone"),
                        resultSet.getDouble("ped_total"),
                        resultSet.getInt("ped_viagem"),
                        new TipoPagamentoDAL().get(resultSet.getInt("tpg_id")));
                Endereco endereco = new EnderecoDAL().get(resultSet.getInt("end_id"));
                pedido.setEndereco(endereco);
                pedidoList.add(pedido);

                String sql2 = "SELECT * FROM item WHERE ped_id="+pedido.getId();
                ResultSet resultSet2 = SingletonDB.getConexao().consultar(sql2);
                while(resultSet2.next()){
                    Produtos produtos = new ProdutoDAL().get(resultSet2.getInt("pro_id"));
                    produtos.setValor(resultSet2.getDouble("it_valor"));
                    pedido.addItem(produtos,resultSet2.getInt("it_quant"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidoList;
    }
}

