package no.ntnu.eventu.Exception;


/**
 * Custom Exception RemoveException
 * Thrown if a remove method was unsuccessful
 * Is of type "Checked" and must be handled
 *
 * @Author Eventu
 **/
public class RemoveException extends Exception {


    /**
     * @param errorMessage the description of what went wrong
     */
    public RemoveException(String errorMessage) {
        super(errorMessage);
    }
}
