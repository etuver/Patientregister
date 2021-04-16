package no.ntnu.eventu;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PrimaryController {


    //MenuItems
    public MenuItem importMenu;
    public MenuItem exportMenu;
    public MenuItem exitMenu;
    public MenuItem addMenu;
    public MenuItem editMenu;
    public MenuItem removeMenu;
    public Menu helpMenu;


    //Buttons
    @FXML
    private Button editPatientBtn;
    @FXML
    private Button addPatientBtn;
    @FXML
    private Button removePatientBtn;
    @FXML
    private Button loadBtn;
    @FXML
    private Button saveBtn;

    //Table
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> ssn, firstName, lastName, diagnosis, generalPractitioner;









    //Create a new PatientRegister
    private PatientRegister patientRegister = PatientRegister.getInstance();

    /**
     * Just some test data before implementing filehandling
     */
    public void fillWithTestData(){
        patientRegister.registerPatient("Donald", "Trump",  "16019295843", "A poor guy");
        patientRegister.registerPatient( "Mikke", "Mus", "02019112345","Petter Smart");
    }




    @FXML
    private  void initialize(){


        TableColumn<Patient, String> firstNameCol = new TableColumn<>("First name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patientsTable.getColumns().add(firstNameCol);


        TableColumn<Patient, String> lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientsTable.getColumns().add(lastNameCol);


        TableColumn<Patient, String> ssnCol = new TableColumn<>("Social Security Number");
        ssnCol.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        patientsTable.getColumns().add(ssnCol);


        TableColumn<Patient, String> gPCol = new TableColumn<>("General Practitioner");
        gPCol.setCellValueFactory(new PropertyValueFactory<>("generalPractitioner"));
        patientsTable.getColumns().add(gPCol);


        TableColumn<Patient, String> diagnosisCol = new TableColumn<>("Diagnosis");
        diagnosisCol.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        patientsTable.getColumns().add(diagnosisCol);


        //fills table
        patientsTable.getItems().addAll(patientRegister.getPatients());


        removePatientBtn.setOnAction(actionEvent -> removePatient());

        exitMenu.setOnAction(actionEvent -> exitProgram());
        helpMenu.setOnAction(actionEvent -> helpDialog());
        loadBtn.setOnAction(actionEvent -> loadFile());
        addPatientBtn.setOnAction(actionEvent -> {
            try {
                switchToRegister();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    /**
     * Method to remove a patient from the register
     * Show Alert confirmDialog
     * If user press yes, patient will be removed from register
     * If user presses no it will cancel
     */
    public void removePatient(){
        int selectedIndex = patientsTable.getSelectionModel().getSelectedIndex();
        Alert removeAlert = new Alert(Alert.AlertType.CONFIRMATION,"Remove patient?" ,ButtonType.YES, ButtonType.NO);
        removeAlert.setTitle("Remove patient");
        removeAlert.setHeaderText("Are you sure you want to remove this patient from the register?\nThis can`t be undone!");
        removeAlert.setContentText("Press yes to confirm, no to cancel");
        removeAlert.showAndWait();
        if (removeAlert.getResult() == ButtonType.YES){
            patientsTable.getItems().remove(selectedIndex);
        }
    }






    /**
     * Switch to register new patient
     * @throws IOException
     */
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("registerDialog");
    }


    //For now just a method to fill table
    private void loadFile(){
        patientsTable.getItems().clear();
        patientsTable.getItems().addAll(patientRegister.getPatients());
    }



    /**
     * Method to exit program
     * imports class Alert to create a confirmation box before exiting
     */
    private void exitProgram(){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? \nAll unsaved progress will be lost!", ButtonType.YES, ButtonType.NO);
        exitAlert.setTitle("Confirm exit");
        exitAlert.setHeaderText("Exit application?");
        exitAlert.setAlertType(Alert.AlertType.WARNING);
        exitAlert.showAndWait();
        if (exitAlert.getResult() == ButtonType.YES){
            System.exit(0);
        }
    }


    /**
     * Shows the helpDialog
     */
    private void helpDialog(){
        Alert helpDialog = new Alert(Alert.AlertType.INFORMATION);
        helpDialog.setTitle("About Patient register");
        helpDialog.setHeaderText("This is a information dialog");
        helpDialog.setContentText("made by a great man");
        helpDialog.showAndWait();
    }

}
