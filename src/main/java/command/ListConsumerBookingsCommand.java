package command;

import controller.Context;
import model.Booking;
import model.Consumer;

import java.util.List;

public class ListConsumerBookingsCommand implements ICommand {

    private List<Booking> result;

    public ListConsumerBookingsCommand() {

    }

    public void execute(Context context) {

        assert context.getUserState().getCurrentUser() != null : "Current user must be logged in.";
        assert context.getUserState().getCurrentUser() instanceof Consumer : "Logged in user must be a consumer.";
        Consumer currentConsumer = (Consumer) context.getUserState().getCurrentUser();
        result = currentConsumer.getBookings();

    }

    public List<Booking> getResult() {
        return result;
    }

}
