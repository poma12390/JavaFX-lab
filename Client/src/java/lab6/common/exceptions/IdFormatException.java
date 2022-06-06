package lab6.common.exceptions;

import lab6.gui.main.MainFrame;

public class IdFormatException extends InvalidDataException{
    public IdFormatException() {
        super("id should be int");
        MainFrame.errors.add("invalidIdFormat");
    }
}
