package command;

import controller.Context;
import model.Event;
import model.EventPerformance;
import model.TicketedEvent;

public class GetAvailablePerformanceTicketsCommand implements ICommand {

    private Integer result;
    private long eventNumber;
    private long performanceNumber;

    public GetAvailablePerformanceTicketsCommand(long eventNumber,
                                                 long performanceNumber) {
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
    }

    public void execute(Context context) {
        for (Event event : context.getEventState().getAllEvents()) {
            if (event.getEventNumber() == eventNumber && event instanceof TicketedEvent) {
                for (EventPerformance performance : event.getPerformances()) {
                    if (performance.getPerformanceNumber() == performanceNumber) {
                        result = ((TicketedEvent) event).getNumTickets();
                        return;
                    }
                }
            }
        }
    }

    public Integer getResult() {
        return result;
    }

}
