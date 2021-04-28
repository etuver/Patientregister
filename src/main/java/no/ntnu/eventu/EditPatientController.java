package no.ntnu.eventu;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;
import no.ntnu.eventu.Exception.RemoveException;

public class EditPatientController {

    public Button secondaryButton;
    public TextField firstNameText;
    public TextField lastNameText;
    public TextField gPText;
    public Button doneEditBtn;
    public Button cancelEditBtn;
    public Label editWarningLbl;
    public Label ssnLabel;
    public TextField ssnText;


    PatientRegister patientRegister = PatientRegister.getInstance();
    Patient patientToEdit;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void editPatient(String ssn){

       patientToEdit = patientRegister.getPatientBySsn(ssn);
        patientToEdit = patientRegister.getPatientBySsn(ssn);
        firstNameText.setText(patientToEdit.getFirstName());
        lastNameText.setText(patientToEdit.getLastName());
        gPText.setText(patientToEdit.getGeneralPractitioner());
        ssnText.setText(ssn);
        // SSN not editable
        ssnText.setEditable(false);
        ssnText.setMouseTransparent(true);
        ssnText.setFocusTraversable(false);
    }

    @FXML
    private void handleEditOK() {
        try {
            Patient patientToEdit = patientRegister.getPatientBySsn(ssnText.getText());
            patientToEdit.setFirstName(firstNameText.getText());
            patientToEdit.setLastName(lastNameText.getText());
            patientToEdit.setGeneralPractitioner(gPText.getText());
            Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
            okAlert.setHeaderText("Success!");
            okAlert.setContentText("Patient successfully edited");
            ImageView imageView = new ImageView(this.getClass().getResource("images/success.png").toString());
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            okAlert.setGraphic(imageView);
            okAlert.showAndWait();
            closeEdit();

        }catch ( IllegalArgumentException i){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error editing the patient");
            alert.setContentText(i.getMessage());
            alert.showAndWait();
            //closeEdit();


        }
    }

    private void closeEdit(){
        Stage stage = (Stage) doneEditBtn.getScene().getWindow();
        stage.close();
    }



    public void editCancel(ActionEvent event) {
        Window window =   ((Node)(event.getSource())).getScene().getWindow();
        if (window instanceof Stage){
            ((Stage) window).close();
        }

    }
}