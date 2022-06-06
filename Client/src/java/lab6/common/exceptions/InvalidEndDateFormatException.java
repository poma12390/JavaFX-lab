package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.CheckOutFrame;
import lab6.gui.main.MainFrame;

public class InvalidEndDateFormatException extends InvalidDataException{
    public InvalidEndDateFormatException() {
        super("End date format must be dd.MM.YYYY");
        MainFrame.errors.add("endDateWrongFormat");
        CheckOutFrame.errors.add("endDateWrongFormat");
    }
}
