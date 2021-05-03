package no.ntnu.eventu.Dialogs;

import javafx.scene.control.Dialog;

public interface DialogFactoryInterface<D> {
    Dialog<D> getDialog();
}
