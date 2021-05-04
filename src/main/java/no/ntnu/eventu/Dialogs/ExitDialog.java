package no.ntnu.eventu.Dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import no.ntnu.eventu.Interfaces.DialogFactoryInterface;


public class ExitDialog implements DialogFactoryInterface {

    /**
     * Method to get a edit confirmation dialog
     * dialog has yes and no button
     *
     * @return the alert dialog
     */
    @Override
    public Dialog<ButtonType> getDialog() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? \nAll unsaved progress will be lost!", ButtonType.YES, ButtonType.NO);
        exitAlert.setTitle("Confirm exit");
        exitAlert.setHeaderText("Exit application?");
        exitAlert.setAlertType(Alert.AlertType.WARNING);
        return exitAlert;
    }

}
