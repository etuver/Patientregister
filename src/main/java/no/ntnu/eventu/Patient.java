package no.ntnu.eventu;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A class representing a patient
 * @Author Eventu
 */

public class Patient {
   private String ssn; //Social security number. Unique for any person
   private String firstName; // First name of the patient
   private String lastName; // Last name of the patient
   private String diagnosis; // The patients diagnosis
   private String generalPractitioner; // The patients general practitioner


    /**
     * Method to create a patient with no diagnosis
     * throws illegalargumentexception if any name or Social security number is invalid
     * @param firstName the first name can only consist letters and blank spaces
     * @param lastName the last name can only consist letters and blank spaces
     * @param ssn the ssn can only be 11 digits and with a format of DDMMYYNNNNNNN
     * @param generalPractitioner name of the general practitioner can only consist letters and blank spaces
     */
    public Patient(String firstName, String lastName, String ssn, String generalPractitioner) {
        if (!nameValidator(firstName)){
            throw new IllegalArgumentException("First name must be letters, space or dots.");
        } else if (!nameValidator(lastName)) {
            throw new IllegalArgumentException("Last name must be letters, space or dots. ");
        } else if (!nameValidator(generalPractitioner)) {
            throw new IllegalArgumentException("General Practitioner must be letters, space or dots.");
        } else if (!ssnValidator(ssn)) {
            throw new IllegalArgumentException("Invalid ssn. A ssn has the format ddmmyynnnnn");
        } else {
            this.ssn = ssn;
            this.firstName = firstName;
            this.lastName = lastName;
            this.diagnosis = diagnosis;
            this.generalPractitioner = generalPractitioner;
        }
    }

    /**
     * Method to create a patient with a diagnosis
     * throws illegalargumentexception if any name or Social security number is invalid
     * @param firstName the first name can only consist letters and blank spaces
     * @param lastName the last name can only consist letters and blank spaces
     * @param ssn the ssn can only be 11 digits and with a format of DDMMYYNNNNNNN
     * @param generalPractitioner name of the general practitioner can only consist letters and blank spaces
     */
    public Patient(String firstName, String lastName, String ssn, String generalPractitioner, String diagnosis) {
        if (!nameValidator(firstName)){
            throw new IllegalArgumentException("First name must be letters, space or dots.");
        } else if (!nameValidator(lastName)) {
            throw new IllegalArgumentException("Last name must be letters, space or dots.");
        } else if (!nameValidator(generalPractitioner)) {
            throw new IllegalArgumentException("General Practitioner must be letters, space or dots.");
        } else if (!ssnValidator(ssn)) {
            throw new IllegalArgumentException("Invalid ssn. A ssn has the format ddmmyynnnnn");
        } else {
            this.ssn = ssn;
            this.firstName = firstName;
            this.lastName = lastName;
            this.diagnosis = diagnosis;
            this.generalPractitioner = generalPractitioner;
        }
    }


    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    /**
     * Method to  set the first name of a patient
     * Will only be added if the name is valid, else throws a ILlegalArgumentException
     * @param firstName the new first name of the patient
     */
    public void setFirstName(String firstName) {
        if (!nameValidator(firstName)) {
            throw new IllegalArgumentException("First name must be letters, space or dots.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    /**
     * Method to set the last name of a patient
     * Will only be added if the name is valid, else throws a ILlegalArgumentException
     * @param lastName the new last name of the patient
     */
    public void setLastName(String lastName) {
     if (!nameValidator(lastName)) {
        throw new IllegalArgumentException("Last name must be letters, space or dots. ");
    }
        this.lastName = lastName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getGeneralPractitioner() {
        return generalPractitioner;
    }

    /**
     * method to set the name of a general practitioner for a patient
     * Will only be added if the name is valid, else throws a ILlegalArgumentException
     * @param generalPractitioner the new name of the general practitoner
     */
    public void setGeneralPractitioner(String generalPractitioner) {
        if (!nameValidator(generalPractitioner)) {
            throw new IllegalArgumentException("General Practitioner must be letters, space or dots.");
        }
        this.generalPractitioner = generalPractitioner;
    }

    /**
     * A tostring for the person, formatted with semicolons to fit writing to csv file
     * @return a person
     */
    public String toString() {
        return firstName + ";" + lastName + ";" + generalPractitioner + ";" + ssn + ";" + diagnosis;
    }


    /**
     * SSN-validator making sure the person is a real person
     *
     * @param ssn Two first digits is the day. A valid day is any from 01-31. not taking leap year, months or D-nr into consideration
     *            Digit 3 and 4 is month. A valid month is from 01-12
     *            Digit 5 and 6 is the year. A valid year is any combination of two digits from 00-99
     *            The rest of the ssn is any combination of five digits. Not taking individual or control numbers into consideration
     * @return true if valid ssn with the given conditions, else return false
     */
    public boolean ssnValidator(String ssn) {
        String regex = "^(0[1-9]|1[0-9]|2[0-9]|30|31)(0[1-9]|1[0-2])(\\d\\d)(\\d{5})$";
        //String regex = "^((\\d\\d)(\\d{9})$)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ssn);
        return m.matches();
    }

    /**
     * Checks if a name is valid
     * A name is valid if it only consists of letters or blank spaces and can not be blank
     * @param name the name to check
     * @return true if valid, else false
     */
    public boolean nameValidator(String name){
        return (name.matches("^[øØæÆåÅa-zA-Z.\\s]*$") && !name.isBlank());
    }


}
