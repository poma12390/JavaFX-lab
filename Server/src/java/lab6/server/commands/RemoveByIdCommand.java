package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import lab6.common.dto.RemoveByIdCommandDto;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashSet;

public class RemoveByIdCommand extends BaseCommand {
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
     *
     * @param params id of worker to delete
     *               delete worker from collections with id
     */

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) {
        boolean auth = Commands.checkAuth(params);
        RemoveByIdCommandDto removeByIdCommandDto = (RemoveByIdCommandDto) params.getCommandArgs();
        CommandResponseDto<RemoveByIdCommandDto> dto = new CommandResponseDto<>(removeByIdCommandDto);
        if (!auth) {
            dto.setResponse("you should be authorized");
        } else {

            int id = removeByIdCommandDto.getId();
            //long count = (set.stream().filter((p) -> p.getId() == id).count());
            try {
                int count = Commands.getDatabase().executeUpdate("delete from workers where username = ? and id = ?", params.getLogin(), id);
                removeByIdCommandDto.setCount(count);

                set.removeIf(worker -> (worker.getId().equals(id) && worker.getUser().equals(params.getLogin())));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

    }
}


