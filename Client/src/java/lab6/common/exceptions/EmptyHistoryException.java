package lab6.common.exceptions;

public class EmptyHistoryException extends InvalidDataException{
    public EmptyHistoryException() {
        super("History is empty");
    }
}
