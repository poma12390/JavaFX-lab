package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.*;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashSet;


public class AddIfMinCommand extends BaseCommand {
    @Override
    public String getName() {
        return "add_If_min";
    }

    /**
     * addIfMin command
     * add new Worker if it's min in coll
     */

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) throws IOException {
        AddIfMinCommandDto addIfMinCommandDto = (AddIfMinCommandDto) params.getCommandArgs();
        WorkerDto workerDto = addIfMinCommandDto.getBum();
        Worker bum = Transformer.WorkerDtoToWorker(workerDto);
        CommandResponseDto<AddIfMinCommandDto> dto = new CommandResponseDto<>(addIfMinCommandDto);
        boolean auth = Commands.checkAuth(params);
        if (!auth) {
            dto.setResponse("you should be authorized");
        } else {
            if (set.size() == 0) {
                Commands.addWorkerToDataBase(bum);
                set.add(bum);
            } else {
                Worker min = set.stream().min(Worker::compareTo).get(); //stream Api
                if (bum.compareTo(min) < 0) {
                    bum.setId(Commands.addWorkerToDataBase(bum));
                    set.add(bum);
                    dto.setResponse("success");

                } else {
                    dto.setResponse("not min element");
                }
            }

        }
        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

    }
}
