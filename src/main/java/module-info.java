module com.caiovini.faiskaburguer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.sql;
    requires com.google.gson;
    requires java.desktop;


    opens com.faiskaburguer to javafx.fxml;
    exports com.faiskaburguer;
    exports com.faiskaburguer.controllers;
    opens com.faiskaburguer.controllers to javafx.fxml;
}