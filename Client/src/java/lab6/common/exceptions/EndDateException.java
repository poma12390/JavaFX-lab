package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.CheckOutFrame;
import lab6.gui.main.MainFrame;

public class EndDateException extends InvalidDataException {
    public EndDateException() {
        super("start date should be < end date");
        MainFrame.errors.add("invalidEndDate");
        CheckOutFrame.errors.add("invalidEndDate");
    }
}
