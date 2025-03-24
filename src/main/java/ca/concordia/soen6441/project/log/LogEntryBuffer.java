package ca.concordia.soen6441.project.log;

public class LogEntryBuffer extends Observable {
    private StringBuilder d_logInfo;
    private static LogEntryBuffer Instance;

    private LogEntryBuffer() {
        d_logInfo = new StringBuilder();
    }

    public static LogEntryBuffer getInstance() {
        if(Instance == null) {
            Instance = new LogEntryBuffer();
        }

        return Instance;
    }

    public void appendToBuffer(String p_appendedLogMessage, boolean p_writeToScreen) {
        d_logInfo.append(p_appendedLogMessage).append(System.lineSeparator());

        if (p_writeToScreen)
        {
            System.out.println(p_appendedLogMessage);
        }

        notifyObservers(this);
    }

    public StringBuilder getLogInfo() {
        return d_logInfo;
    }

}
