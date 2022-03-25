package controller;

import external.MockPaymentSystem;
import external.PaymentSystem;
import state.*;

public class Context {

    private PaymentSystem paymentSystem;
    private UserState userState;
    private EventState eventState;
    private BookingState bookingState;
    private SponsorshipState sponsorshipState;

    public Context() {
        this.paymentSystem = new MockPaymentSystem();
        this.userState = new UserState();
        this.eventState = new EventState();
        this.bookingState = new BookingState();
        this.sponsorshipState = new SponsorshipState();
    }

    // needs to be implemented
    // public Context(Context other) {}

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public IUserState getUserState() {
        return userState;
    }

    public IBookingState getBookingState() {
        return bookingState;
    }

    public IEventState getEventState() {
        return eventState;
    }

    public ISponsorshipState getSponsorshipState() {
        return sponsorshipState;
    }

}
