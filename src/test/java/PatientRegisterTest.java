import no.ntnu.eventu.Patient;
import no.ntnu.eventu.PatientRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegisterTest {


    /**
     * Positive test
     * Testing registering new patient successfully
     */
    @Test
    public void registerPatientTest(){
   PatientRegister register = new PatientRegister("testregister");
        //Patient pat = new Patient("16019112345", "Donald", "Trump", "No need to explain", "Doctor Proctor");
        try {
            register.registerPatient("16019112345", "Donald", "Trump",  "Doctor Proctor");
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
    @Test
    public void registerPatientInvalidDateTest(){
        PatientRegister register = new PatientRegister("testRegister");
        try {
            register.registerPatient("00019112345", "Donald", "Trump",  "Doctor Proctor");
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
    @Test
    public void registerPatientInvalidCharacterTest(){
        PatientRegister register = new PatientRegister("testRegister");
        try {
            register.registerPatient("x0019112345", "Donald", "Trump",  "Doctor Proctor");
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
    public void registerPatientAnyFieldEmptyTest(){
        PatientRegister register = new PatientRegister("testRegister");
        try {
            register.registerPatient("x0019112345", "Donald", "", "Doctor Proctor");
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
    public void registerExistingPatientTest(){
        PatientRegister register = new PatientRegister("testRegister");
        register.registerPatient("24120012345", "Mikke", "Mus",  "Doctor Proctor");
        try {
            register.registerPatient("24120012345", "Donald", "Duck",  "Doctor Proctor");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        assertEquals(1, register.getRegisterSize());
    }




}
