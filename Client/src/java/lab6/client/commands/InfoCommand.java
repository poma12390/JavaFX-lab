package lab6.client.commands;

import lab6.client.ServerReceiver;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.InfoCommandDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InfoCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(InfoCommand.class);
    /**
     * info command
     * command to show info aд
     */

    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(0, params);
        InfoCommandDto dto = new InfoCommandDto();
        CommandRequestDto <InfoCommandDto> crd = new CommandRequestDto<>("info", dto);

        byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));

        CommandResponseDto response = (CommandResponseDto) transformer.DeSerialize(buf);
        logger.info(response.getResponse());
    }
}
