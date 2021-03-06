package no.ntnu.eventu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.eventu.Dialogs.AboutDialog;
import no.ntnu.eventu.Dialogs.ExitDialog;
import no.ntnu.eventu.Dialogs.NoPatientSelectedDialog;
import no.ntnu.eventu.Dialogs.RemoveDialog;
import no.ntnu.eventu.Exception.RemoveException;


/**
 * Controller class for the main window
 *
 * @Author Eventu
 */
public class PrimaryController {


    //MenuItems
    @FXML
    private MenuItem importMenu;
    @FXML
    private MenuItem exportMenu;
    @FXML
    private MenuItem exitMenu;
    @FXML
    private MenuItem addMenu;
    @FXML
    private MenuItem editMenu;
    @FXML
    private MenuItem removeMenu;
    @FXML
    private Menu helpMenu;


    // Statusbar at the bottom
    @FXML
    private Label statusLabel;
    @FXML
    private ImageView statusBarIcon;


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


    //PatientRegister
    PatientRegister patientRegister = PatientRegister.getInstance();

    //filemanager
    FileManager fileManager = new FileManager();

    //DialocFactory
    DialogFactory dialogFactory = new DialogFactory();

    private static String lastSaved = "";


    /**
     * Initializes each time the program starts
     */
    @FXML
    private void initialize() {


        // Fills table with columns
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

        // Update statuslabel every time primary controller loads
        updateStatusLabel();

        // Refresh table every time it is loaded
        refreshTable();

        //Double click a patient to edit
        patientsTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
                    try {
                        openEditPatient();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Actionevents on buttons
        // Buttons named Menu are the menuItems
        removePatientBtn.setOnAction(actionEvent -> removePatient());
        removeMenu.setOnAction(actionEvent -> removePatient());
        saveBtn.setOnAction(actionEvent -> saveFile());
        exportMenu.setOnAction(actionEvent -> saveFile());
        exitMenu.setOnAction(actionEvent -> exitProgram());
        helpMenu.setOnAction(actionEvent -> helpDialog());
        loadBtn.setOnAction(actionEvent -> loadFile());
        importMenu.setOnAction(actionEvent -> loadFile());
        editMenu.setOnAction(event -> {
            try {
                openEditPatient();
            } catch (IOException e) {
                statusLabel.setText("There was an error opening dialog " + e.getMessage());
            }
        });
        editPatientBtn.setOnAction(event -> {
            try {
                openEditPatient();
            } catch (IOException e) {
                statusLabel.setText("There was an error opening dialog " + e.getMessage());
            }
        });
        addMenu.setOnAction(actionEvent -> {
            try {
                switchToRegister();
            } catch (IOException e) {
                statusLabel.setText("There was an error opening dialog " + e.getMessage());
            }
        });
        addPatientBtn.setOnAction(actionEvent -> {
            try {
                switchToRegister();
            } catch (IOException e) {
                statusLabel.setText("There was an error opening dialog " + e.getMessage());
            }
        });

    }

    /**
     * Method which updates the status label
     */
    private void updateStatusLabel() {
        if (patientRegister.getRegisterSize() == 0) {
            statusLabel.setText("Welcome. Please load a file or register new patients");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/warning.png").toString()));
        } else if (lastSaved.isBlank() || lastSaved == null) {
            statusLabel.setText("File is not saved. Remember to save your data");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/warning.png").toString()));
        } else {
            statusLabel.setText("Last saved on " + lastSaved);
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/warning.png").toString()));
        }
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
        if (patientRegister.getRegisterSize() == 0) {
            Alert saveEmptyRegisterAlert = new Alert(Alert.AlertType.ERROR, "Do not save empty register");
            saveEmptyRegisterAlert.setTitle("Error");
            saveEmptyRegisterAlert.setHeaderText("Nothing to save");
            saveEmptyRegisterAlert.setContentText("You have no patients to save");
            saveEmptyRegisterAlert.showAndWait();
        } else {
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
     * If successfully loading, updates statusbar with successful message,
     * if unsuccessful, update statusbar with errormessage.
     */
    private void loadFile() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = now.format(formatter);
        try {
            fileManager.openFile(patientRegister);
            statusLabel.setText("Status: File successfully loaded on " + currentTime + "           Do not forget to save your changes!");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/success2.png").toString()));
        } catch (FileNotFoundException f) {
            statusLabel.setText("Could not load file. File not found or illegal filetype");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/error.png").toString()));
        } catch (NullPointerException n) {
            statusLabel.setText("Could not load file. Operation aborted");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/error.png").toString()));
        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/error.png").toString()));
        } catch (ArrayIndexOutOfBoundsException a) {
            statusLabel.setText("Error loading file, invalid data");
            statusBarIcon.setImage(new Image(this.getClass().getResource("images/error.png").toString()));
        }
        refreshTable();
    }


    /**
     * Method to remove a patient from the register
     * If no patient in the table is selected, show a dialog box to tell user to select a patient first
     * Opens a confirm dialog box
     * If user press yes, patient will be removed from register
     * If user presses no it will cancel
     */
    public void removePatient() {
        int selectedIndex = patientsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            NoPatientSelectedDialog noSelected = (NoPatientSelectedDialog) dialogFactory.getDialog(DialogFactory.DialogType.NoSelected);
            noSelected.getDialog().showAndWait();
        } else {
            RemoveDialog removeDialog = (RemoveDialog) dialogFactory.getDialog(DialogFactory.DialogType.Remove);
            Optional<ButtonType> result = removeDialog.getDialog().showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {

                // Get selected patient from table
                TablePosition pos = patientsTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Patient patientToRemove = patientsTable.getItems().get(row);
                String deleteSsn = patientToRemove.getSsn();
                try {
                    patientRegister.removePatient(deleteSsn);
                } catch (RemoveException i) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error removing patient", ButtonType.OK);
                    errorAlert.setHeaderText("Unable to remove patient");
                    errorAlert.setContentText(i.getMessage() + "\nPlease try again.");
                    errorAlert.showAndWait();
                }
            }
        }
        refreshTable();
    }


