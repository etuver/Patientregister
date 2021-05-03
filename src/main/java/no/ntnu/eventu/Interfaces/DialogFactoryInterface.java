package no.ntnu.eventu.Interfaces;

import javafx.scene.control.Dialog;

public interface DialogFactoryInterface<D> {
    Dialog<D> getDialog();
}
