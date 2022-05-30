package lab6.client.commands;

import lab6.client.ServerReceiver;
import lab6.common.dto.ClearCommandDto;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClearCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(ClearCommand.class);
    /**
     * clear command
     * clear collection
     */

    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(0, params);
        ClearCommandDto dto = new ClearCommandDto();
        CommandRequestDto<ClearCommandDto> crd = new CommandRequestDto<>("clear", dto);

        byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));

        CommandResponseDto response = (CommandResponseDto) transformer.DeSerialize(buf);
        logger.info(response.getResponse());

    }
}
