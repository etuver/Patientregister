package no.ntnu.eventu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    PatientRegister patientRegister = new PatientRegister();
    FileManager fileManager = new FileManager();

    /**
     * Testing to open a file and add to register successfully
     */
    @Test
    public void testOpenFile(){

        try {
            fileManager.openFile(patientRegister);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        }
        assertEquals(7, patientRegister.getRegisterSize());
    }

}