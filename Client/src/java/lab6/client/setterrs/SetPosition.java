package lab6.client.setterrs;

import lab6.common.Position;
import lab6.common.Worker;
import lab6.common.exceptions.EmptyPositionException;
import lab6.common.exceptions.EmptyStringException;

import java.util.Locale;

public class SetPosition {



    public static void setPosition(String pos, Worker bum) throws EmptyPositionException {
        System.out.println(pos);
        if (pos.isEmpty()){
            throw new EmptyPositionException();
        }
        else {
            pos = pos.toUpperCase(Locale.ENGLISH);
            //System.out.println(pos);
            bum.setPosition(Position.valueOf(pos));
        }
    }
}
