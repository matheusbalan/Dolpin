package database.exception;

/**
 * Created by u14185 on 07/04/2016.
 */
public class MonitorExistingException extends MonitorException {

    public MonitorExistingException() {
        super("Monitor já existente");
    }
}
