package no.ntnu.eventu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for Patient class
 */
class PatientTest {



    /**
     * Testing creating a new PAtient without diagnosis
     * Asserting all fields are as expected
     * also testing getters
     */
    @Test
    public void testNewPatient(){
        Patient pat = new Patient("Harry","Potter","16010012345", "Lord Voldemort");
        assertEquals("Harry", pat.getFirstName());
        assertEquals("Potter", pat.getLastName());
        assertEquals("16010012345", pat.getSsn());
        assertEquals("Lord Voldemort", pat.getGeneralPractitioner());
    }

    /**
     * Test setters changing the attributes of a patient
     * asserting that the the info has changed from harry to mikkel
     */
    @Test
    public void testSetters(){
        Patient pat = new Patient("Harry","Potter","16010012345", "Lord Voldemort");
        assertEquals("Harry;Potter;Lord Voldemort;16010012345;null", pat.toString());
        pat.setFirstName("Mikkel");
        pat.setLastName("Rev");
        pat.setGeneralPractitioner("Pappa Flosshatt");
        pat.setDiagnosis("Skvetten");
        assertEquals("Mikkel;Rev;Pappa Flosshatt;16010012345;Skvetten", pat.toString());
    }


    /**
     * Test creating new patient with diagnosis
     * * Asserting all fields are as expected
     */
    @Test
    public void testNewPatientDiagnosed(){
        Patient pat = new Patient("Skrue","McDuck","02010012345", "Petter Smart", "Gullfeber");
        assertEquals("Skrue", pat.getFirstName());
        assertEquals("McDuck", pat.getLastName());
        assertEquals("02010012345", pat.getSsn());
        assertEquals("Petter Smart", pat.getGeneralPractitioner());
        assertEquals("Gullfeber", pat.getDiagnosis());
    }

    /**
     * Testing creating a new patient with empty fields
     * asserting they throw exceptions accordingly
     */
    @Test
    public void testNewPatientNull(){
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("", "Potter", "12129112345", "Lord Voldemort");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Harry", "", "12121212345", "Lord Voldemort");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Harry", "Potter", "", "Lord Voldemort");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Harry", "Potter", "12121212345", "");});
    }

    /**
     * Testing creating a patient with invalid inputs
     * asserting they throw exceptions accordingly
     */
    @Test
    public void testNewPatientInvalid(){
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("*", "Potter", "12129112345", "Lord Voldemort");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("Harry", "/", "12129112345", "Lord Voldemort");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("*", "Potter", "99010212345", "Lord Voldemort");});
        assertThrows(IllegalArgumentException.class,
                () -> { Patient pat = new Patient("harry", "Potter", "12129112345", "*");});
    }

    /**
     * Testing ssnValidator
     */
    @Test
    public void testSSNValidator(){
        Patient pat = new Patient("Harry","Potter","16010012345", "Lord Voldemort");
        assertTrue(pat.ssnValidator("01010012345"));
        assertFalse(pat.ssnValidator("00010012345")); //Illegal day
        assertFalse(pat.ssnValidator("01000012345")); //Illegal month
        assertFalse(pat.ssnValidator("0010012345")); //10 digits
        assertFalse(pat.ssnValidator("160100123456")); //12 digits
    }


    /**
     * Testing name validator
     */
    @Test
    public void testNameValidator(){
        Patient pat = new Patient("Harry","Potter","16010012345", "Lord Voldemort");
        assertTrue(pat.nameValidator("Skrue McDuck"));
        assertFalse(pat.nameValidator("*Skrue McDuck"));
        assertFalse(pat.nameValidator("1Skrue McDuck"));
        assertFalse(pat.nameValidator("-Skrue McDuck"));
    }




}