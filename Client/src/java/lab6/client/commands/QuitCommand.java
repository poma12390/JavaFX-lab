package lab6.client.commands;

import lab6.client.memory.LoginPassword;
import lab6.common.exceptions.InvalidDateFormatException;
import lab6.common.exceptions.InvalidEndDateException;
import lab6.common.exceptions.InvalidSalaryException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class QuitCommand extends BaseCommand{
    @Override
    protected void Execute(List<String> params) throws IOException, InvalidSalaryException, InvalidDateFormatException, ParseException, InvalidEndDateException {
        ParamsChecker.checkParams(0,params);
        LoginPassword.setLogin(null);
        LoginPassword.setPassword(null);
        System.out.println("success");
    }
}
