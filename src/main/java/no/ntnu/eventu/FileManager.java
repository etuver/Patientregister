package no.ntnu.eventu;


import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Class to manage writing and saving data from patientregister to file
 */
public final class FileManager implements FileInterface {


    /**
     * Open file with register
     * Shows a FileChooser dialog to open file
     * @param patientRegister the patientregister which filedata will be added to
     * @throws FileNotFoundException
     */
    public void openFile(PatientRegister patientRegister) throws FileNotFoundException, IllegalArgumentException {
        FileChooser fileChooser = new FileChooser();
        File file = new File(fileChooser.showOpenDialog(new Stage()).getAbsolutePath());
        //String filepath = file.getAbsolutePath();
        Scanner scanner = new Scanner(new File(file.getAbsolutePath()));
        while (scanner.hasNextLine()){
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();;
                String[] lineInfo = line.split(";");
                String firstName = lineInfo[0];
                String lastName = lineInfo[1];
                String ssn = lineInfo[3];
                String gpractitioner = lineInfo[2];
                Patient p = new Patient(firstName,lastName,ssn,gpractitioner);
                System.out.println(p.toString());
                patientRegister.registerPatient(firstName,lastName,ssn,gpractitioner);
            }
        }
        scanner.close();
        }


    /**
     * Save the register to chosen file
     * Shows a FileChooser dialog to choose file
     * @param patientRegister the patientregister to save
     * @throws FileNotFoundException
     * @throws NullPointerException
     */
    public void saveFile(PatientRegister patientRegister) throws FileNotFoundException, NullPointerException {
        FileChooser fileChooser = new FileChooser();
        File file = new File(fileChooser.showSaveDialog(new Stage()).getAbsolutePath());
            PrintWriter writer = new PrintWriter(file);
            for (int i = 0; i < patientRegister.getRegisterSize(); i++){
                Patient p = patientRegister.getPatients().get(i);
                writer.println(p.toString());
            }
            writer.flush();
            writer.close();
    }


}
