package exceptions;

import lombok.Getter;

/**
 * Created by Chaklader on Feb, 2021
 */
@Getter
public class BaseException extends RuntimeException {

    protected final int lineNumber;

    public BaseException(String message, int lineNumber) {

        super(message);
        this.lineNumber = lineNumber;
    }
}
