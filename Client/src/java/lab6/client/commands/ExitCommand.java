package lab6.client.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExitCommand extends BaseCommand{
    private static final Logger logger
            = LoggerFactory.getLogger(ExitCommand.class);


        /**
         * exit command
         * command for exit
         */

    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(0, params);
        System.out.println("bye");
        System.exit(0);
    }
}
