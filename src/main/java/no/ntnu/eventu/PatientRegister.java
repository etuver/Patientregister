package no.ntnu.eventu;


import no.ntnu.eventu.Exception.RemoveException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class PatientRegister
 *
 * @Author Eventu
 */
public class PatientRegister {


    private ArrayList<Patient> patients;

    public PatientRegister() {
        this.patients = new ArrayList<>();
    }


    /**
     * Method to register a new patient without diagnosis
     * Checks if patient with chosen ssn already exists as two persons can`t have the same ssn
     * Else tries to create a new patient
     *
     * @param ssn                 social security number
     * @param firstName           first name
     * @param lastName            last name
     * @param generalPractitioner name of general practitioner
     */
    public void registerPatient(String firstName, String lastName, String ssn, String generalPractitioner) throws IllegalArgumentException {
        if (patients.stream().anyMatch(c -> c.getSsn().equals(ssn))) {
            throw new IllegalArgumentException("Patient already exists");
        } else {
            patients.add(new Patient(firstName, lastName, ssn, generalPractitioner));
        }
    }

    /**
     * Method to register a new patient with diagnosis
     * Checks if patient with chosen ssn already exists as two persons can`t have the same ssn
     * Else tries to create a new patient
     *
     * @param ssn                 social security number
     * @param firstName           first name
     * @param lastName            last name
     * @param generalPractitioner name of general practitioner
     */
    public void registerPatient(String firstName, String lastName, String ssn, String generalPractitioner, String diagnosis) throws IllegalArgumentException {
        if (patients.stream().anyMatch(c -> c.getSsn().equals(ssn))) {
            throw new IllegalArgumentException("Patient already exists");
        } else {
            patients.add(new Patient(firstName, lastName, ssn, generalPractitioner, diagnosis));
        }
    }

    /**
     * method to get the list
     *
     * @returns the patients
     */
    public ArrayList<Patient> getPatients() {
        return this.patients;
    }


    /**
     * Method to remove a patient from the register
     * Throws RemoveException if the patient could not be removed
     *
     * @param ssn ssn of the patient to remove
     */
    public void removePatient(String ssn) throws RemoveException {
        boolean success = false;
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getSsn().equals(ssn)) {
                patients.remove(i);
                success = true;
            }
        }
        if (!success) {
            throw new RemoveException("Unable to remove patient. Patient not found");
        }
    }


    /**
     * SSN-validator
     *
     * @param ssn Two first digits is the day. A valid day is any from 01-31. not taking leap year, months or D-nr into consideration
     *            Digit 3 and 4 is month. A valid month is from 01-12
     *            Digit 5 and 6 is the year. A valid year is any combination of two digits from 00-99
     *            The rest of the ssn is any combination of five digits. Not taking individual or control numbers into consideration
     * @return true if valid ssn with the given conditions, else return false
     */
    public boolean ssnValidator(String ssn) {
        String regex = "^(0[1-9]|1[0-9]|2[0-9]|30|31)(0[1-9]|1[0-2])(\\d\\d)(\\d{5})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ssn);
        return m.matches();
    }


    /**
     * Method to get a patient by social security number
     * Used to find a specific patient
     *
     * @param ssn social security number to search for
     * @return Patient with entered ssn if found, else returns a patient null
     */
    public Patient getPatientBySsn(String ssn) {
        Patient pat = null;
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getSsn().equals(ssn)) {
                pat = patients.get(i);
            }
        }
        return pat;
    }

    /**
     * Method to get size of the list
     *
     * @return size
     */
    public int getRegisterSize() {
        return patients.size();
    }


    private static final PatientRegister instance = new PatientRegister();


    public static PatientRegister getInstance() {
        return instance;
    }


}

