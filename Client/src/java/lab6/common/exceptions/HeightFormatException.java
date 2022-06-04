package lab6.common.exceptions;

import lab6.gui.AuthFrame;
import lab6.gui.RegisterFrame;
import lab6.gui.main.MainFrame;

public class HeightFormatException extends InvalidDataException{
    public HeightFormatException() {
        super("wrong height format");
        AuthFrame.responses.add("invalidHeightFormat");
        RegisterFrame.responses.add("invalidHeightFormat");
        MainFrame.errors.add("invalidHeightFormat");
    }
}
