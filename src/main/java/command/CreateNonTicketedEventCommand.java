package command;

import controller.Context;
import model.EntertainmentProvider;
import model.EventType;
import model.NonTicketedEvent;

public class CreateNonTicketedEventCommand extends CreateEventCommand {

    public CreateNonTicketedEventCommand(String title, EventType type) {

        super(title, type);

    }

    @Override
    public void execute(Context context) {
        if (this.isUserAllowedToCreateEvent(context)) {
            EntertainmentProvider thisProvider = (EntertainmentProvider) context.getUserState().getCurrentUser();
            NonTicketedEvent newEvent = context.getEventState().createNonTicketedEvent(thisProvider, title, type);
            this.eventNumberResult = newEvent.getEventNumber();
        }
        else {
            this.eventNumberResult = null;
        }
    }

}
