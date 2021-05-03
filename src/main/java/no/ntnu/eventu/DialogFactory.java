package no.ntnu.eventu;

import no.ntnu.eventu.Dialogs.*;

public class DialogFactory {

    public DialogFactoryInterface getDialog(DialogType dialogType){
        switch (dialogType){
            case Exit:
                return new  ExitDialog();
            case About:
                return new AboutDialog();
            case NoSelected:
                return new NoPatientSelectedDialog();
            case Remove:
                return new RemoveDialog();
        }
        return null;
    }

    public enum DialogType{
        Exit,
        About,
        NoSelected,
        Remove,
    }

}
