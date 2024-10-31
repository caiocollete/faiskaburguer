package com.faiskaburguer.db.movimentacao;

import com.faiskaburguer.db.dal.*;
import com.faiskaburguer.db.entidade.*;
import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.dal.ProdutoDAL;
import com.faiskaburguer.db.entidade.Pedido;
import com.faiskaburguer.db.entidade.Produtos;
import com.faiskaburguer.db.entidade.TipoPagamento;
import com.faiskaburguer.db.util.SingletonDB;

import java.time.LocalDate;
import java.util.List;

public abstract class GerenciarPedido {
    /*
    Na criação de um pedido não há a necessidade de registrar os dados do cliente, somente
    seu telefone e nome serão informados. Os produtos vendidos serão os cadastrados no sistema e categorizados
    (lanches, bebidas, pizzas e outros tipos de produtos), além de ter uma foto, preço, descrição e nome.
    Ao criar um pedido, será perguntado ao cliente se o mesmo será consumido no local ou será embalado para viagem.
    Caso seja embalado para viagem será cobrado o valor da embalagem junto com o pedido.
    O tipo de pagamento do pedido também será informado.
    Ao analisar os requisitos do sistema foi proposto os seguintes módulos:*/

    public void novoPedido(String clinome, String clifone, Double total, char viagem, TipoPagamento tipoPagamento, List<Integer> idsProdutos, List<Integer> quantProdutos){
        Pedido pedido = new Pedido(LocalDate.now(), clinome, clifone, total, viagem, tipoPagamento);
        PedidoDAL pedidoDAL = new PedidoDAL();
        ProdutoDAL produtosDAL = new ProdutoDAL();

        for(int i: idsProdutos){
            Produtos produto = produtosDAL.get(idsProdutos.get(i));
            if(!produto.getValor().isNaN()){
                pedido.addItem(produto, quantProdutos.get(i));
            }
        }
        if(!pedidoDAL.gravar(pedido)){
            System.out.println("ERRO: "+ SingletonDB.getConexao().getMensagemErro());
        }
    }

    public void atualizarPedido(int id){

    }

}
