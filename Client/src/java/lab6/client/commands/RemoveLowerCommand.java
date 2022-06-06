package lab6.client.commands;

import lab6.client.ServerReceiver;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.RemoveLowerCommandDto;
import lab6.gui.main.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RemoveLowerCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(RemoveLowerCommand.class);
    @Override
    public String getName() {
        return "remove_lower";
    }

    /**
     * removeLower command
     * remove lower element from collection
     */

    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(0, params);
        RemoveLowerCommandDto dto = new RemoveLowerCommandDto();
        CommandRequestDto<RemoveLowerCommandDto> crd = new CommandRequestDto<>(getName(), dto);
        byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));

        //byte[] buf = ServerReceiver.receiveFromServer();

        CommandResponseDto response = (CommandResponseDto) transformer.DeSerialize(buf);

        logger.info(response.getResponse());
        if(response.getResponse().equals("Collection is empty")){
            MainFrame.errors.add("emptyCollection");
        }else if (response.getResponse().toLowerCase().equals("success")){
            MainFrame.responses.add("\r\n");
            MainFrame.responses.add("successfullyRem");
        }else {
            MainFrame.responses.add("\r\n");
            MainFrame.responses.add(response.getResponse());
        }


    }
}
