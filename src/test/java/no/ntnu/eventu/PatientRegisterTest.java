package no.ntnu.eventu;

import no.ntnu.eventu.Exception.RemoveException;
import no.ntnu.eventu.Patient;
import no.ntnu.eventu.PatientRegister;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegisterTest {
    PatientRegister register = new PatientRegister();

    @BeforeEach
    public void setup(){
    }


    /**
     * Positive test
     * Testing registering new patient successfully
     */
    @Test
    public void testTegisterPatient(){
        register.registerPatient("Donald", "Trump","16019112345",   "Doctor Proctor");
        assertEquals(1, register.getRegisterSize());
    }


    /**
     * Negative test
     * Testing registering patient with invalid ssn (invalid date)
     * first two digits causing the exception
     */
    @Test
    public void testRegisterPatientInvalidDate(){
        assertThrows(IllegalArgumentException.class,
                () -> { register.registerPatient("Donald", "Trump", "00019112345", "Doctor Proctor");});
    }

    /**
     * Negative test
     * Testing register patient with invalid character in ssn
     * x in first number of ssn causes the exception
     */
    @Test
    public void testRegisterPatientInvalidCharacter(){
        assertThrows(IllegalArgumentException.class,
                () -> { register.registerPatient("Donald", "Trump", "x0019112345", "Doctor Proctor");});
    }

    /**
     * Negative test
     * Testing register patient with empty fields
     */
    @Test
    public void testRegisterPatientAnyFieldEmpty(){
        assertThrows(IllegalArgumentException.class,
                    () -> { Patient pat = new Patient("", "Trump", "00019112345", "Doctor Proctor");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Donald", "", "00019112345", "Doctor Proctor");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Donald", "Trump", "", "Doctor Proctor");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Donald", "Trump", "", "");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Donald", "Trump", "", "Doctor Proctor", "");});
    }


    /**
     * Negative test
     * Testing to register person with existing ssn
     */
    @Test
    public void testRegisterExistingPatient(){
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        assertThrows(IllegalArgumentException.class,
                () -> { register.registerPatient("Donald", "Duck", "24120012345", "Doctor Proctor")  ;});
        assertEquals(1, register.getRegisterSize());
    }


    /**
     * Positive test
     * Testing to successfully remove patient from the register
     */
    @Test
    public void testRemovePatient() throws RemoveException {
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        register.registerPatient( "Donald", "Duck","16120012345",  "Doctor Proctor");
        register.removePatient("24120012345"); // Remove Mikke Mus
        assertEquals(1, register.getRegisterSize());
    }

    /**
     * Negative test
     * Testing to remove a patient with ssn null
     */
    @Test
    public void testRemovePatientNull(){
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        register.registerPatient( "Donald", "Duck","16120012345",  "Doctor Proctor");
        assertThrows(RemoveException.class,
                () -> { register.removePatient("");});
        assertEquals(2,register.getRegisterSize());
    }

    /**
     * Negative test
     * Testing to remove person not in the list
     */
    @Test
    public void testRemovePatientNotExisting(){
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        register.registerPatient( "Donald", "Duck","16120012345",  "Doctor Proctor");
        assertThrows(RemoveException.class,
                () -> { register.removePatient("02030012345");});
        assertEquals(2,register.getRegisterSize());
    }


    /**
     * Positive test
     * Testing to confirm a valid ssn is returning true
     */
    @Test
    public void testSsnValidator(){
        assertTrue(register.ssnValidator("16019135954"));
    }


    /**
     * Negative test
     * Testing to confirm an invalid ssn is returning false
     */
    @Test
    public void testSsnvalidatorFalse(){
        assertFalse(register.ssnValidator("70091212345")); // a date can`t start with 70
    }

    /**
     * Negative test
     * Testing to ensure a null ssn will return false
     */
    @Test
    public void testSsnvalidatorNull(){
        assertFalse(register.ssnValidator("")); // a date can`t start with 70
    }


    /**
     * Test getting size of the register
     */
    @Test
    public void testGetRegisterSize(){
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        register.registerPatient( "Donald", "Duck","16120012345",  "Doctor Proctor");
        assertEquals(2, register.getRegisterSize());
    }

}
