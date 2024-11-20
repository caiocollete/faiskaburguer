package com.faiskaburguer.controllers.gerenciadores.editores;

import com.faiskaburguer.db.dal.CategoriaDAL;
import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.dal.ProdutoDAL;
import com.faiskaburguer.db.dal.TipoPagamentoDAL;
import com.faiskaburguer.db.entidade.*;
import com.faiskaburguer.db.util.Mascaras;
import com.faiskaburguer.db.util.SingletonDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.faiskaburguer.db.util.Mascaras.mascFone;

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
        this.viagem_check.setSelected(false);
        if(pedido.getViagem()==1){
            this.viagem_check.setSelected(true);
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
        this.total_pedido.setText("R$" + pedido.getTotal());


        List<Pedido.Item> itensPedido = pedido.getItens();
        for(Pedido.Item item : itensPedido){
            listItens.add(item);
            for(int i=0;i<item.quant();i++){
                listSelectionProds.getItems().add(item.produtos().getNome());
                produtosSelecionados.add(item.produtos());
            }
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

        System.out.printf("aaa");
        for(Pedido.Item i: pedido.getItens()){
            System.out.println(i.produtos()+"-"+i.quant());
        }

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
                this.pedido.setClifone(Mascaras.mascFone(numero_cliente.getText()));
                if(viagem_check.isSelected()){
                    this.pedido.setEndereco(new Endereco(cep.getText(),rua.getText(),numero_casa.getText()));
                    this.pedido.setViagem(1);
                }
                else this.pedido.setViagem(0);
                this.pedido.setTotal(finalTotalDouble);
                this.pedido.setListItens(listItens);

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
        // Verifica se há um item selecionado no ListView
        int index = listSelectionProds.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            // Remove a entrada selecionada no ListView
            Produtos produtoRemover = produtosSelecionados.get(index);
            listSelectionProds.getItems().remove(index);
            produtosSelecionados.remove(index);

            // Atualiza a quantidade ou remove da lista `listItens`
            Pedido.Item itemRemover = null;

            for (Pedido.Item item : pedido.getItens()) {
                if (item.produtos().equals(produtoRemover)) {
                    if (item.quant() > 1) {
                        // Atualiza o item com a quantidade decrementada
                        Pedido.Item itemAtualizado = new Pedido.Item(
                                item.produtos(),
                                item.quant() - 1,
                                item.valor()
                        );
                        pedido.getItens().set(pedido.getItens().indexOf(item), itemAtualizado);
                    } else {
                        // Marca o item para remoção se a quantidade for 1
                        itemRemover = item;
                    }
                    break;
                }
            }

            // Remove o item da lista se necessário
            if (itemRemover != null) {
                pedido.getItens().remove(itemRemover);
            }

            // Atualiza o total do pedido
            pedido.totalizar();
            total_pedido.setText("R$ " + String.format("%.2f", pedido.getTotal()));
        } else {
            // Nenhum produto selecionado
            System.out.println("Nenhum produto selecionado ou índice inválido.");
        }

        // Debugging opcional
        System.out.println("Produtos selecionados: " + produtosSelecionados);
        System.out.println("Itens do pedido: " + pedido.getItens());
    }



    @FXML
    protected void ativarEndereco(){
        rua.setDisable(!viagem_check.isSelected());
        numero_casa.setDisable(!viagem_check.isSelected());
        cep.setDisable(!viagem_check.isSelected());
    }
}
