package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class InvalidEndDateException extends InvalidDataException{
    public InvalidEndDateException() {
        super("End Date should be > start date");
        AuthFrame.responses.add("invalidEndDate");
        RegisterFrame.responses.add("invalidEndDate");
        MainFrame.errors.add("invalidEndDate");

    }
}
