package lab6.client.setterrs;


import lab6.common.Person;
import lab6.common.Worker;
import lab6.common.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class SetPersParams {
    public static void setHeight(String height, Person pers) throws EmptyHeightException, HeightFormatException {
        if (height.isEmpty()){
            throw new EmptyHeightException();
        }
        else {
            float f;
            try {
                f = Float.parseFloat(height);
            }catch (NumberFormatException e){
                throw new HeightFormatException();
            }

            if (f == 0 | f < 0) {
                throw new EmptyHeightException();
            } else {
                pers.setHeight(f);
            }
        }
    }

    public static void setWeight(String weight, Person pers) throws EmptyWeightException, WeightFormatException {
        if (weight.isEmpty()){
            throw new EmptyWeightException();
        }
        else {
            float f;
            try {
                f = Float.parseFloat(weight);
            }catch (NumberFormatException e){
                throw new WeightFormatException();
            }
            if (f == 0 | f < 0) {
                throw new EmptyWeightException();
            } else {
                pers.setWeight(f);
            }
        }
    }
    public static void setBirthday(String birthday, Person pers) throws ParseException, InvalidBirthdayFormatException {
        String regex = "\\d{2}\\.\\d{2}.\\d{4}";
        if (birthday.matches(regex)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse(birthday);
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Instant instant = date.toInstant();
            ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
            pers.setBirthday(zonedDateTime);
        } else {
            throw new InvalidBirthdayFormatException();
        }
    }

    public static void setPersToWork(Person pers, Worker bum){
        bum.setPerson(pers);
    }
}
