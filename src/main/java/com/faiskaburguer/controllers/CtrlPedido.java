package com.faiskaburguer.controllers;

import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.dal.ProdutoDAL;
import com.faiskaburguer.db.dal.TipoPagamentoDAL;
import com.faiskaburguer.db.entidade.Pedido;
import com.faiskaburguer.db.entidade.Produtos;
import com.faiskaburguer.db.entidade.TipoPagamento;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CtrlPedido {
    @FXML
    private TextField nome_cliente;

    @FXML
    private TextField numero_cliente;

    @FXML
    private CheckBox viagem_check;

    @FXML
    private MenuButton selectionProds;

    @FXML
    private ListView<String> listSelectionProds;

    @FXML
    private javafx.scene.text.Text total_pedido;

    @FXML
    private SplitMenuButton selectionTipoPagamento;

    @FXML
    private Button gravar;

    private List<Produtos> produtosSelecionados = new ArrayList<>();
    private List<Pedido.Item> listItens = new ArrayList<>();
    private TipoPagamento tipoPagamento;

    public void initialize() {
        List<Produtos> produtosList = new ProdutoDAL().get();
        List<TipoPagamento> tipoPagamentosList = new TipoPagamentoDAL().get();

        // Populate the MenuButton with product items
        for (Produtos produto : produtosList) {
            MenuItem menuItem = new MenuItem(produto.getNome());
            selectionProds.getItems().add(menuItem);

            // When a MenuItem is clicked, add it to the ListView
            menuItem.setOnAction(event -> {
                listSelectionProds.getItems().add(produto.getNome());
                this.listItens.add(new Pedido.Item(produto, retornaQuantidade(produto), produto.getValor()));
                this.produtosSelecionados.add(produto);

                System.out.println("Produto selecionado adicionado: " + produto.getNome());

                String total = total_pedido.getText();
                total = total.replace("R$", "").trim().replace(",", ".");
                Double totalDouble = Double.parseDouble(total);
                totalDouble+=produto.getValor();
                total = "R$"+String.format("%.2f", totalDouble);
                total_pedido.setText(total);
            });
        }

        for(TipoPagamento tpgtObj : tipoPagamentosList){
            MenuItem menuItem = new MenuItem(tpgtObj.getNome());
            selectionTipoPagamento.getItems().add(menuItem);

            menuItem.setOnAction(event -> {
                this.tipoPagamento = tpgtObj;
                selectionTipoPagamento.setText(tpgtObj.getNome());
            });
        }
    }

    private int retornaQuantidade(Produtos produto) {
        int quant=1;
        if(this.produtosSelecionados.contains(produto)){
            for(Produtos obj : this.produtosSelecionados)
                if(obj.equals(produto))
                    quant++;
        }
        return quant;
    }

    @FXML
    protected void GravarPedido() throws IOException {
        // Extrair e processar o valor total do pedido
        String total = total_pedido.getText();
        total = total.replace("R$", "").trim().replace(",", ".");

        Double totalDouble = Double.parseDouble(total);

        // Adiciona taxa de entrega se o pedido for "para viagem"
        if (viagem_check.isSelected()) {
            totalDouble += 5.00;
        }

        total = "R$ " + String.format("%.2f", totalDouble);

        // Mostrar diálogo de confirmação do pedido
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Pedido");
        alert.setContentText("Valor total do pedido: " + total);

        // Captura a resposta do usuário
        String finalTotal = total;
        Double finalTotalDouble = totalDouble;
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Executar ação de gravação do pedido se o usuário confirmar

                String nomeCliente = nome_cliente.getText();
                String numeroCliente = numero_cliente.getText();
                char viagem = 'N';
                if(viagem_check.isSelected()) viagem = 'S';

                System.out.println("Salvando pedido...");
                System.out.println("Cliente: " + nomeCliente);
                System.out.println("Número: " + numeroCliente);
                System.out.println("Viagem: " + viagem);
                System.out.println("Produtos Selecionados: " + this.produtosSelecionados);
                System.out.println("Total do pedido: " + finalTotal);

                Pedido pedido = new Pedido(LocalDate.now(),nomeCliente,numeroCliente, finalTotalDouble,viagem,tipoPagamento,listItens);

                // Armazena no db
                PedidoDAL pedidodal = new PedidoDAL();
                pedidodal.gravar(pedido);


                // Limpar campos após salvar o pedido
                nome_cliente.clear();
                numero_cliente.clear();
                viagem_check.setSelected(false);
                listSelectionProds.getItems().clear();
                System.out.println("Pedido salvo com sucesso!");
            }
        });
    }
}
