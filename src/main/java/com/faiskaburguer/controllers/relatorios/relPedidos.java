package com.faiskaburguer.controllers.relatorios;

import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.entidade.Pedido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.RandomAccess;

public class relPedidos {

    @FXML
    private ListView<String> pedidosSistema;
    @FXML
    private ListView<String> produtosPedido;
    @FXML
    private TextField nome;
    @FXML
    private TextField telefone;
    @FXML
    private TextField rua;
    @FXML
    private TextField numero;
    @FXML
    private TextField cidade;
    @FXML
    private TextField uf;
    @FXML
    private TextField bairro;
    @FXML
    private DatePicker dataPedido;
    @FXML
    private CheckBox viagem;

    private List<Pedido> pedidoList;


    @FXML
    protected void initialize() {
        pedidoList = new PedidoDAL().get("");
        for(Pedido p : pedidoList){
            pedidosSistema.getItems().add(p.getClinome() + " - " + p.getData());
        }
    }

    @FXML
    protected void BuscarPedidos(ActionEvent event) {
        if(!nome.getText().isEmpty())
            clearAll();

        int idx = pedidosSistema.getSelectionModel().getSelectedIndices().get(0);
        Pedido pedido = pedidoList.get(idx);
        List<Pedido.Item> itensPedido = pedido.getItens();

        nome.setText(pedido.getClinome());
        telefone.setText(pedido.getClifone());
        dataPedido.setValue(pedido.getData());
        if(pedido.getViagem()==1){
            viagem.setSelected(true);
            rua.setText(pedido.getEndereco().getRua());
            numero.setText(pedido.getEndereco().getNumero());
            cidade.setText(pedido.getEndereco().getCidade());
            uf.setText(pedido.getEndereco().getUf());
            bairro.setText(pedido.getEndereco().getBairro());
        }

        for(Pedido.Item item : itensPedido){
            produtosPedido.getItems().add(item.produtos().getNome() +" - "+item.quant());
        }
    }

    @FXML
    protected void Salvar(ActionEvent event) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);

        if (fileChooser.getSelectedFile() != null) {
            int idx = pedidosSistema.getSelectionModel().getSelectedIndices().get(0);
            Pedido pedido = pedidoList.get(idx);

            // Definindo o nome do arquivo com a data do pedido
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + "/" + pedido.getData().toString() + ".txt");

            try (FileWriter fw = new FileWriter(file)) { // Utilizando try-with-resources para fechar o FileWriter automaticamente
                fw.write(pedido.toString());
                // Opcional: Exibir mensagem de sucesso
                System.out.println("Pedido salvo com sucesso em: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Erro ao salvar o pedido: " + e.getMessage());
            }
        }
    }


    @FXML
    protected void SalvarTudo(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);

        if (fileChooser.getSelectedFile() != null) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + "/" + "Todos_Os_Pedidos.txt");

            try (FileWriter fw = new FileWriter(file, true)) { // 'true' ativa o modo append
                for (Pedido p : pedidoList) {
                    fw.write(p.toString() + System.lineSeparator()); // Adiciona uma nova linha após cada pedido
                    System.out.println("Pedido #" + p.getId() + " salvo com sucesso em: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("Erro ao salvar os pedidos: " + e.getMessage());
            }
        }
    }

    private void clearAll() {
        nome.clear();
        telefone.clear();
        dataPedido.setValue(null);
        if(viagem.isSelected()){
            rua.clear();
            numero.clear();
            cidade.clear();
            uf.clear();
            bairro.clear();
            viagem.setSelected(false);
        }
        produtosPedido.getItems().clear();
    }
}
