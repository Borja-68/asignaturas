module com.mycompany.prueba1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.prueba1 to javafx.fxml;
    exports com.mycompany.prueba1;
}
