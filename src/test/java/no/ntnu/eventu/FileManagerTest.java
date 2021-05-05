package no.ntnu.eventu;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FileManager
 *
 * @Author Eventu
 * Using JUnit5
 * Bit wierd testmethods because JUnit not supporting Filechooser dialogs
 */
class FileManagerTest {

    PatientRegister patientRegister = new PatientRegister();
    FileManager fileManager = new FileManager();

    /**
     * Testing to open a file and add to register successfully
     * JUnit not supporting Filechooser dialogs thus importing the method with a set file
     */
    @Test
    public void testOpenFile() throws FileNotFoundException {
        File file = new File("src/test/resources/testOpenfile.csv");
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
                String diagnosis = "";
                if (lineInfo.length > 4) { // If there is a diagnosis
                    diagnosis = lineInfo[4];
                } else { // if no diagnosis
                    diagnosis = "";
                }
                if (diagnosis.equals("null")) {
                    diagnosis = "";
                }
                if (ssn.length() == 10) {
                    ssn = "0" + ssn;
                }
                patientRegister.registerPatient(firstName, lastName, ssn, gpractitioner, diagnosis);
            }
        }
        scanner.close();
        assertEquals(7, patientRegister.getRegisterSize());
    }

    /**
     * Testing writing to file
     * bit scuffed way to run the tests but Junit does not seem to handle the Filechooser dialog
     * thus importing the method with a set file
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testSaveFile() throws FileNotFoundException {
        patientRegister.registerPatient("Mikke", "Mus", "24120012345", "Doctor Proctor");
        patientRegister.registerPatient("Donald", "Duck", "16120012345", "Doctor Proctor", "Langnebbet");
        File file = new File("src/test/resources/testSaveFile.csv");
        assertTrue(file.exists());
        PrintWriter writer = new PrintWriter(file);
        assertTrue(file.length() == 0); // Asserting that the file is empty
        writer.println("First Name" + ";" + "Last Name" + ";" + "General Practitioner" + ";" + "Social Security Number" + ";" + "Diagnosis");
        for (int i = 0; i < patientRegister.getRegisterSize(); i++) {
            Patient p = patientRegister.getPatients().get(i);
            writer.println(p.toString());
        }
        writer.flush();
        writer.close();
        assertFalse(file.length() == 0); // File is no longer null and has been filled

        // Create a new empty register
        PatientRegister newRegister = new PatientRegister();


        // Read the file and add to the new register
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
                String diagnosis = "";
                if (lineInfo.length > 4) { // If there is a diagnosis
                    diagnosis = lineInfo[4];
                } else { // if no diagnosis
                    diagnosis = "";
                }
                if (diagnosis.equals("null")) {
                    diagnosis = "";
                }
                if (ssn.length() == 10) {
                    ssn = "0" + ssn;
                }
                newRegister.registerPatient(firstName, lastName, ssn, gpractitioner, diagnosis);
            }
        }
        scanner.close();

        // Asserting the patients from file is added to the new register
        assertEquals(2, newRegister.getRegisterSize());
        assertEquals("Mikke;Mus;Doctor Proctor;24120012345;", newRegister.getPatients().get(0).toString());
        assertEquals("Donald;Duck;Doctor Proctor;16120012345;Langnebbet", newRegister.getPatients().get(1).toString());
    }

}