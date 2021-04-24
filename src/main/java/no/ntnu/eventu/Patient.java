package no.ntnu.eventu;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patient {
    String ssn; //Social security number. Unique for any person
    String firstName; // First name of the patient
    String lastName; // Last name of the patient
    String diagnosis; // The patients diagnosis
    String generalPractitioner; // The patients general practitioner
    public Patient(String firstName, String lastName, String ssn, String generalPractitioner) {
        //if (firstName == null || firstName.isBlank()) {
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


    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
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

    public void setGeneralPractitioner(String generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
    }

    public String toString() {
        return firstName + ";" + lastName + ";" + generalPractitioner + ";" + ssn + ";" + diagnosis;
    }


    /**
     * SSN-validator
     * Using regex might be a bit overkill on this assignment, but it is nice to practice and works well
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

    public boolean nameValidator(String name){
        return (name.matches("^[øØæÆåÅa-zA-Z.\\s]*$") && !name.isBlank());
    }


}
