package command;

import controller.Context;
import model.EventPerformance;

import java.time.LocalDateTime;
import java.util.List;

public class AddEventPerformanceCommand implements ICommand {

    public AddEventPerformanceCommand(long eventNumber,
                                      String venueAddress,
                                      LocalDateTime startDateTime,
                                      LocalDateTime endDateTime,
                                      List<String> performerNames,
                                      boolean hasSocialDistancing,
                                      boolean hasAirFiltration,
                                      boolean isOutdoors,
                                      int capacityLimit,
                                      int venueSize){



    }

    public void execute(Context context) {



    }

    public EventPerformance getResult() {
        return null;
    }

}
