package exceptions;

import lombok.Getter;

/**
 * Created by Chaklader on Feb, 2021
 */
@Getter
public class ParsingException extends BaseException {


    private final String actualToken;

    private final String tokenName;


    public ParsingException(int lineNumber, String tokenName, String actualToken) {

        super("Invalid '" + tokenName + "' found on line " + lineNumber + " while parsing the token '" + actualToken + "'.", lineNumber);

        this.tokenName = tokenName;
        this.actualToken = actualToken;
    }
}
