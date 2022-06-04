package lab6.client.setterrs;

import lab6.common.Worker;
import lab6.common.exceptions.InvalidSalaryException;

public class SetSalary {
    public static void setSalary(String salary, Worker bum) throws InvalidSalaryException{
        if (salary.isEmpty()) {
            throw new InvalidSalaryException();
        } else {
            float f;
            try {
                f = Float.parseFloat(salary);
            } catch (NumberFormatException e){
                throw new InvalidSalaryException();
            }

            if (f == 0 | f < 0) {
                throw new InvalidSalaryException();
            } else {
                bum.setSalary(f);
            }
        }
    }
}
