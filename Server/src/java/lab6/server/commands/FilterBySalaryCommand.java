package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.FilterBySalaryCommandDto;
import lab6.common.dto.PackageDto;
import lab6.common.exceptions.InvalidSalaryException;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FilterBySalaryCommand extends BaseCommand {
    @Override
    public String getName() {
        return "filter_by_salary";
    }

    @Override
    protected int getCommandParamsCount() {
        return 1;
    }

    /**
     * FilterBySalary command
     * show elements sorted by salary
     */


    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) throws InvalidSalaryException {
        FilterBySalaryCommandDto filterBySalaryCommandDto = (FilterBySalaryCommandDto) params.getCommandArgs();
        CommandResponseDto<FilterBySalaryCommandDto> dto = new CommandResponseDto<>(filterBySalaryCommandDto);
        float salary = filterBySalaryCommandDto.getSalary();
        String response = "";

        List<Worker> workers = (set.stream().filter((p) -> p.getSalary() == salary).collect(Collectors.toList())); // Получаем нужных челов
        response = response + "Всего найдено " + workers.size() + " челов" + "\r\n";
        filterBySalaryCommandDto.setWorkers(workers);

        dto.setResponse(response);


        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

    }
}


