package no.ntnu.eventu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RegisterDialogController {


    public TextField firstNameText;
    public TextField lastNameText;
    public TextField ssnText;
    public TextField gPText;
    public Button doneRegisterBtn;
    public Button cancelRegisterBtn;
    public ImageView registerImageView;


    private Stage registerStage;
    private Patient registerPatient;
    PatientRegister patientRegister = new PatientRegister("patientRegister");


    @FXML
    private void initialize(){

    }

    public void setRegisterStage(Stage registerStage){
        this.registerStage = registerStage;
        patientRegister.registerPatient(firstNameText.getText(), lastNameText.getText(), ssnText.getText(), gPText.getText());
    }

    public void handleRegisterOk(){
        if (validInput()){
            patientRegister.registerPatient(firstNameText.getText(), lastNameText.getText(), ssnText.getText(), gPText.getText());
            Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
            okAlert.setHeaderText("Success!");
            okAlert.setContentText("Patient successfully registered");
            okAlert.showAndWait();
            registerStage.close();
        }

    }













    private boolean validInput(){
        String errorMessage = "";
        if (firstNameText.getText() == null || firstNameText.getText().length()  == 0){
            errorMessage += "Invalid first name\n";
        } if (lastNameText.getText() == null || lastNameText.getText().length()  == 0){
            errorMessage += "Invalid last name\n";
        }if (ssnText.getText() == null || ssnText.getText().length()  == 0  ){
            errorMessage += "Invalid Social security Number\n";
        }if (gPText.getText() == null || gPText.getText().length()  == 0){
            errorMessage += "Invalid General Practitioner\n";
        }
        if (errorMessage.length() == 0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid form");
            alert.setHeaderText("Please fill all fields");
            alert.initOwner(registerStage);
            alert.setContentText(errorMessage);
            return false;
        }

    }



















}
