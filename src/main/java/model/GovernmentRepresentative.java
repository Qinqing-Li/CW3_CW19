package model;

public class GovernmentRepresentative extends User {
    public GovernmentRepresentative (String email, String password, String paymentAccountEmail){
        super(email,password,paymentAccountEmail);
    }

    @Override
    public String toString(){
        return "Government representative email: " + getEmail() +
                "\nPayment account email:" + getPaymentAccountEmail();
    }
}
