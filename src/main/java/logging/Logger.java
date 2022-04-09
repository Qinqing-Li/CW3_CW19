package logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Logger {
    private static Logger logger_instance;
    private static List<LogEntry> logs;

    private Logger(){
    }

    public void	clearLog(){
        logs.clear();
    }

    public static Logger getInstance(){
        if (logger_instance == null){
            logger_instance = new Logger();
            logs = new ArrayList<>();
        }
        return logger_instance;
    }

    public List<LogEntry> getLog(){
        return logs;
    }

    public void	logAction (String callerName, Object result){
        LogEntry newLog = new LogEntry(callerName, result, Collections.emptyMap());
        logs.add(newLog);
    }

    public void	logAction (String callerName, Object result, Map<String, Object> additionalInfo){
        LogEntry newLog = new LogEntry(callerName, result, additionalInfo);
        logs.add(newLog);
    }
}
