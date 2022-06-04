package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class EndDateException extends InvalidDataException {
    public EndDateException() {
        super("start date should be < end date");
        AuthFrame.responses.add("invalidEndDate");
        RegisterFrame.responses.add("invalidEndDate");
        MainFrame.errors.add("invalidEndDate");
    }
}
