package org.roda.asserver.db;

/**
 * Created by kvvn on 3/23/15.
 */
public class DataBaseDriverException extends Exception {

    private static final long serialVersionUID = 161607L;

    public DataBaseDriverException(String message) {
        super(message);
    }

    public DataBaseDriverException(Throwable cause) {
        super(cause);
    }

    public DataBaseDriverException(String message, Throwable cause) {
        super(message, cause);
    }

}
