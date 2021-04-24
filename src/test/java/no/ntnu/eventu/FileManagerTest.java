package no.ntnu.eventu;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {



    /**
     * Testing to open a file and add to register
     */
    @Disabled
    public void testOpenFile(){
        PatientRegister patientRegister = new PatientRegister();
        FileManager fileManager = new FileManager();
        try {
            fileManager.openFile(patientRegister);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        }
        assertEquals(15,patientRegister.getRegisterSize());
    }

}