package lab6.client.commands;

import lab6.client.ServerReceiver;
import lab6.client.memory.LoginPassword;
import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.UpdateIdCommandDto;
import lab6.common.exceptions.IdFormatException;
import lab6.gui.main.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class UpdateIdCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(UpdateIdCommand.class);

    @Override
    protected int getCommandParamsCount() {
        return 1;
    }

    /**
     * update command
     *
     * @param params id Worker to update
     *               update all stats
     */
    @Override
    protected void Execute(List<String> params) throws IOException, IdFormatException {
        Worker bum = new Worker(LoginPassword.getLogin());
        UpdateIdCommandDto dto = new UpdateIdCommandDto();
        if (params.size() == 1) {
            Utils.updateAll(bum);
        } else {
            String[] strings = params.toArray(new String[0]);
            Utils.upload(strings, bum);
        }


        if (bum.getId() == null) {
            try {
                int id = Integer.parseInt(params.get(params.size() - 1));
                dto.setWorkerId(id);
            } catch (Exception e) {
                throw new IdFormatException();
            }
            CommandRequestDto<UpdateIdCommandDto> crd = new CommandRequestDto<>("updateid", dto);
            byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));
            CommandResponseDto responseObj = (CommandResponseDto) transformer.DeSerialize(buf);
            String response = responseObj.getResponse();
            logger.info(response);
            if (response.equals("Correct id")) {
                dto.setWorkerDto(Transformer.WorkerToWorkerDto(bum));

                buf = serverCaller.sendToServer(transformer.Serialize(crd));

                responseObj = (CommandResponseDto) transformer.DeSerialize(buf);
                response = responseObj.getResponse();
                logger.info(response);
                if (response.toLowerCase().equals("success")){

                    MainFrame.responses.add("successfullyUpd");
                    MainFrame.responses.add("\r\n");
                }
                else if(response.equals("Not your element")){
                    MainFrame.errors.add("notYourElement");
                }
                else {
                    MainFrame.responses.add(response);
                    MainFrame.responses.add("\r\n");
                }
            }else {
                MainFrame.errors.add("noSuchId");
            }
        }



    }
}
