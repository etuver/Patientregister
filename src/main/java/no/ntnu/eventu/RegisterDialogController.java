package no.ntnu.eventu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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


    private Stage registerStage;
    PatientRegister patientRegister = new PatientRegister();


    @FXML
    private void initialize(){
        doneRegisterBtn.setOnAction(actionEvent -> handleRegisterOk());
        cancelRegisterBtn.setOnAction(actionEvent -> {
            try {
                switchToPrimary();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }


    public void setRegisterStage(Stage registerStage){
        this.registerStage = registerStage;
        patientRegister.registerPatient( ssnText.getText(),firstNameText.getText(), lastNameText.getText(), gPText.getText());
    }

    public void handleRegisterOk(){
        if (validInput() && patientRegister.ssnValidator(ssnText.getText())){
            try {
                patientRegister.registerPatient(ssnText.getText(),firstNameText.getText(), lastNameText.getText(), gPText.getText());
                Alert okAlert = new Alert(Alert.AlertType.INFORMATION);
                okAlert.setHeaderText("Success!");
                okAlert.setContentText("Patient successfully registered");
                okAlert.showAndWait();
            }catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
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
            alert.showAndWait();
            return false;
        }
    }



















}
