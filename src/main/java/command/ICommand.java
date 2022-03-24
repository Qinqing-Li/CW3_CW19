package command;

import controller.Context;

public interface ICommand {

    default void execute(Context context) {

    }

    default Object getResult() {

        return null;

    }

}
