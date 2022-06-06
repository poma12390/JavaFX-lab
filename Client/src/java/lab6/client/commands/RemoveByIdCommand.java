package lab6.client.commands;

import lab6.client.memory.LoginPassword;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.RemoveByIdCommandDto;
import lab6.gui.main.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.MissingFormatArgumentException;

public class RemoveByIdCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(RemoveByIdCommand.class);
    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    protected int getCommandParamsCount() {
        return 1;
    }

    /**
     * removeById command
     * @param params id of worker to delete
     * delete worker from collections with id
     */

    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(1, params);
        RemoveByIdCommandDto dto = new RemoveByIdCommandDto();
        CommandRequestDto<RemoveByIdCommandDto> crd = new CommandRequestDto<>(getName(), dto);
        try {
            int id = Integer.parseInt(params.get(0));
            dto.setId(id);
        } catch (Exception e) {
            throw  new MissingFormatArgumentException("param should be int");
        }

        byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));

        CommandResponseDto response = (CommandResponseDto) transformer.DeSerialize(buf);

        dto = (RemoveByIdCommandDto) response.getCommandArgs();
        long count = dto.getCount();
        logger.info("Deleted " +count + " elements");
        if (count == 0){
            MainFrame.errors.add("noSuchId");
        }
        else {
            MainFrame.responses.add("deleted");
            MainFrame.responses.add(String.valueOf(count));
            MainFrame.responses.add("elements");
            MainFrame.responses.add("\r\n");
        }

    }
}
