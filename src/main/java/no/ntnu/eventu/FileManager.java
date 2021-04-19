package no.ntnu.eventu;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class FileManager {



    public void readFile(){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter;
        filter = new FileChooser.ExtensionFilter("*.csv","*.CSV");
        chooser.getExtensionFilters().add(filter);




    }
}
