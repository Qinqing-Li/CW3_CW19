package command;

import controller.Context;
import external.EntertainmentProviderSystem;
import external.MockEntertainmentProviderSystem;
import model.Booking;
import model.Consumer;
import model.EntertainmentProvider;
import model.User;

import java.time.LocalDateTime;
import java.util.Map;

import static model.BookingStatus.Active;

public class CancelBookingCommand implements ICommand {

    private Boolean result;
    private long bookingNumber;

    public CancelBookingCommand(long bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public void execute(Context context) {
        result = false;

        assert context.getUserState().getCurrentUser() instanceof Consumer : "User must be consumer.";
        Consumer currentConsumer = (Consumer) context.getUserState().getCurrentUser();
        Booking currentBooking = context.getBookingState().findBookingByNumber(bookingNumber);

        // if booking doesn't belong to consumer it will remain null
        assert currentBooking != null : "Booking number must belong to logged-in consumer.";
        assert currentBooking.getStatus() == Active : "Booking must be active.";
        assert currentBooking.getEventPerformance().getStartDateTime().minusDays(1).isAfter(LocalDateTime.now()):
                "The booked performance start must be at least 24hrs away from now.";

        String buyerAccountEmail = currentConsumer.getPaymentAccountEmail();
        EntertainmentProvider seller = currentBooking.getEventPerformance().getEvent().getOrganiser();

        String sellerAccountEmail;
        assert context.getPaymentSystem().processRefund(
                buyerAccountEmail,
                seller.getPaymentAccountEmail(),
                currentBooking.getAmountPaid()) :
                "Refund unsuccessful.";

        currentBooking.cancelByConsumer();

        EntertainmentProviderSystem providerSystem = seller.getProviderSystem();
        providerSystem.cancelBooking(currentBooking.getBookingNumber());

        result = true;
    }

    public Boolean getResult() {
        return result;
    }

}
