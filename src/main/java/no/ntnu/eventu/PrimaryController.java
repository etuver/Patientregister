package no.ntnu.eventu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PrimaryController {


    //MenuItems
    public MenuItem importMenu;
    public MenuItem exportMenu;
    public MenuItem exitMenu;
    public MenuItem addMenu;
    public MenuItem editMenu;
    public MenuItem removeMenu;
    public Menu helpMenu;
    public Pane statusbar;
    public Label statusLabel;
    public ImageView statusBarIcon;


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
        refreshTable();
        //patientsTable.getItems().addAll(patientRegister.getPatients());


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

    private void refreshTable(){
        patientsTable.getItems().clear();
        patientsTable.getItems().addAll(patientRegister.getPatients());
    }






    private void loadFile(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = "";
        currentTime = now.format(formatter);



        statusLabel.setText("Status: File successfully loaded on " + currentTime + "           Do not forget to save your changes!");
        statusBarIcon.setImage(new Image(this.getClass().getResource("images/success2.png").toString()));
    }


    /**
     * Method to remove a patient from the register
     * Show Alert confirmDialog
     * If user press yes, patient will be removed from register
     * If user presses no it will cancel
     */
    public void removePatient(){
        int selectedIndex = patientsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 ){
            Alert removeErrorAlert = new Alert(Alert.AlertType.ERROR,"No patient selected", ButtonType.OK);
            removeErrorAlert.setTitle("Error");
            removeErrorAlert.setHeaderText("No patient selected");
            removeErrorAlert.setContentText("Mark a patient in the table before pressing remove");
            removeErrorAlert.showAndWait();
        }else {
            Alert removeAlert = new Alert(Alert.AlertType.CONFIRMATION,"Remove patient?" ,ButtonType.YES, ButtonType.NO);
            removeAlert.setTitle("Remove patient");
            removeAlert.setHeaderText("Are you sure you want to remove this patient from the register?\nThis can`t be undone!");
            removeAlert.setContentText("Press yes to confirm, no to cancel");
            removeAlert.showAndWait();
            if (removeAlert.getResult() == ButtonType.YES){
                TablePosition pos =    patientsTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Patient item = patientsTable.getItems().get(row);
                TableColumn col = pos.getTableColumn();
                String deleteSsn = (String) col.getCellObservableValue(item).getValue();
                patientRegister.removePatient(deleteSsn);


                //patientsTable.getItems().remove(selectedIndex);
            }
        }
        refreshTable();


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
    private void helpDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "loadAlert");
        alert.setHeaderText("loaderalert");
        FlowPane fp = new FlowPane();
        Label label = new Label("Application created by " +
                "\n(C)Even Tuverud\n" +
                "2021-04-20\n" +
                "\n" +
                "Check out my GitLab for more:\n" +
                "Click link to copy to clipboard");
        Hyperlink hyperlink = new Hyperlink("https://gitlab.stud.idi.ntnu.no/eventu/patientregister");

        hyperlink.setOnAction(event -> {
                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(hyperlink.getText());
                    clipboard.setContent(content);
                }
                );



        fp.getChildren().addAll(label,hyperlink);
        alert.getDialogPane().contentProperty().set(fp);
        alert.setTitle("About Patient Register");
        alert.setHeaderText("Patient Register \nv0.1-SNAPSHOT");
        ImageView imageView = new ImageView(this.getClass().getResource("images/info.png").toString());
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        alert.setGraphic(imageView);
        alert.showAndWait();


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
    private void OLDhelpDialog(){
        Alert helpDialog = new Alert(Alert.AlertType.INFORMATION);
        Hyperlink gitlab = new Hyperlink("https://gitlab.stud.idi.ntnu.no/eventu/patientregister");
        gitlab.setOnAction(event -> gitlab.requestFocus());
        helpDialog.setTitle("About Patient register");
        helpDialog.setHeaderText("This is a information dialog");
        helpDialog.setContentText("made by a great man\nVersion 0.0.1" +"\nCheck out on GitLab:\n" + gitlab);
        helpDialog.showAndWait();
    }

}
