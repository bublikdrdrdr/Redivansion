package tk.ubublik.redivansion.gamelogic.exceptions;

/**
 * Created by Bublik on 10-Nov-17.
 */

public class LoadSettingsException extends RuntimeException {
    public LoadSettingsException() {
    }

    public LoadSettingsException(String message) {
        super(message);
    }

    public LoadSettingsException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadSettingsException(Throwable cause) {
        super(cause);
    }
}
