package ca.concordia.soen6441.project.log;

import ca.concordia.soen6441.project.interfaces.log.Observer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class represents the observer. It writes to a file whenever
 * the observable state changes
 */
public class LogWriter implements Observer, Serializable {

    /**
     * Constructor of LogWriter
     * @param p_logEntryBuffer this is the observable. LogWriter get attach to the observable
     */
    public LogWriter(LogEntryBuffer p_logEntryBuffer) {
        p_logEntryBuffer.attach(this);
    }
    private final String d_logFilename = "log.txt";

    /**
     * Each time the observable state changes, it notifies LogWriter which write to a file
     * @param p_observable object of the Observable class
     */
    @Override
    public void update(Observable p_observable) {
        try (FileWriter writer = new FileWriter(d_logFilename)) {
            writer.write(((LogEntryBuffer) p_observable).getLogInfo().toString());
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
