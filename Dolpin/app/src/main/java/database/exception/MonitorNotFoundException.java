package database.exception;

/**
 * Created by u14185 on 06/04/2016.
 */
public class MonitorNotFoundException extends MonitorException {

    public MonitorNotFoundException() {
        super("Monitor não encontrado");
    }
}
