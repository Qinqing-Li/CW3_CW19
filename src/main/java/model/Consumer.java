package model;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends User {
    private String name;
    private String phoneNumber;
    private List<Booking> bookings;
    private ConsumerPreferences preferences = new ConsumerPreferences();

    public Consumer(String name, String email, String phoneNumber, String password,
                    String paymentAccountEmail){
        super(email,password,paymentAccountEmail);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bookings = new ArrayList<>();
    }

    public void	addBooking (Booking booking){
        bookings.add(booking);
    }

    public List<Booking> getBookings(){
        return bookings;
    }

    public String getName(){
        return this.name;
    }

    public ConsumerPreferences getPreferences(){
        return preferences;
    }

    public void	notify (String message){
        // Mock method: print out a message to STDOUT.
        System.out.println(message);
    }

    public void	setName (String newName){
        this.name = newName;
    }

    public void	setPhoneNumber(String newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
    }

    public void	setPreferences(ConsumerPreferences preferences){
        this.preferences = preferences;
    }
}
