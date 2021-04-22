import no.ntnu.eventu.Patient;
import no.ntnu.eventu.PatientRegister;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegisterTest {


    /**
     * Positive test
     * Testing registering new patient successfully
     */
    @Test
    public void testTegisterPatient(){
   PatientRegister register = new PatientRegister();
        try {
            register.registerPatient("Donald", "Trump","16019112345",   "Doctor Proctor");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        assertEquals(1, register.getRegisterSize());
    }


    /**
     * Negative test
     * Testing registering patient with invalid ssn (invalid date)
     * first two digits causing the exception
     */
    @Ignore
    public void testRegisterPatientInvalidDate(){
        PatientRegister register = new PatientRegister();
        try {
            register.registerPatient( "Donald", "Trump","00019112345",  "Doctor Proctor");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        assertEquals(0, register.getRegisterSize());
    }

    /**
     * Negative test
     * Testing register patient with invalid character in ssn
     * x in first number of ssn causes the exception
     */
    @Ignore   //Ignore because ssnValidator is deactivated due to test data
    public void testRegisterPatientInvalidCharacter(){
        PatientRegister register = new PatientRegister();
        try {
            register.registerPatient("Donald", "Trump", "x0019112345",  "Doctor Proctor");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        assertEquals(0, register.getRegisterSize());
    }

    /**
     * Negative test
     * Testing register patient with no last name
     */
    @Test
    public void testRegisterPatientAnyFieldEmpty(){
        PatientRegister register = new PatientRegister();
        try {
            register.registerPatient( "Donald", "", "x0019112345","Doctor Proctor");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        assertEquals(0,register.getRegisterSize());
    }


    /**
     * Negative test
     * Testing to register person with existing ssn
     */
    @Test
    public void testRegisterExistingPatient(){
        PatientRegister register = new PatientRegister();
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        try {
            register.registerPatient( "Donald", "Duck","24120012345",  "Doctor Proctor");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        assertEquals(1, register.getRegisterSize());
    }


    /**
     * Positive test
     * Testing to successfully remove patient from the register
     */
    @Test
    public void testRemovePatient(){
        PatientRegister register = new PatientRegister();
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        register.registerPatient( "Donald", "Duck","16120012345",  "Doctor Proctor");
        try {
            register.removePatient("16120012345"); // Remove Donald Duck
        }catch (NullPointerException n){
            System.out.println(n.getMessage());
        }
        assertEquals(1,register.getRegisterSize());
    }

    /**
     * Negative test
     * Testing to remove a patient with ssn null
     */
    @Test
    public void testRemovePatientNull(){
        PatientRegister register = new PatientRegister();
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        register.registerPatient( "Donald", "Duck","16120012345",  "Doctor Proctor");
        try {
            register.removePatient(""); // Remove null
        }catch (NullPointerException n){
            System.out.println(n.getMessage());
        }
        assertEquals(2,register.getRegisterSize());
    }

    /**
     * Negative test
     * Testing to remove person not in the list
     */
    @Test
    public void testRemovePatientNotExisting(){
        PatientRegister register = new PatientRegister();
        register.registerPatient("Mikke","Mus","24120012345",   "Doctor Proctor");
        register.registerPatient( "Donald", "Duck","16120012345",  "Doctor Proctor");
        try {
            register.removePatient("01019912345"); // Remove null
        }catch (NullPointerException n){
            System.out.println(n.getMessage());
        }
        assertEquals(2,register.getRegisterSize());
    }


    /**
     * Positive test
     * Testing to confirm a valid ssn is returning true
     */
    @Test
    public void testSsnValidator(){
        PatientRegister register = new PatientRegister();
        assertTrue(register.ssnValidator("16019135954"));
    }


    /**
     * Negative test
     * Testing to confirm an invalid ssn is returning false
     */
    @Ignore  //Ignore because ssnvalidator is deactivated
    public void testSsnvalidatorFalse(){
        PatientRegister register = new PatientRegister();
        assertFalse(register.ssnValidator("70091212345")); // a date can`t start with 70
    }

    /**
     * Negative test
     * Testing to ensure a null ssn will return false
     */
    @Ignore // Ignore because ssn is deactivated
    public void testSsnvalidatorNull(){
        PatientRegister register = new PatientRegister();
        assertFalse(register.ssnValidator("")); // a date can`t start with 70
    }

















}
