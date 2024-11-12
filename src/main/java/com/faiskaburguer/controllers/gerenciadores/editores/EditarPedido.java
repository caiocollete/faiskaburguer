package com.faiskaburguer.controllers.gerenciadores.editores;

import com.faiskaburguer.db.dal.CategoriaDAL;
import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.dal.ProdutoDAL;
import com.faiskaburguer.db.dal.TipoPagamentoDAL;
import com.faiskaburguer.db.entidade.*;
import com.faiskaburguer.db.util.SingletonDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EditarPedido {

    @FXML
    private TextField nome_cliente;

    @FXML
    private TextField numero_cliente;

    @FXML
    private CheckBox viagem_check;

    @FXML
    private TextField rua;

    @FXML
    private TextField numero_casa;

    @FXML
    private TextField cep;

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

    private Pedido pedido;

    public EditarPedido(Pedido pedido) {;
        this.pedido = pedido;
    }

    @FXML
    protected void initialize() {
        this.nome_cliente.setText(pedido.getClinome());
        this.numero_cliente.setText(pedido.getClifone());
        this.viagem_check.setSelected(pedido.getViagem());
        if(pedido.getViagem()){
            this.rua.setText(pedido.getEndereco().getRua());
            this.cep.setText(pedido.getEndereco().getCep());
            this.numero_casa.setText(pedido.getEndereco().getNumero());
        }
        else{
            this.rua.clear();
            this.cep.clear();
            this.numero_casa.clear();
        }


        this.selectionTipoPagamento.getItems().add(new MenuItem(pedido.getTipoPagamento().getNome()));
        this.selectionTipoPagamento.setText(pedido.getTipoPagamento().getNome());
        this.total_pedido.setText(Double.toString(pedido.getTotal()));


        List<Pedido.Item> itensPedido = pedido.getItens();
        for(Pedido.Item item : itensPedido){
            for(int i=0;i<item.quant();i++)
                listSelectionProds.getItems().add(item.produtos().getNome());
        }


        List<Produtos> produtosList = new ProdutoDAL().get("");
        List<TipoPagamento> tipoPagamentosList = new TipoPagamentoDAL().get();
        // Populate the MenuButton with product items
        for (Produtos produto : produtosList) {
            MenuItem menuItem = new MenuItem(produto.getNome());
            selectionProds.getItems().add(menuItem);

            // When a MenuItem is clicked, add it to the ListVie
            menuItem.setOnAction(event -> {
                // Adiciona o produto ao ListView, permitindo duplicatas
                listSelectionProds.getItems().add(produto.getNome());
                produtosSelecionados.add(produto);

                // Verifica se o produto já está na lista de itens do pedido
                boolean itemExiste = false;
                for (int i = 0; i < listItens.size(); i++) {
                    Pedido.Item item = listItens.get(i);
                    if (item.produtos().equals(produto)) {
                        // Cria um novo item com quantidade incrementada
                        Pedido.Item itemAtualizado = new Pedido.Item(produto, item.quant() + 1, produto.getValor());
                        listItens.set(i, itemAtualizado);
                        itemExiste = true;
                        break;
                    }
                }
                // Se o produto não está na lista, adiciona como novo
                if (!itemExiste) {
                    Pedido.Item novoItem = new Pedido.Item(produto, 1, produto.getValor());
                    listItens.add(novoItem);
                }


                // Atualiza o total do pedido
                Double totalDouble = Double.parseDouble(total_pedido.getText().replace("R$", "").trim().replace(",", "."));
                totalDouble += produto.getValor();
                total_pedido.setText("R$" + String.format("%.2f", totalDouble));
            });

        }

        for(TipoPagamento tpgtObj : tipoPagamentosList){
            if(!tpgtObj.equals(pedido.getTipoPagamento())){
                MenuItem menuItem = new MenuItem(tpgtObj.getNome());
                selectionTipoPagamento.getItems().add(menuItem);

                menuItem.setOnAction(event -> {
                    this.pedido.setTipoPagamento(tpgtObj);
                    selectionTipoPagamento.setText(tpgtObj.getNome());
                });
            }
        }
    }

    @FXML
    protected void GravarPedido(ActionEvent event) {
        // Extrair e processar o valor total do pedido
        String total = total_pedido.getText();
        total = total.replace("R$", "").trim().replace(",", ".");

        Double totalDouble = Double.parseDouble(total);

        // Adiciona taxa de entrega se o pedido for "para viagem"
        if (viagem_check.isSelected()) {
            totalDouble += 5;
        }

        total = "R$ " + String.format("%.2f", totalDouble);

        // Captura a resposta do usuário
        Double finalTotalDouble = totalDouble;

        // Mostrar diálogo de confirmação do pedido
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Pedido");
        alert.setContentText("Valor total do pedido: " + total);
        alert.showAndWait().ifPresent(response -> {

            if(response == ButtonType.OK){


                PedidoDAL pedidoDAL = new PedidoDAL();
                this.pedido.setClinome(nome_cliente.getText());
                this.pedido.setClifone(numero_cliente.getText());
                if(viagem_check.isSelected()){
                    this.pedido.setEndereco(new Endereco(cep.getText(),rua.getText(),numero_casa.getText()));
                }
                this.pedido.setTotal(finalTotalDouble);

                if(pedidoDAL.alterar(this.pedido)){
                    alert.setTitle("AVISO");
                    alert.setHeaderText(null);
                    alert.setContentText("Produto alterado com sucesso!");
                }
                else{
                    alert.setTitle("AVISO");
                    alert.setHeaderText("Houve um erro no hora de salvar  o produto!");
                    alert.setContentText(SingletonDB.getConexao().getMensagemErro());
                }
            }
        });
    }

    @FXML
    protected void RemoverProduto() {
        // Verifica se há um item selecionado
        int index = listSelectionProds.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            // Remove a entrada selecionada no ListView
            listSelectionProds.getItems().remove(index);

            Produtos produtoRemover = produtosSelecionados.get(index);

            // Remove o produto de produtosSelecionados
            produtosSelecionados.remove(index);

            // Atualiza a quantidade ou remove da lista `listItens`
            for (int i = 0; i < listItens.size(); i++) {
                Pedido.Item item = listItens.get(i);
                if (item.produtos().equals(produtoRemover)) {
                    if (item.quant() > 1) {
                        // Atualiza o item com quantidade decrementada
                        Pedido.Item itemAtualizado = new Pedido.Item(produtoRemover, item.quant() - 1, item.valor());
                        listItens.set(i, itemAtualizado);
                    } else {
                        // Remove o item se a quantidade for 1
                        listItens.remove(i);
                    }
                    break;
                }
            }

            // Atualiza o total do pedido
            Double totalDouble = Double.parseDouble(total_pedido.getText().replace("R$", "").trim().replace(",", "."));
            totalDouble -= produtoRemover.getValor();
            total_pedido.setText("R$" + String.format("%.2f", totalDouble));
        } else {
            System.out.println("Nenhum produto selecionado ou índice inválido.");
        }

        System.out.println(listSelectionProds.toString());
        System.out.println(produtosSelecionados.toString()); // DIMINUIR UM PRODUTO
        System.out.println(listItens.toString()); // ESTA INSERINDO DUPLICADO
    }

    @FXML
    protected void ativarEndereco(){
        rua.setDisable(!viagem_check.isSelected());
        numero_casa.setDisable(!viagem_check.isSelected());
        cep.setDisable(!viagem_check.isSelected());
    }
}
