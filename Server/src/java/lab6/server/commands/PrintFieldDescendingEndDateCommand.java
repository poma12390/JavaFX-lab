package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import lab6.common.dto.PrintFieldDescendingEndDateCommandDto;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintFieldDescendingEndDateCommand extends BaseCommand {
    @Override
    public String getName() {
        return "print_field_descending_end_date";
    }

    /**
     * printFieldDescendingDate command
     * show sorted endDate
     */

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) {
        PrintFieldDescendingEndDateCommandDto dts = new PrintFieldDescendingEndDateCommandDto();
        CommandResponseDto<PrintFieldDescendingEndDateCommandDto> dto = new CommandResponseDto<>(dts);

        List<Date> dates = Arrays.stream(set.stream().flatMap((p) -> Stream.of(p.getEndDate())).toArray(Date[]::new)).collect(Collectors.toList());//.sorted().collect(Collectors.toList());
        long[] lng = Transformer.DateListToLongArray(dates);
        ForkJoinTask<?> task = new Concurrent_MergeSort(lng, 0, lng.length - 1);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        dates = Transformer.longArrayToDateList(lng);
        //System.out.println(dates);
        // На часах 5:20 Жестко переписал с Stream Api

        dts.setDates(dates);

        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

    }
}
