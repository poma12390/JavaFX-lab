package lab6.client.memory;

import lab6.common.exceptions.RecursiveScriptExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class OverflowChecker {
    private static final Logger logger
            = LoggerFactory.getLogger(OverflowChecker.class);
    private static ArrayList<String> files = new ArrayList<String>();

    public static ArrayList<String> getFiles() {
        return files;
    }
    public static void checkRec(String path){
        if (files.contains(path)){
            throw new RecursiveScriptExecuteException();
        }
        files.add(path);
        logger.info(String.valueOf(files));


    }
}
