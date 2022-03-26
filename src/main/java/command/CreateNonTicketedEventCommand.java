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
            NonTicketedEvent newEvent = context.getEventState().createNonTicketedEvent(
                    (EntertainmentProvider) context.getUserState().getCurrentUser(),
                    this.title,
                    this.type);
            this.eventNumberResult = newEvent.getEventNumber();
        }
        else {
            this.eventNumberResult = null;
        }
    }

}
