package command;

import controller.Context;
import model.Consumer;
import model.ConsumerPreferences;
import model.User;

import java.util.Map;
import java.util.Objects;

public class UpdateConsumerProfileCommand extends UpdateProfileCommand {

    private String oldPassword;
    private String newName;
    private String newEmail;
    private String newPhoneNumber;
    private String newPassword;
    private String newPaymentAccountEmail;
    ConsumerPreferences newPreferences;
    private Boolean successResult;


    public UpdateConsumerProfileCommand(String oldPassword,
                                        String newName,
                                        String newEmail,
                                        String newPhoneNumber,
                                        String newPassword,
                                        String newPaymentAccountEmail,
                                        ConsumerPreferences newPreferences) {
        super();
        this.oldPassword = oldPassword;
        this.newName = newName;
        this.newEmail = newEmail;
        this.newPhoneNumber = newPhoneNumber;
        this.newPassword = newPassword;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newPreferences = newPreferences;
    }

    @Override
    public void execute(Context context) {
        successResult = false;

        // assert user is consumer
        assert context.getUserState().getCurrentUser() instanceof Consumer : "User must be a consumer.";

        Consumer currentUser = (Consumer) context.getUserState().getCurrentUser();

        // assert parameters are not null
        assert this.oldPassword != null : "Password must not be null.";
        assert this.newName != null : "New name must not be null.";
        assert this.newEmail != null : "New email must not be null.";
        assert this.newPhoneNumber != null : "New phone number must not be null.";
        assert this.newPassword != null : "New password must not be null.";
        assert this.newPaymentAccountEmail != null : "New payment account must not be null.";

        //assert user is logged in
        assert currentUser != null : "Current user must be logged in.";

        // assert password correct
        assert !this.isProfileUpdateInvalid(context, oldPassword, newEmail) : "Password must match old password.";

        // assert email is not taken by other user
        boolean emailVacant = true;
        for (Map.Entry<String, User> user : context.getUserState().getAllUsers().entrySet()) {
            if (user.getValue().getEmail().equals(newEmail)) {
                emailVacant = false;
                break;
            }
        }
        assert emailVacant : "Email is already taken by other user.";

        currentUser.setName(newName);
        currentUser.setEmail(newEmail);
        currentUser.setPhoneNumber(newPhoneNumber);
        currentUser.updatePassword(newPassword);
        currentUser.setPaymentAccountEmail(newPaymentAccountEmail);
        currentUser.setPreferences(newPreferences);

        successResult = true;
    }

}
