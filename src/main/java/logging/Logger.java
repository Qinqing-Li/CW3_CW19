package logging;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Logger {
    private static Logger logger_instance;
    private List<LogEntry> logs;

    private Logger(){
        logger_instance = new Logger();
    }

    public void	clearLog(){
        logger_instance = null;
    }

    public static Logger getInstance(){
        if (logger_instance == null){
            logger_instance = new Logger();
        }
        return logger_instance;
    }

    public List<LogEntry> getLog(){
        return logs;
    }

    public void	logAction (String callerName, Object result){
        LogEntry newLog = new LogEntry(callerName, result, Collections.<String, Object>emptyMap());
        logs.add(newLog);
    }

    public void	logAction (String callerName, Object result, Map<String, Object> additionalInfo){
        LogEntry newLog = new LogEntry(callerName, result, additionalInfo);
        logs.add(newLog);
    }
}
