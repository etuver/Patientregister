package no.ntnu.eventu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for Patient class
 */
class PatientTest {



    /**
     * Testing creating a new PAtient and asserting all fields are as expected
     * also testing getters
     */
    @Test
    public void testNewPatient(){
        Patient pat = new Patient("Harry","Potter","16010012345", "Lang nese");
        assertEquals("Harry", pat.getFirstName());
        assertEquals("Potter", pat.getLastName());
        assertEquals("16010012345", pat.getSsn());
        assertEquals("Lang nese", pat.getGeneralPractitioner());
    }



    /**
     * Testing creating a new patient with empty fields
     * asserting they throw exceptions accordingly
     */
    @Test
    public void testNewPatientNull(){
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("", "Potter", "12129112345", "");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Harry", "", "12121212345", "");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Harry", "Potter", "", "");});
    }


}