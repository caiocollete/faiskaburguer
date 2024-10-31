package com.faiskaburguer.db.dal;

import com.faiskaburguer.db.entidade.Empresa;
import com.faiskaburguer.db.util.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAL implements IDAL <Empresa> {
    @Override
    public boolean gravar(Empresa entidade) {
        String sql = """
                INSERT INTO public.empresa(
                emp_id, emp_razao, emp_fantasia, emp_cnpj, emp_cep, emp_rua, emp_numero, emp_bairro, emp_cidade, emp_uf, emp_fone, emp_email, emp_vlremb)
        VALUES (#1, '#2', '#3', '#4', '#5', '#6', '#7', '#8', '#9', '#a1', '#a2', '#a3', #a4);
        """;

sql = sql.replace("#1", ""+entidade.getEmp_id());
sql = sql.replace("#2", entidade.getEmp_razao());
sql = sql.replace("#3", entidade.getEmp_fantasia());
sql = sql.replace("#4", entidade.getEmp_cnpj());
sql = sql.replace("#5", entidade.getEmp_cep());
sql = sql.replace("#6", entidade.endereco.logradouro());
sql = sql.replace("#7", entidade.getEmp_numero());
sql = sql.replace("#8", entidade.endereco.bairro());
sql = sql.replace("#9", entidade.endereco.localidade());
sql = sql.replace("#a1", entidade.endereco.uf());
sql = sql.replace("#a2", entidade.getEmp_fone());
sql = sql.replace("#a3", entidade.getEmp_email());
sql = sql.replace("#a4", ""+entidade.getEmp_vlremb());
return SingletonDB.getConexao().manipular(sql);
}

@Override
public boolean alterar(Empresa entidade) {
String sql = """
               UPDATE public.empresa
               SET  emp_razao='#1', emp_fantasia='#2', emp_cnpj='#3', emp_cep='#4', emp_rua='#5', emp_numero='#6', emp_bairro='#7', emp_cidade='#8', emp_uf='#9', emp_fone='#a1', emp_email='#a2', emp_vlremb=#a3
               WHERE <condition>;
                """;

        sql = sql.replace("#1", entidade.getEmp_razao());
        sql = sql.replace("#2", entidade.getEmp_fantasia());
        sql = sql.replace("#3", entidade.getEmp_cnpj());
        sql = sql.replace("#4", entidade.getEmp_cep());
        sql = sql.replace("#5", entidade.endereco.logradouro());
        sql = sql.replace("#6", entidade.getEmp_numero());
        sql = sql.replace("#7", entidade.endereco.bairro());
        sql = sql.replace("#8", entidade.endereco.localidade());
        sql = sql.replace("#9", entidade.endereco.uf());
        sql = sql.replace("#a1", entidade.getEmp_fone());
        sql = sql.replace("#a2", entidade.getEmp_email());
        sql = sql.replace("#a3", ""+entidade.getEmp_vlremb());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Empresa entidade) {
       return SingletonDB.getConexao().manipular("DELETE FROM empresa WHERE emp_id="+entidade.getEmp_id());
    }

    @Override
    public Empresa get(int id) throws SQLException {
        Empresa empresa = null;
        String sql="SELECT * FROM empresa WHERE emp_id="+id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            if(resultSet.next())
                empresa = new Empresa(resultSet.getInt("emp_id"),
                        resultSet.getString("emp_razap"),
                        resultSet.getString("emp_fantasia"),
                        resultSet.getString("emp_cnpj"),
                        resultSet.getString("emp_cep"),
                        resultSet.getString("emp_numero"),
                        resultSet.getString("emp_fone"),
                        resultSet.getString("emp_email"),
                        resultSet.getDouble("emp_vlremb"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empresa;
    }

    @Override
    public List<Empresa> get(String filtro) {
        List<Empresa> empresaList = new ArrayList<>();
        String sql = "SELECT * FROM empresa";
        if(!filtro.isEmpty())
            sql+= " WHERE "+ filtro;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);


        try {
            while(resultSet.next()) {
                Empresa empresa = new Empresa(resultSet.getInt("emp_id"),
                        resultSet.getString("emp_razap"),
                        resultSet.getString("emp_fantasia"),
                        resultSet.getString("emp_cnpj"),
                        resultSet.getString("emp_cep"),
                        resultSet.getString("emp_numero"),
                        resultSet.getString("emp_fone"),
                        resultSet.getString("emp_email"),
                        resultSet.getDouble("emp_vlremb"));
                empresaList.add(empresa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empresaList;
    }
}
