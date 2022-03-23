package model;

// import bcrypt here
import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class User {
    private String email;
    private String password;
    private String paymentAccountEmail;

    protected User (String email, String password, String paymentAccountEmail){
        this.email = email;
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());;
        this.paymentAccountEmail = paymentAccountEmail;
    }

    public boolean checkPasswordMatch(String password){
        return BCrypt.verifyer().verify(password.toCharArray(), this.password).verified;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPaymentAccountEmail(){
        return this.paymentAccountEmail;
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public void setPaymentAccountEmail(String newPaymentAccountEmail){
        this.paymentAccountEmail = newPaymentAccountEmail;
    }

    @Override
    public String toString(){
        return "User email address " + email + "\npayment account email "
                + paymentAccountEmail;
    }

    public void updatePassword(String newPassword){
        this.password = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
    }
}
