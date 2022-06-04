package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class InvalidBirthdayFormatException extends InvalidDataException{
    public InvalidBirthdayFormatException() {
        super("Birthday format must be dd.MM.YYYY");
        AuthFrame.responses.add("birthdayWrongFormat");
        RegisterFrame.responses.add("birthdayWrongFormat");
        MainFrame.errors.add("birthdayWrongFormat");
    }
}
