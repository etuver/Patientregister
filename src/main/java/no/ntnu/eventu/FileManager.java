package no.ntnu.eventu;


import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Class to manage writing and saving data from patientregister to file
 * @author Eventu
 */
public final class FileManager implements FileInterface {


    /**
     * Open file with register
     * Shows a FileChooser dialog to open file
     * If file type is of csv or CSV, reads file and fille patientregister
     * If file type is NOT csv or CSV, shows a dialog and return to primary window
     * @param patientRegister the patientregister which filedata will be added to
     * @throws FileNotFoundException
     */
    public void openFile(PatientRegister patientRegister) throws FileNotFoundException, IllegalArgumentException {
        FileChooser fileChooser = new FileChooser();
        File file = new File(fileChooser.showOpenDialog(new Stage()).getAbsolutePath());
        if (getFileExtension(file.getAbsoluteFile().toString()).equals("CSV") || getFileExtension(file.getAbsoluteFile().toString()).equals("csv")) { //Only reads if file is csv or CSV
            Scanner scanner = new Scanner(new File(file.getAbsolutePath()));
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] lineInfo = line.split(";");
                    String firstName = lineInfo[0];
                    String lastName = lineInfo[1];
                    String gpractitioner = lineInfo[2];
                    String ssn = lineInfo[3];
                    if (ssn.length() == 10){
                        ssn = "0"+ssn;
                    }
                        patientRegister.registerPatient(firstName, lastName, ssn, gpractitioner);
                    }
                }
            scanner.close();

        } else {  //If file type is not csv
            Alert errorLoadingAlert = new Alert(Alert.AlertType.ERROR);
            errorLoadingAlert.setHeaderText("Invalid filetype chosen");
            errorLoadingAlert.setContentText("Please choose a .csv file");
            errorLoadingAlert.showAndWait();
        }
    }


    /**
     * Save the register to chosen file
     * Shows a FileChooser dialog to choose file
     *
     * @param patientRegister the patientregister to save
     * @throws FileNotFoundException
     * @throws NullPointerException
     */
    public void saveFile(PatientRegister patientRegister) throws FileNotFoundException, NullPointerException {
        FileChooser fileChooser = new FileChooser();
        File file = new File(fileChooser.showSaveDialog(new Stage()).getAbsolutePath());
        PrintWriter writer = new PrintWriter(file);
        writer.println("First Name" + ";" + "Last Name" + ";" + "General Practitioner" + ";" + "Social Security Number" + ";" + "Diagnosis");
        for (int i = 0; i < patientRegister.getRegisterSize(); i++) {
            Patient p = patientRegister.getPatients().get(i);
            writer.println(p.toString());
        }
        writer.flush();
        writer.close();
    }


    /**
     * simple Method to check filetype of a name
     *
     * @param filename the filename to check, must include full filename with extension
     * @return returns the extension of the file
     */
    private String getFileExtension(String filename) {
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i >= 0) {
            extension = filename.substring(i + 1);
        }
        return extension;
    }


}
