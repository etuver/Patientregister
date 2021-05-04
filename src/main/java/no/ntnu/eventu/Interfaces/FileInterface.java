package no.ntnu.eventu.Interfaces;

import no.ntnu.eventu.PatientRegister;

import java.io.FileNotFoundException;

public interface FileInterface {
    public void openFile(PatientRegister patientRegister) throws FileNotFoundException;

    public void saveFile(PatientRegister patientRegister) throws FileNotFoundException;
}
