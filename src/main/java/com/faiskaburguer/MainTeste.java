package com.faiskaburguer;

import com.faiskaburguer.db.dal.CategoriaDAL;
import com.faiskaburguer.db.entidade.Categoria;
import com.faiskaburguer.db.entidade.Empresa;
import com.faiskaburguer.db.util.SingletonDB;

public class MainTeste {
    public static void main(String[] args){
        if(!SingletonDB.conectar()){
            System.out.println("ERRO: "+ SingletonDB.getConexao().getMensagemErro());
        }

        System.out.println("Classe teste");
        Categoria categoria = new Categoria("HAMBURGER");
        CategoriaDAL categoriaDAL = new CategoriaDAL();

        if(categoriaDAL.gravar(categoria)){
            System.out.println("CATEGORIA ARMAZENADA");
        }
        else System.out.println("ERRO: "+ SingletonDB.getConexao().getMensagemErro());

        Empresa emp = new Empresa(1,"cap LTDA", "cap", "", "19407010","767","","",10.0);
        System.out.println(emp.getEndereco());
    }
}
