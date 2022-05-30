package lab6.server.commands;


import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.exceptions.*;
import lab6.server.ClientCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.LinkedHashSet;

public abstract class BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(BaseCommand.class);

    private static final String SUFFIX = "Command";
    Transformer transformer = new Transformer();
    ClientCaller clientCaller = new ClientCaller();
    private final String name;

    public BaseCommand() {
        String simpleName = this.getClass().getSimpleName();

        assert simpleName.endsWith(SUFFIX);

        this.name = simpleName.substring(0, simpleName.length() - SUFFIX.length());
    }

    /**
     * Определить количество параметрво, необходимых для данной команды.
     * Переопределяется в командах-наследниках, у которых количество параметров отличается от дефолтного
     * @return по умолчанию возвращает 0 (команда без параметров)
     */
    protected int getCommandParamsCount() {
        return 0;
    }

    protected abstract void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) throws IOException, InvalidSalaryException, InvalidDateFormatException, ParseException, InvalidEndDateException;

    public String getName() {
        return name;
    }

    public void ExecuteCommand(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set) {
        try {
            Execute(params, set, transformer, clientCaller);
        } catch (InvalidEndDateException | FileNotFoundException | MissedCommandArgumentException | EmptyCollectionException | InvalidSalaryException | InvalidDateFormatException | RecursiveScriptExecuteException e){
            logger.info(e.getMessage() + " server exception ignored later");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            logger.error("ошибка парсера");
        }
    }
}