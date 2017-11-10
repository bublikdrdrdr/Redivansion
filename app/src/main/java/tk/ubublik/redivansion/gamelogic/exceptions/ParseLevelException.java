package tk.ubublik.redivansion.gamelogic.exceptions;

/**
 * Created by Bublik on 10-Nov-17.
 */

public class ParseLevelException extends Exception {
    public ParseLevelException() {
    }

    public ParseLevelException(String message) {
        super(message);
    }

    public ParseLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseLevelException(Throwable cause) {
        super(cause);
    }
}
