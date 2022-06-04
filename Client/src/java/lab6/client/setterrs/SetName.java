package lab6.client.setterrs;

import lab6.common.Worker;
import lab6.common.exceptions.EmptyNameException;


public class SetName {
    public static void setname(String name, Worker bum) throws EmptyNameException {
        if (name.isEmpty()) {
            throw new EmptyNameException();
        } else {
            bum.setName(name);
        }
    }
}
