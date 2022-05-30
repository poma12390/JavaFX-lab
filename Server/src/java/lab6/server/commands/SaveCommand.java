package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.server.ClientCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashSet;

public class SaveCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(SaveCommand.class);

    /**
     * save command
     * save collection in csv file
     */

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) throws IOException {
        Commands.dataBaseToCollection();
    }
}
