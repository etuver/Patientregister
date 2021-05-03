package no.ntnu.eventu.Dialogs;

import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import no.ntnu.eventu.Interfaces.DialogFactoryInterface;

public class AboutDialog implements DialogFactoryInterface {
    @Override
    public Dialog<ButtonType> getDialog() {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        FlowPane fp = new FlowPane();
        Label label = new Label("Application created by " +
                "\n(C)Even Tuverud\n" +
                "2021-04-20\n" +
                "\n" +
                "Check out my GitLab for more:\n");

        Hyperlink hyperlink = new Hyperlink("https://gitlab.stud.idi.ntnu.no/eventu/patientregister");
        Label bottomLabel = new Label("(Click link to copy to clipboard)");
        hyperlink.setOnAction(event -> {
                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(hyperlink.getText());
                    clipboard.setContent(content);
                }
        );
        fp.getChildren().addAll(label, hyperlink, bottomLabel);
        aboutAlert.getDialogPane().contentProperty().set(fp);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Patient Register \nv0.1-SNAPSHOT");
        return aboutAlert;
    }
}
