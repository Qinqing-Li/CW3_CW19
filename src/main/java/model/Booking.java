package model;

import java.time.LocalDateTime;

public class Booking {
    private long bookingNumber;
    private Consumer booker;
    private EventPerformance performance;
    private int numTickets;
    private double amountPaid;
    private LocalDateTime bookingDateTime;
    private BookingStatus status;

    public Booking(long bookingNumber, Consumer booker, EventPerformance performance,
                   int numTickets, double amountPaid, LocalDateTime bookingDateTime){
        this.bookingNumber = bookingNumber;
        this.booker = booker;
        this.performance = performance;
        this.numTickets = numTickets;
        this.amountPaid = amountPaid;
        this.bookingDateTime = bookingDateTime;
        this.status = BookingStatus.Active;
    }

    public void cancelByConsumer(){
        this.status = BookingStatus.CancelledByConsumer;
    }

    public void cancelByProvider(){
        this.status = BookingStatus.CancelledByProvider;
    }

    public void cancelPaymentFailed(){
        this.status = BookingStatus.PaymentFailed;
    }

    public double getAmountPaid(){
        return this.amountPaid;
    }

    public Consumer getBooker(){
        return this.booker;
    }

    public long getBookingNumber(){
        return this.bookingNumber;
    }

    public EventPerformance getEventPerformance(){
        return this.performance;
    }

    public BookingStatus getStatus(){
        return this.status;
    }

    @Override
    public String toString(){
        return "Booking number is " + bookingNumber +
                "\nnum of tickets is " + numTickets +
                "\namount paid is " + amountPaid +
                "\nbooking date and time is " + bookingDateTime +
                "\nbooking status is " + this.status;
    }
}