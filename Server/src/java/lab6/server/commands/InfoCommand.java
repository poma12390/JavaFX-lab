package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.InfoCommandDto;
import lab6.common.dto.PackageDto;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class InfoCommand extends BaseCommand {
    /**
     * info command
     * command to show info aд
     */

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) {
        String response = "";
        CommandResponseDto<InfoCommandDto> dto = new CommandResponseDto<>(new InfoCommandDto());
        boolean auth = Commands.checkAuth(params);
        if (!auth) {
            dto.setResponse("you should be authorized");
        } else {
            int size = set.size(); //help me
            if (size == 0) {
                response = response + "empty collection";
            } else {
                long count = set.stream().filter((p) -> p.getUser().equals(params.getLogin())).count();
                Iterator<Worker> it = set.iterator();
                Worker p1 = it.next();
                response = response + "Collection size " + size + "\r\n"
                        + "Type - Worker \r\n"
                        + "Created date - " + p1.getCreationDate()  + "\r\n"
                        + "your elements - " + count;

            }

            dto.setResponse(response);
        }
        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

       // clientCaller.sendToClient(Transformer.serialize(dto));

    }
}
