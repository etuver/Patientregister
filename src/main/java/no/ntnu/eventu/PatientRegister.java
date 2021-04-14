package no.ntnu.eventu;

import javafx.css.Match;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientRegister {

    public ArrayList<Patient> patients = new ArrayList<>();


    /**
     * Method to register a new patient
     * Checks if patient with chosen ssn allready exists
     * Checks if chosen ssn is a valid ssn
     * @param ssn sosial security number
     * @param firstName first name
     * @param lastName last name
     * @param diagnosis diagnosis
     * @param generalPractitioner name of general practitioner
     */
    public void registerPatient(String ssn, String firstName, String lastName, String diagnosis, String generalPractitioner){
        for (int i = 0; i < patients.size(); i++){
            if (patients.get(i).getSsn().equals(ssn)){
                System.out.println("A patient with chosen ssn already exists");
            }
        }
        Patient newPatient = new Patient(ssn, firstName, lastName, diagnosis ,generalPractitioner);
        if (!ssnValidator(ssn)){
            System.out.println("Invalid ssn. A ssn has the format ddmmyynnnnn");
        }else  {
            patients.add(newPatient);
        }
    }

    public boolean registerDiagnosis(String ssn, String diagnosis){
        boolean success = false;
        for (int i = 0; i < patients.size(); i++){
            if (patients.get(i).getSsn().equals(ssn)){
               Patient pat =  patients.get(i);
                pat.setDiagnosis(diagnosis);
                success = true;
            }
        }
        return success;
    }


    public void removePatient(String ssn){
        if (ssn == null || ssn == ""){
            throw new NullPointerException("ssn cant be null");
        }
        for (int i = 0; i < patients.size(); i++){
            if (patients.get(i).getSsn().equals(ssn)){
                patients.remove(i);
            }
        }
    }



    /**
     * SSN-validator
     * Using regex might be a bit overkill on this assignment, but it
     * @param ssn
     * Two first digits is the day. A valid day is any from 01-31. not taking leap year, months or D-nr into consideration
     * Digit 3 and 4 is month. A valid month is from 01-12
     * Digit 5 and 6 is the year. A valid year is any combination of two digits from 00-99
     * The rest of the ssn is any combination of five digits. Not taking individual or control numbers into consideration
     * @return true if valid ssn with the given conditions, else return false
     */
    public boolean ssnValidator(String ssn){
        String regex = "^([1-9]|1[0-9]|2[0-9]|30|31)(0[1-9]|1[0-2])(\\d\\d)(\\d{5})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ssn);
        return m.matches();
    }



    //editpatient





















}
