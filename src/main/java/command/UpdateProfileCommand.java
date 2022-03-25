package command;

import controller.Context;
import model.User;

public abstract class UpdateProfileCommand implements ICommand {

    protected Boolean successResult;

    public UpdateProfileCommand() {
        successResult = false;
    }

    // TODO check why is newEmail provided? Do we need to change email here?
    protected boolean isProfileUpdateInvalid(Context context,
                                             String oldPassword,
                                             String newEmail) {

        User currentUser = context.getUserState().getCurrentUser();
        return !currentUser.checkPasswordMatch(oldPassword);

    }

    public Boolean getResult() {
        return this.successResult;
    }

}
