package lab6.common.exceptions;

import lab6.gui.main.MainFrame;

public class NoSuchIdException extends  InvalidDataException{
    public NoSuchIdException() {
        super("no such id");
        MainFrame.errors.add("noSuchId");
    }
}
