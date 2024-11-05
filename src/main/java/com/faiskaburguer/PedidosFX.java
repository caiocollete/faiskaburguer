package com.faiskaburguer;

import com.faiskaburguer.db.util.SingletonDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class PedidosFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("starts.fxml"));
        Scene scene = new Scene(root, 600, 400);

        // Adiciona o arquivo CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Faiska Burguer");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        if(!SingletonDB.conectar()){
            System.out.println("ERRO: "+ SingletonDB.getConexao().getMensagemErro());
        }
        launch();

    }
}