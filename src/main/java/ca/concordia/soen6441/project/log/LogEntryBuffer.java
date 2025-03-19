package ca.concordia.soen6441.project.log;

public class LogEntryBuffer extends Observable {
    private StringBuilder d_logInfo;
    private static LogEntryBuffer INSTANCE;

    private LogEntryBuffer() {
        d_logInfo = new StringBuilder();
    }

    public static LogEntryBuffer getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new LogEntryBuffer();
        }

        return INSTANCE;
    }

    public void notifyObservers(String p_appendedLogMessage) {
        d_logInfo.append(p_appendedLogMessage).append(System.lineSeparator());
        notifyObservers(this);
    }

    public StringBuilder getLogInfo() {
        return d_logInfo;
    }

}