    /**
     * Open edit patient dialog
     *
     * @throws IOException
     */
    @FXML
    private void openEditPatient() throws IOException {
        int selectedIndex = patientsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) { // If no in table patient is selected
            NoPatientSelectedDialog noSelected = (NoPatientSelectedDialog) dialogFactory.getDialog(DialogFactory.DialogType.NoSelected);
            noSelected.getDialog().showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editDialog.fxml"));
            Parent root = loader.load();
            EditPatientController editPatientController = loader.getController();

            //Get selected patient
            TablePosition pos = patientsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Patient selectedPatient = patientsTable.getItems().get(row);
            String editSsn = selectedPatient.getSsn();
            editPatientController.editPatient(editSsn);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Patient");
            stage.showAndWait();
            refreshTable();
        }
    }


    /**
     * Opens the register new patient dialog
     *
     * @throws IOException
     */
    @FXML
    private void switchToRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registerDialog.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Register new Patient");
        stage.showAndWait();
        refreshTable();
    }


    /**
     * Opens a dialog when menuitem help is pressed
     * Contains information about the app and link to GitLAb repo
     */
    private void helpDialog() {
        AboutDialog aboutDialog = (AboutDialog) dialogFactory.getDialog(DialogFactory.DialogType.About);
        aboutDialog.getDialog().showAndWait();
    }


    /**
     * Method to exit program
     * imports class Alert to create a confirmation box before exiting
     */
    private void exitProgram() {
        ExitDialog exitDialog = (ExitDialog) dialogFactory.getDialog(DialogFactory.DialogType.Exit);
        if (exitDialog.getDialog().showAndWait().get() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }


}
