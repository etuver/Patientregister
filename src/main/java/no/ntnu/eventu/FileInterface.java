package no.ntnu.eventu;

import java.io.FileNotFoundException;

public interface FileInterface {

    public void openFile(PatientRegister patientRegister) throws FileNotFoundException;

    public void saveFile(PatientRegister patientRegister) throws FileNotFoundException;


}
