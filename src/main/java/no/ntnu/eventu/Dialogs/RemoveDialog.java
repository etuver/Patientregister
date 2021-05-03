package no.ntnu.eventu.Dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class RemoveDialog implements DialogFactoryInterface{
    @Override
    public Dialog getDialog() {
        Alert removeAlert = new Alert(Alert.AlertType.CONFIRMATION, "Remove patient?", ButtonType.YES, ButtonType.NO);
        removeAlert.setTitle("Remove patient");
        removeAlert.setHeaderText("Are you sure you want to remove this patient from the register?\nThis can`t be undone!");
        removeAlert.setContentText("Press yes to confirm, no to cancel");
        return removeAlert;
    }
}