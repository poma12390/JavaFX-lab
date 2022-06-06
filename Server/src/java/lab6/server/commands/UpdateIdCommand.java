package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import lab6.common.dto.UpdateIdCommandDto;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

public class UpdateIdCommand extends BaseCommand {
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
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) throws IOException {
        UpdateIdCommandDto updateIdCommandDto = (UpdateIdCommandDto) params.getCommandArgs();
        CommandResponseDto<UpdateIdCommandDto> dto = new CommandResponseDto<>(updateIdCommandDto);
        boolean auth = Commands.checkAuth(params);
        if (!auth) {
            dto.setResponse("you should be authorized");
        } else {
            if (updateIdCommandDto.getWorkerDto() != null) {
                Worker newbum = Transformer.WorkerDtoToWorker(updateIdCommandDto.getWorkerDto());
                Worker bum = Commands.getWorkerById(updateIdCommandDto.getWorkerId());
                Commands.updateWorkerById(updateIdCommandDto.getWorkerId(), newbum);
                if (bum.getUser().equals(updateIdCommandDto.getWorkerDto().getUser())) {
                    Transformer.WorkerToWorker(bum, newbum);
                    dto.setResponse("Success");
                }
                else {
                    dto.setResponse("Not your element");
                }
            } else {
                try {
                    ResultSet set1 = Commands.getDatabase().executeQuery("select * from workers where id = ?", updateIdCommandDto.getWorkerId());
                    if (set1.next()) {
                        dto.setResponse("Correct id");
                    } else {
                        dto.setResponse("UnCorrect Id");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }
        }
        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

    }
}
