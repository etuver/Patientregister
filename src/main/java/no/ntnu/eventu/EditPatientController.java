package no.ntnu.eventu;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * Controller class for the edit patient dialog
 *
 * @Author Eventu
 */
public class EditPatientController {


    // Textfields
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField gPText;
    @FXML
    private TextField ssnText;
    @FXML
    private TextField diagnosisText;

    // Buttons
    @FXML
    private Button doneEditBtn;
    @FXML
    private Button cancelEditBtn;

    // Labels
    @FXML
    private Label editWarningLbl;
    @FXML
    private Label ssnLabel;


    private PatientRegister patientRegister = PatientRegister.getInstance();
    private Patient patientToEdit;


    /**
     * Method which takes finds the patient to be edit from the register and fills the form with the current values
     * Ssn is show for the user to be able to see which patient he/she is editing, aswell as keeping the layout the same as for registering a new patient.
     * SSN is made not editable and user can not interact with it
     *
     * @param ssn social security number of the patient to be edited
     */
    public void editPatient(String ssn) {
        patientToEdit = patientRegister.getPatientBySsn(ssn);
        firstNameText.setText(patientToEdit.getFirstName());
        lastNameText.setText(patientToEdit.getLastName());
        gPText.setText(patientToEdit.getGeneralPractitioner());
        ssnText.setPromptText(ssn);
        diagnosisText.setText(patientToEdit.getDiagnosis());

        // SSN not editable
        ssnText.setEditable(false);
        ssnText.setMouseTransparent(true);
        ssnText.setFocusTraversable(false);
    }


    /**
     * When a user presses the OK button, tries to set the changes for the patient to edit
     * If successfully the user gets a dialog confirming it was a success
     * If unsuccessful the user get a dialog with a message of what went wrong
     */
    @FXML
    private void handleEditOK() {
        try {
            patientToEdit.setFirstName(firstNameText.getText());
            patientToEdit.setLastName(lastNameText.getText());
            patientToEdit.setGeneralPractitioner(gPText.getText());
            patientToEdit.setDiagnosis(diagnosisText.getText());
            Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
            okAlert.setHeaderText("Success!");
            okAlert.setContentText("Patient successfully edited");
            ImageView imageView = new ImageView(this.getClass().getResource("images/success.png").toString());
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            okAlert.setGraphic(imageView);
            okAlert.showAndWait();
            closeEdit();

        } catch (IllegalArgumentException i) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error editing the patient");
            alert.setContentText(i.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * Close the edit dialog and go back to main window
     */
    private void closeEdit() {
        Stage stage = (Stage) doneEditBtn.getScene().getWindow();
        stage.close();
    }


    /**
     * If the user presses the cancel button, closes the edit dialog and go back to main window
     *
     * @param event any action event
     */
    public void editCancel(ActionEvent event) {
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage) window).close();
        }
    }
}