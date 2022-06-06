package lab6.client.setterrs;

import lab6.common.Position;
import lab6.common.Worker;
import lab6.common.exceptions.EmptyPositionException;

import java.util.Locale;

public class SetPosition {


    public static void setPosition(String pos, Worker bum) throws EmptyPositionException {

        if (pos == null || pos.isEmpty() || pos.equals("null")){
            throw new EmptyPositionException();
        }
        else {
            pos = pos.toUpperCase(Locale.ENGLISH);
            bum.setPosition(Position.valueOf(pos));
        }
    }
}
