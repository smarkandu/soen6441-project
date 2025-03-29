package ca.concordia.soen6441.project.log;

/**
 * The class will be used as an observable. It contains a StringBuilder
 * that will be appended and write to a log file by the observer.
 */
public class LogEntryBuffer extends Observable {
    private StringBuilder d_logInfo;
    private static LogEntryBuffer Instance;

    /**
     * Constructor of LogEntryBuffer
     */
    private LogEntryBuffer() {
        d_logInfo = new StringBuilder();
    }

    /**
     * Singleton of the object LogEntryBuffer
     * @return the same instance of a LogEntryBuffer
     */
    public static LogEntryBuffer getInstance() {
        if(Instance == null) {
            Instance = new LogEntryBuffer();
        }

        return Instance;
    }

    /**
     * This method is used to append text to a StringBuilder object. After that the observer
     * gets notify
     * @param p_appendedLogMessage the message to append to the StringBuilder object
     * @param p_writeToScreen if it is true write to console
     */
    public void appendToBuffer(String p_appendedLogMessage, boolean p_writeToScreen) {
        d_logInfo.append(p_appendedLogMessage).append(System.lineSeparator());

        if (p_writeToScreen)
        {
            System.out.println(p_appendedLogMessage);
        }

        notifyObservers(this);
    }

    /**
     * This a string representation of the StringBuilder object
     * @return StringBuilder
     */
    public StringBuilder getLogInfo() {
        return d_logInfo;
    }

}
