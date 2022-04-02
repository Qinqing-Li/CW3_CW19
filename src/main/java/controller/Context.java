package controller;

import external.MockPaymentSystem;
import external.PaymentSystem;
import state.*;

public class Context {

    private PaymentSystem paymentSystem;
    private UserState userState;
    private EventState eventState;
    private BookingState bookingState;

    public Context() {
        this.paymentSystem = new MockPaymentSystem();
        this.userState = new UserState();
        this.eventState = new EventState();
        this.bookingState = new BookingState();
    }

    public Context (Context other){
        this.paymentSystem = other.paymentSystem;

        try{
            userState = (UserState) other.userState.clone();
            eventState = (EventState) other.eventState.clone();
            bookingState = (BookingState) other.bookingState.clone();
        }catch (CloneNotSupportedException e){
            // create default new instances when clone failed
            this.userState = new UserState();
            this.eventState = new EventState();
            this.bookingState = new BookingState();
        }
    }

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
}
