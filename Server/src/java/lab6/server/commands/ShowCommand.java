package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import lab6.common.dto.ShowCommandDto;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class ShowCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(ShowCommand.class);


    /**
     * show command
     * show all obj from in Collection
     */

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) {

        ShowCommandDto showCommandDto = new ShowCommandDto();
        CommandResponseDto<ShowCommandDto> commandResponseDto = new CommandResponseDto<>(showCommandDto);

        showCommandDto.setWorkers(set
                .stream()
                .sorted(Comparator.comparing(Worker::getName))
                .collect(Collectors.toList())
        );


        PackageDto packageDto = new PackageDto(commandResponseDto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);
        //clientCaller.send(packageDto);

        //clientCaller.sendToClient(Transformer.serialize(commandResponseDto));
    }
}

