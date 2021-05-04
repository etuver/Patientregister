package no.ntnu.eventu;

import no.ntnu.eventu.Dialogs.*;
import no.ntnu.eventu.Interfaces.DialogFactoryInterface;

/**
 * A simple factory class for smaller Dialog alerts
 *
 * @Author Eventu
 */
public class DialogFactory {

    /**
     * Method to get a chosen dialog
     *
     * @param dialogType the type of dialog wanted
     * @return the chosen dialog
     */
    public DialogFactoryInterface getDialog(DialogType dialogType) {
        switch (dialogType) {
            case Exit:
                return new ExitDialog();
            case About:
                return new AboutDialog();
            case NoSelected:
                return new NoPatientSelectedDialog();
            case Remove:
                return new RemoveDialog();
        }
        return null;
    }

    public enum DialogType {
        Exit,
        About,
        NoSelected,
        Remove,
    }

}
