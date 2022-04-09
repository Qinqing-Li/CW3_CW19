package command;

import controller.Context;
import model.Consumer;
import model.User;

public class RegisterConsumerCommand implements ICommand {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String paymentAccountEmail;
    private Consumer result;

    public enum LogStatus{
        REGISTER_CONSUMER_SUCCESS,
        USER_REGISTER_FIELDS_CANNOT_BE_NULL,
        USER_REGISTER_EMAIL_ALREADY_REGISTERED,
        USER_LOGIN_SUCCESS
    }

    public RegisterConsumerCommand(String name,
                                    String email,
                                    String phoneNumber,
                                    String password,
                                    String paymentAccountEmail) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.paymentAccountEmail = paymentAccountEmail;
    }

    @Override
    public void execute(Context context) {
        assert name != null : "Name cannot be null.";
        assert email != null : "Email cannot be null.";
        assert phoneNumber != null : "Phone number cannot be null.";
        assert password != null : "Password cannot be null.";
        assert paymentAccountEmail != null : "Payment account email cannot be null.";

        assert !context.getUserState().getAllUsers().containsKey(email) :
                "Email already registered with another user.";

        Consumer newUser = new Consumer(name, email, phoneNumber, password, paymentAccountEmail);
        context.getUserState().addUser(newUser);
        result = newUser;

        context.getUserState().setCurrentUser(newUser);
    }

    @Override
    public Object getResult() {
        return result;
    }
}
