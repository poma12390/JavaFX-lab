package lab6.client.commands;

import lab6.client.memory.HistoryWork;
import lab6.common.exceptions.EmptyHistoryException;
import lab6.gui.main.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HistoryCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(HistoryCommand.class);
    /**
     * history command
     * show last 5 commands without params
     */


    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(0, params);
        ArrayList<String> history = HistoryWork.getHistory();
        if (history.size() == 0) {
            try {
                throw  new EmptyHistoryException();
            } catch (EmptyHistoryException e) {
                MainFrame.errors.add("emptyHistory");
            }
        } else {
            for (String s : history) {
                logger.info(s + " ");
                MainFrame.responses.add(s);
                MainFrame.responses.add("\r\n");
            }

        }
        logger.info("");
    }
}
