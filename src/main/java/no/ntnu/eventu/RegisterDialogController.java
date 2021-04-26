package no.ntnu.eventu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterDialogController {


    public TextField firstNameText;
    public TextField lastNameText;
    public TextField ssnText;
    public TextField gPText;
    public Button doneRegisterBtn;
    public Button cancelRegisterBtn;
    public ImageView registerImageView;
    public Label emptyRegWarningLbl;


    private PatientRegister patientRegister = PatientRegister.getInstance();


    @FXML
    private void initialize() {
        if (patientRegister.getRegisterSize() == 0) {
            emptyRegWarningLbl.setText("NB! Your register is empty, you might want to load a file first");
        }
        doneRegisterBtn.setOnAction(actionEvent -> handleRegisterOk());
        cancelRegisterBtn.setOnAction(actionEvent -> {
            try {
                switchToPrimary();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * When ok button is pressed
     * First checks if the input is valid. If invalid input, shows a Dialog box with which fields is invalid.
     * If valid, Tries to register a patient, and gives the user a dialog to confirm it was successful.
     * If registering the Patient was unsuccessfully, catches the exception and shows exceptionmessage in a alert dialog. Should
     * normally not reach this point because of the validInput() first.
     * <p>
     * ValidInput first is to check all input fields at once to improve user experience instead of one dialog box for each invalid input
     */
    public void handleRegisterOk() {
        if (validInput()) {
            try {
                patientRegister.registerPatient(firstNameText.getText(), lastNameText.getText(), ssnText.getText(), gPText.getText());
                Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
                okAlert.setHeaderText("Success!");
                okAlert.setContentText("Patient successfully registered");
                ImageView imageView = new ImageView(this.getClass().getResource("images/success.png").toString());
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                okAlert.setGraphic(imageView);
                okAlert.showAndWait();
                switchToPrimary();
            } catch (IllegalArgumentException | IOException e) {
                String errorMessage = e.getMessage();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid form");
                alert.setHeaderText("A field was not filled correctly");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            }
        }
    }


    /**
     * Switch to primary controller
     *
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }


    /**
     * Checks if the input from user is valid for a patient, and accumulates the field with wrong input to give the user a single feedback for all inputs
     *
     * @return true if all fields are valid, false if any input is wrong, and displays a dialog box with the errormessage
     */
    private boolean validInput() { // todo move to patientregister or patient?
        boolean valid = false;
        String errorMessage = "";
        if (firstNameText.getText() == null || firstNameText.getText().length() == 0) {
            errorMessage += "Invalid first name\n";
        }
        if (lastNameText.getText() == null || lastNameText.getText().length() == 0) {
            errorMessage += "Invalid last name\n";
        }
        if (gPText.getText() == null || gPText.getText().length() == 0) {
            errorMessage += "Invalid General Practitioner\n";
        }
        if (patientRegister.getPatientBySsn(ssnText.getText()) != null) {
            errorMessage += "A patient with that social security number already exists!";
        }
        if (!patientRegister.ssnValidator(ssnText.getText())) {
            errorMessage += "Social security number is invalid";
        }
        if (errorMessage.length() == 0) {
            valid = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid form");
            alert.setHeaderText("A field were not filled correctly");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        return valid;
    }


}
