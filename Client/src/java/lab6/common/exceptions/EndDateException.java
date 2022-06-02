package lab6.common.exceptions;

public class EndDateException extends InvalidDataException {
    public EndDateException() {
        super("start date should be < end date");
    }
}
