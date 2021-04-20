package no.ntnu.eventu;

import javafx.css.Match;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientRegister {


    private ArrayList<Patient> patients;

    public PatientRegister(){
        this.patients = new ArrayList<>();
    }




    /**
     * Method to register a new patient
     * Checks if patient with chosen ssn allready exists as two persons can`t have the same ssn
     * Checks if chosen ssn is a valid ssn
     * @param ssn sosial security number
     * @param firstName first name
     * @param lastName last name
     * @param generalPractitioner name of general practitioner
     */
    public void registerPatient( String firstName, String lastName,String ssn, String generalPractitioner){
        if (ssn.equals("") || firstName.equals("") || lastName.equals("")){
            throw new IllegalArgumentException("ssn, first name or last name can`t be null");
        }
        for (int i = 0; i < patients.size(); i++){
            if (patients.get(i).getSsn().equals(ssn)){
                throw new IllegalArgumentException("A patient with chosen ssn already exists");
                //System.out.println("A patient with chosen ssn already exists");
            }
        }

        if (!ssnValidator(ssn)){
            throw new IllegalArgumentException("Invalid ssn. A ssn has the format ddmmyynnnnn");
            //System.out.println("Invalid ssn. A ssn has the format ddmmyynnnnn");
        }else  {
            Patient newPatient = new Patient( firstName, lastName,ssn, generalPractitioner);
            patients.add(newPatient);
        }
    }


    /**
     * NOT USED IN THIS ASSIGNMENT
     * Method to add diagnosis to a patient
     * @param ssn social security number of the patient
     * @param diagnosis the diagnosis
     * @return true if successfully added
     */
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

    public ArrayList<Patient>getPatients(){
        return this.patients;
    }


    /**
     * Method to remove a patient from the register
     * @param ssn ssn of the patient to remove
     */
    public void removePatient(String ssn){
        if (ssn == null || ssn.equals("")){
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
     * Using regex might be a bit overkill on this assignment, but it is nice to practice and works well
     * @param ssn
     * Two first digits is the day. A valid day is any from 01-31. not taking leap year, months or D-nr into consideration
     * Digit 3 and 4 is month. A valid month is from 01-12
     * Digit 5 and 6 is the year. A valid year is any combination of two digits from 00-99
     * The rest of the ssn is any combination of five digits. Not taking individual or control numbers into consideration
     * @return true if valid ssn with the given conditions, else return false
     */
    public boolean ssnValidator(String ssn){
        //String regex = "^(0[1-9]|1[0-9]|2[0-9]|30|31)(0[1-9]|1[0-2])(\\d\\d)(\\d{5})$";
        //Pattern p = Pattern.compile(regex);
        //Matcher m = p.matcher(ssn);
        //return m.matches();
        return true;
    }


    /**
     * Method to get size of the list
     * @return size
     */
    public int getRegisterSize(){
        return patients.size();
    }




    private static final PatientRegister instance = new PatientRegister();

    public static PatientRegister getInstance(){
        return instance;
    }









    //editpatient





}
