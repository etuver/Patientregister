package no.ntnu.eventu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import no.ntnu.eventu.Exception.RemoveException;


public class PrimaryController {


    //MenuItems
    public MenuItem importMenu;
    public MenuItem exportMenu;
    public MenuItem exitMenu;
    public MenuItem addMenu;
    public MenuItem editMenu;
    public MenuItem removeMenu;
    public Menu helpMenu;


    // Statusbar at the bottom
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
    //@FXML
    //private TableColumn<Patient, String> ssn, firstName, lastName, diagnosis, generalPractitioner;


    //Create a new PatientRegister
    PatientRegister patientRegister = PatientRegister.getInstance();

    //Initialise filemanager
    FileManager fileManager = new FileManager();

    private static String lastSaved = "";


    /**
     * Just some test data
     */
    public void fillWithTestData() {
        patientRegister.registerPatient("Donald", "Trump", "16019295843", "A poor guy");
        patientRegister.registerPatient("Mikke", "Mus", "02019112345", "Petter Smart");
    }


    @FXML
    private void initialize() {


        // Filles table with columns
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

       updateStatusLabel();


        // Refresh table every time it is loaded
        refreshTable();


        // Actionevents on buttons
        // Buttons named Menu are menuItems
        editPatientBtn.setOnAction(event -> editPatient());
        removePatientBtn.setOnAction(actionEvent -> removePatient());
        removeMenu.setOnAction(actionEvent -> removePatient());
        saveBtn.setOnAction(actionEvent -> saveFile());
        exportMenu.setOnAction(actionEvent -> saveFile());
        exitMenu.setOnAction(actionEvent -> exitProgram());
        helpMenu.setOnAction(actionEvent -> helpDialog());
        loadBtn.setOnAction(actionEvent -> loadFile());
        importMenu.setOnAction(actionEvent -> loadFile());
        addMenu.setOnAction(actionEvent -> {
            try {
                switchToRegister();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addPatientBtn.setOnAction(actionEvent -> {
            try {
                switchToRegister();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void updateStatusLabel(){
        if (patientRegister.getRegisterSize() == 0){
            statusLabel.setText("Welcome. Please load a file or register new patients");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/warning.png").toString()));
        }
        else if (lastSaved.isBlank() || lastSaved == null){
            statusLabel.setText("File is not saved. Remember to save your data");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/warning.png").toString()));
        }else {
            statusLabel.setText("Last saved on "+lastSaved);
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/warning.png").toString()));
        }
    }

    private void editPatient(){
        refreshTable();
    }


    /**
     * Method to refresh patientsTable
     * Removes all data from the table and adds all data from patientsRegister
     */
    private void refreshTable() {
        patientsTable.getItems().clear();
        patientsTable.getItems().addAll(patientRegister.getPatients());
    }


    /**
     * Method to save data to selected file
     * If successfully loading, updates statusbar with sucessfull message,
     * if unsuccessful, update statusbar with errormessage.
     */
    private void saveFile() {
        if (patientRegister.getRegisterSize() == 0){
            Alert saveEmptyRegisterAlert = new Alert(Alert.AlertType.ERROR,"Do not save empty register");
            saveEmptyRegisterAlert.setTitle("Error");
            saveEmptyRegisterAlert.setHeaderText("Nothing to save");
            saveEmptyRegisterAlert.setContentText("You have no patients to save");
            saveEmptyRegisterAlert.showAndWait();
        }else {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            fileManager.saveFile(patientRegister);
            lastSaved = now.format(formatter);
            statusLabel.setText("Status: File successfully saved on " + lastSaved + "!");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/success2.png").toString()));
        } catch (FileNotFoundException | NullPointerException fileNotFoundException) {
            statusLabel.setText("Could not save file. Please try again. Make sure file is not open in another program");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/error.png").toString()));
        }
        }
    }


    /**
     * Method to load data from a file
     * If successfully loading, updates statusbar with sucessfull message,
     * if unsuccessful, update statusbar with errormessage.
     */
    private void loadFile() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = now.format(formatter);
        //currentTime = now.format(formatter);
        try {
            fileManager.openFile(patientRegister);
            statusLabel.setText("Status: File successfully loaded on " + currentTime + "           Do not forget to save your changes!");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/success2.png").toString()));
        } catch (FileNotFoundException  f) {
            statusLabel.setText("Could not load file. File not found or illegal filetype");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/error.png").toString()));
        }catch (NullPointerException n){
            statusLabel.setText("Could not load file. Operation aborted");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/error.png").toString()));
        } catch (IllegalArgumentException e){
            statusLabel.setText(e.getMessage());
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/error.png").toString()));
        }
        refreshTable();
    }


    /**
     * Method to remove a patient from the register
     * Opens a confirmdialog
     * If user press yes, patient will be removed from register
     * If user presses no it will cancel
     */
    public void removePatient() {
        int selectedIndex = patientsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert removeErrorAlert = new Alert(Alert.AlertType.ERROR, "No patient selected", ButtonType.OK);
            removeErrorAlert.setTitle("Error");
            removeErrorAlert.setHeaderText("No patient selected");
            removeErrorAlert.setContentText("Mark a patient in the table before pressing remove");
            removeErrorAlert.showAndWait();
        } else {
            Alert removeAlert = new Alert(Alert.AlertType.CONFIRMATION, "Remove patient?", ButtonType.YES, ButtonType.NO);
            removeAlert.setTitle("Remove patient");
            removeAlert.setHeaderText("Are you sure you want to remove this patient from the register?\nThis can`t be undone!");
            removeAlert.setContentText("Press yes to confirm, no to cancel");
            removeAlert.showAndWait();
            if (removeAlert.getResult() == ButtonType.YES) {
                TablePosition pos = patientsTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Patient item = patientsTable.getItems().get(row);
                //TableColumn col = pos.getTableColumn();
                //String deleteSsn = (String) col.getCellObservableValue(item).getValue();
                String deleteSsn = item.getSsn();

                try {
                    patientRegister.removePatient(deleteSsn);
                } catch (RemoveException i){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR,"Error removing patient", ButtonType.OK);
                    errorAlert.setHeaderText("Unable to remove patient");
                    errorAlert.setContentText(i.getMessage()+"\nPlease try again.");
                    errorAlert.showAndWait();
                }


                //patientsTable.getItems().remove(selectedIndex);
            }
        }
        refreshTable();


    }


    /**
     * Switch to register new patient
     *
     * @throws IOException
     */
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("registerDialog");
    }


    /**
     * Opens a dialog when menuitem help is pressed
     * Contains information about the app and link to GitLAb rep
     * GitLab repo closed until due date for the assignment
     */
    private void helpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,  "loadAlert");
        alert.setHeaderText("loaderalert");
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
        alert.getDialogPane().contentProperty().set(fp);
        alert.setTitle("About");
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
    private void exitProgram() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? \nAll unsaved progress will be lost!", ButtonType.YES, ButtonType.NO);
        exitAlert.setTitle("Confirm exit");
        exitAlert.setHeaderText("Exit application?");
        exitAlert.setAlertType(Alert.AlertType.WARNING);
        exitAlert.showAndWait();
        if (exitAlert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }


}
