package command;

import controller.Context;
import model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public class GovernmentReport1Command implements ICommand {

    public GovernmentReport1Command(LocalDateTime intervalStartInclusive, LocalDateTime intervalEndInclusive) {

    }

    public void execute(Context context) {

    }

    public List<Booking> getResult() {
        return null;
    }

}
