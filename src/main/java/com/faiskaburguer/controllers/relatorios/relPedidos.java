package com.faiskaburguer.controllers.relatorios;

import com.faiskaburguer.db.dal.PedidoDAL;
import com.faiskaburguer.db.entidade.Pedido;

import com.itextpdf.layout.element.Paragraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;


import javax.swing.*;
import java.io.IOException;
import java.util.List;

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
    protected void Salvar(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);

        if (fileChooser.getSelectedFile() != null) {
            int idx = pedidosSistema.getSelectionModel().getSelectedIndices().get(0);
            Pedido pedido = pedidoList.get(idx);

            // Caminho do arquivo PDF
            String pdfFilePath = fileChooser.getSelectedFile().getAbsolutePath() + "/" + pedido.getData().toString() + ".pdf";

            try (PdfWriter writer = new PdfWriter(pdfFilePath);
                 Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer))) {

                // Adicionar informações do pedido no PDF
                document.add(new Paragraph("Informações do Pedido"));
                document.add(new Paragraph("Nome: " + pedido.getClinome()));
                document.add(new Paragraph("Telefone: " + pedido.getClifone()));
                document.add(new Paragraph("Data: " + pedido.getData().toString()));

                if (pedido.getViagem() == 1) {
                    document.add(new Paragraph("Endereço:"));
                    document.add(new Paragraph("Rua: " + pedido.getEndereco().getRua()));
                    document.add(new Paragraph("Número: " + pedido.getEndereco().getNumero()));
                    document.add(new Paragraph("Cidade: " + pedido.getEndereco().getCidade()));
                    document.add(new Paragraph("UF: " + pedido.getEndereco().getUf()));
                    document.add(new Paragraph("Bairro: " + pedido.getEndereco().getBairro()));
                }

                document.add(new Paragraph("\nItens do Pedido:"));
                for (Pedido.Item item : pedido.getItens()) {
                    document.add(new Paragraph("- " + item.produtos().getNome() + " (Quantidade: " + item.quant() + ")"));
                }

                System.out.println("PDF gerado com sucesso em: " + pdfFilePath);

            } catch (IOException e) {
                System.out.println("Erro ao gerar o PDF: " + e.getMessage());
            }
        }
    }

    @FXML
    protected void SalvarTudo(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);

        if (fileChooser.getSelectedFile() != null) {
            String pdfFilePath = fileChooser.getSelectedFile().getAbsolutePath() + "/Todos_Os_Pedidos.pdf";

            try (PdfWriter writer = new PdfWriter(pdfFilePath);
                 Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer))) {

                document.add(new Paragraph("Todos os Pedidos\n"));

                for (Pedido pedido : pedidoList) {
                    document.add(new Paragraph("Pedido ID: " + pedido.getId()));
                    document.add(new Paragraph("Nome: " + pedido.getClinome()));
                    document.add(new Paragraph("Telefone: " + pedido.getClifone()));
                    document.add(new Paragraph("Data: " + pedido.getData().toString()));

                    if (pedido.getViagem() == 1) {
                        document.add(new Paragraph("Endereço:"));
                        document.add(new Paragraph("Rua: " + pedido.getEndereco().getRua()));
                        document.add(new Paragraph("Número: " + pedido.getEndereco().getNumero()));
                        document.add(new Paragraph("Cidade: " + pedido.getEndereco().getCidade()));
                        document.add(new Paragraph("UF: " + pedido.getEndereco().getUf()));
                        document.add(new Paragraph("Bairro: " + pedido.getEndereco().getBairro()));
                    }

                    document.add(new Paragraph("Itens do Pedido:"));
                    for (Pedido.Item item : pedido.getItens()) {
                        document.add(new Paragraph("- " + item.produtos().getNome() + " (Quantidade: " + item.quant() + ")"));
                    }
                    document.add(new Paragraph("\n"));
                }

                System.out.println("PDF gerado com sucesso em: " + pdfFilePath);

            } catch (IOException e) {
                System.out.println("Erro ao gerar o PDF: " + e.getMessage());
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
