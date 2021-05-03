package no.ntnu.eventu.Dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class NoPatientSelectedDialog implements DialogFactoryInterface {
    @Override
    public Dialog getDialog() {
        Alert noSelectedAlert = new Alert(Alert.AlertType.ERROR, "No patient selected", ButtonType.OK);
        noSelectedAlert.setTitle("Error");
        noSelectedAlert.setHeaderText("No patient selected");
        noSelectedAlert.setContentText("Mark a patient in the table before pressing first");
        return noSelectedAlert;
    }
}
