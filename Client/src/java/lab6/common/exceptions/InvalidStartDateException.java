package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class InvalidStartDateException extends InvalidDataException{
    public InvalidStartDateException() {
        super("Start date format must be dd.MM.YYYY");
        AuthFrame.responses.add("startDateWrongFormat");
        RegisterFrame.responses.add("startDateWrongFormat");
        MainFrame.errors.add("startDateWrongFormat");

    }
}
