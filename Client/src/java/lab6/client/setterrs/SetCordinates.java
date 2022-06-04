package lab6.client.setterrs;


import lab6.common.Coordinates;
import lab6.common.Worker;
import lab6.common.exceptions.CoordinatesFormatException;
import lab6.common.exceptions.EmptyCoordinatesException;
import lab6.common.exceptions.InvalidCoordinatesException;

public class SetCordinates {
    public static void setcordinates(String x, String y, Worker bum) throws InvalidCoordinatesException, EmptyCoordinatesException, CoordinatesFormatException {
        if (x.isEmpty() | y.isEmpty()) {
            throw new EmptyCoordinatesException();
        } else {
            long x1;
            int y1;
            try {
                x1 = Long.parseLong(x);
                y1 = Integer.parseInt(y);
            }catch (NumberFormatException e){
                throw new CoordinatesFormatException();
            }
            if (x1 == 0 | y1 == 0 | y1 < 0) {
                throw new InvalidCoordinatesException();
            } else {
                Coordinates pCord = new Coordinates();
                pCord.setXY(x1, y1);
                bum.setCoordinates(pCord);
            }
        }
    }


}
