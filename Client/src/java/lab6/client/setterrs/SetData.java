package lab6.client.setterrs;

import lab6.common.Worker;
import lab6.common.exceptions.EndDateException;
import lab6.common.exceptions.InvalidDateFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetData {
    private static Date endDateMoreStartDate;
    public static void  setStartData(String startDate, Worker bum) throws InvalidDateFormatException, ParseException {
        String regex = "\\d{2}\\.\\d{2}.\\d{4}";
        if (startDate.matches(regex)) {
            String[] items = startDate.split("\\.");
            int y = Integer.parseInt(items[0]);
            int y1 = Integer.parseInt(items[1]);
            if (y>31 | y1 > 12){
                throw new InvalidDateFormatException();
            }
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                Date date = formatter.parse(startDate);
                endDateMoreStartDate = date;
                bum.setStartDate(date);
            }
        } else {
            throw new InvalidDateFormatException();
        }
    }
    public static void setEndData(String endDate, Worker bum) throws ParseException, InvalidDateFormatException, EndDateException {
        String regex = "\\d{2}\\.\\d{2}.\\d{4}";
        if (endDate.matches(regex)) {
            String[] items = endDate.split("\\.");
            int y = Integer.parseInt(items[0]);
            int y1 = Integer.parseInt(items[1]);
            if (y>31 | y1 > 12 ){
                throw new InvalidDateFormatException();
            }
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                Date date = formatter.parse(endDate);
                if (date.compareTo(endDateMoreStartDate)<=0){
                    System.out.println("start date should be < enddate");
                    throw new EndDateException();
                }
                bum.setEndDate(date);
            }
        } else {
            throw new InvalidDateFormatException();
        }
    }

}
