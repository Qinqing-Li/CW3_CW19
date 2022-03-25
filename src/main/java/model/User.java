package model;

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
        return email;
    }

    public String getPaymentAccountEmail(){
        return paymentAccountEmail;
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public void setPaymentAccountEmail(String newPaymentAccountEmail){
        this.paymentAccountEmail = newPaymentAccountEmail;
    }

    public void updatePassword(String newPassword){
        this.password = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
    }
}
