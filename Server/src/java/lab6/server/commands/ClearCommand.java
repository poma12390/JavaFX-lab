package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.ClearCommandDto;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashSet;

public class ClearCommand extends BaseCommand {
    /**
     * clear command
     * clear collection
     */

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) {
        CommandResponseDto<ClearCommandDto> dto = new CommandResponseDto<>(new ClearCommandDto());
        boolean auth = Commands.checkAuth(params);
        if (!auth) {
            dto.setResponse("you should be authorized");
        } else {
            try {
                int count = Commands.getDatabase().executeUpdate("delete from workers where username = ?", params.getLogin());
                set.removeIf(worker -> worker.getUser().equals(params.getLogin()));
                dto.setResponse("deleted " + count + " elements");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);


    }
}
