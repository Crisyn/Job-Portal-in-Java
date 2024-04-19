module org.example.jobportal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.net.http;
    requires com.google.gson;
    requires atlantafx.base;

    opens org.example.jobportal to javafx.fxml;
    exports org.example.jobportal;
}