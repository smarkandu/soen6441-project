package ca.concordia.soen6441.project.log;

import java.io.FileWriter;
import java.io.IOException;

public class LogWriter implements Observer {

    public LogWriter(LogEntryBuffer p_logEntryBuffer) {
        p_logEntryBuffer.attach(this);
    }

    @Override
    public void update(Observable p_observable) {
        LogEntryBuffer l_logEntryBuffer = (LogEntryBuffer) p_observable;
        try (FileWriter writer = new FileWriter("log.txt")) {
            writer.write(((LogEntryBuffer) p_observable).getLogInfo().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
