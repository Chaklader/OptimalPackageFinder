package exceptions;

/**
 * Created by Chaklader on Feb, 2021
 */
public class ValidationException extends BaseException {


    public ValidationException(String message, int lineNumber) {

        super("Invalid values found on line " + lineNumber + " :" + System.lineSeparator() + message, lineNumber);
    }
}

