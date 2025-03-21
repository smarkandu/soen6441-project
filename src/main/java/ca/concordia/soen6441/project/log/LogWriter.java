package ca.concordia.soen6441.project.log;

import ca.concordia.soen6441.project.interfaces.log.Observer;

import java.io.FileWriter;
import java.io.IOException;

public class LogWriter implements Observer {

    public LogWriter(LogEntryBuffer p_logEntryBuffer) {
        p_logEntryBuffer.attach(this);
    }
    private String d_logFilename = "log.txt";

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
