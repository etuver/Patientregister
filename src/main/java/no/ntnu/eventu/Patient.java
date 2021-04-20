package no.ntnu.eventu;


public class Patient {
    String ssn; //Social security number. Unique for any person
    String firstName; // First name of the patient
    String lastName; // Last name of the patient
    String diagnosis; // The patients diagnosis
    String generalPractitioner; // The patients general practitioner

    public Patient( String firstName, String lastName, String ssn, String generalPractitioner) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.diagnosis = diagnosis;
        this.generalPractitioner = generalPractitioner;
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

    public String toString(){
        return firstName +";"+lastName+";"+generalPractitioner+";"+ssn+";"+diagnosis;
    }


}
