package logging;

import java.util.Map;

public class LogEntry {
    private String callerName;
    private Object result;
    private Map<String, Object> additionalInfo;

    public LogEntry (String callerName, Object result, Map<String, Object> additionalInfo){
        this.callerName = callerName;
        this.result = result;
        this.additionalInfo = additionalInfo;
    }

    public String getResult(){
        /*
            stringify objects:
            additionalInfo
             .entrySet()
             .stream()
             .collect(Collectors.toMap(
                 Map.Entry::getKey,
                 entry -> String.valueOf(entry.getValue()))
                );
         */
        return String.valueOf(result);
    }
}
