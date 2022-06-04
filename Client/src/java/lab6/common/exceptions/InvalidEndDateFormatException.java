package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class InvalidEndDateFormatException extends InvalidDataException{
    public InvalidEndDateFormatException() {
        super("End date format must be dd.MM.YYYY");
        AuthFrame.responses.add("endDateWrongFormat");
        RegisterFrame.responses.add("endDateWrongFormat");
        MainFrame.errors.add("endDateWrongFormat");
    }
}
