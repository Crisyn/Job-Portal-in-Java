module org.example.jobportal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.jobportal to javafx.fxml;
    exports org.example.jobportal;
}