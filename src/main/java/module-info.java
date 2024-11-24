module com.faiskaburguer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.sql;
    requires com.google.gson;
    requires java.desktop;
    requires layout;
    requires kernel;


    opens com.faiskaburguer to javafx.fxml;
    exports com.faiskaburguer;
    exports com.faiskaburguer.controllers;
    opens com.faiskaburguer.controllers to javafx.fxml;
    exports com.faiskaburguer.controllers.cadastros;
    opens com.faiskaburguer.controllers.cadastros to javafx.fxml;
    exports com.faiskaburguer.controllers.gerenciadores;
    opens com.faiskaburguer.controllers.gerenciadores to javafx.fxml;
    exports com.faiskaburguer.controllers.relatorios;
    opens com.faiskaburguer.controllers.relatorios to javafx.fxml;
    exports com.faiskaburguer.db.entidade;
    opens com.faiskaburguer.db.entidade to javafx.fxml;
    exports com.faiskaburguer.controllers.gerenciadores.editores;
    opens com.faiskaburguer.controllers.gerenciadores.editores to javafx.fxml;
}