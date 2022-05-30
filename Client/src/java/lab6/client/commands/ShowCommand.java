package lab6.client.commands;

import lab6.client.ServerReceiver;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.ShowCommandDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShowCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(ShowCommand.class);

    /**
     * show command
     * show all obj from in Collection
     */

    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(0, params);

        ShowCommandDto dto = new ShowCommandDto();
        CommandRequestDto<ShowCommandDto> crd = new CommandRequestDto<>("show", dto);
        byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));

        //byte[] buf = ServerReceiver.receiveFromServer();
        CommandResponseDto response = (CommandResponseDto) transformer.DeSerialize(buf);

        dto = (ShowCommandDto) response.getCommandArgs();
        List<Worker> workers = (List<Worker>) dto.getWorkers();
        if (workers.size() == 0) {
            logger.error("Collection is empty");
        } else {
            for (Worker i : workers) {
                logger.info(String.valueOf(i));
            }
        }

    }
}
