module no.ntnu.eventu {
    requires javafx.controls;
    requires javafx.fxml;

    opens no.ntnu.eventu to javafx.fxml;
    exports no.ntnu.eventu;
}