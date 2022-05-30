package lab6.client.commands;

import lab6.common.exceptions.EndStreamException;
import lab6.client.memory.OverflowChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

import static lab6.client.commands.Utils.*;
import static lab6.client.inputters.InputUtils.inputString;

public class ExecuteScriptCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(ExecuteScriptCommand.class);
    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    protected int getCommandParamsCount() {
        return 1;
    }
    /**
     * executeScript command
     * @param params filename to complete script
     * run all commands from file
     */
    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(1, params);

        String fileName = params.get(0);
        File file = new File(fileName);
        if (!isFileExecuted)
            OverflowChecker.getFiles().clear();

        OverflowChecker.checkRec(file.getAbsolutePath());

        BufferedReader oldReader = currentBufferedReader;
        blockPrompts = true;
        boolean oldIsFileExecuted = isFileExecuted;
        isFileExecuted = true;
        try {

            try {
                try(InputStream fileInputStream = new FileInputStream(fileName)) {
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    Utils.setCurrentBufferedReader(bufferedReader);
                    do {
                        try {
                            String commandWithParameters = inputString("");
                            logger.info("doing " + commandWithParameters);
                            runCommandFromString(commandWithParameters);
                        } catch (EndStreamException e) {
                            break;
                        }
                    } while (true);

                }
            } catch (FileNotFoundException e) {
                logger.error("File not found: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            Utils.setIsFileExecuted(oldIsFileExecuted);
            Utils.setBlockPrompts(false);
            Utils.setCurrentBufferedReader(oldReader);
        }

    }
}
