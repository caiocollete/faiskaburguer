module com.caiovini.faiskaburguer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.sql;
    requires com.google.gson;


    opens com.faiskaburguer to javafx.fxml;
    exports com.faiskaburguer;
}