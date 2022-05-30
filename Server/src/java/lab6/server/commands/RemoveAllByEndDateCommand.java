package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import lab6.common.dto.RemoveAllByEndDateCommandDto;
import lab6.common.exceptions.InvalidDateFormatException;
import lab6.common.exceptions.InvalidEndDateException;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;

public class RemoveAllByEndDateCommand extends BaseCommand {
    @Override
    public String getName() {
        return "remove_all_by_end_date";
    }

    @Override
    protected int getCommandParamsCount() {
        return 1;
    }

    /**
     * removeByEndDate command
     *
     * @param params end date to delete elements with
     *               delete elemets with input end date
     */

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) throws InvalidDateFormatException, ParseException, InvalidEndDateException {
        boolean auth = Commands.checkAuth(params);
        RemoveAllByEndDateCommandDto removeAllByEndDateCommandDto = (RemoveAllByEndDateCommandDto) params.getCommandArgs();
        CommandResponseDto<RemoveAllByEndDateCommandDto> dto = new CommandResponseDto<>(removeAllByEndDateCommandDto);
        if (!auth) {
            dto.setResponse("you should be authorized");
        } else {
            Date endDate = removeAllByEndDateCommandDto.getEndDate();
            //long count = (set.stream().filter((p) -> p.getEndDate().equals(endDate)).count());
            try {
                int count = Commands.getDatabase().executeUpdate("delete from workers where username = ? and enddate = ?", params.getLogin(), endDate);
                set.removeIf(worker -> (worker.getEndDate().equals(endDate) && worker.getUser().equals(params.getLogin())));
                removeAllByEndDateCommandDto.setCount(count);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Optional<Worker> workers = set.stream().filter((p)-> p.getEndDate().equals(endDate)).findAny();
            //Commands.getIds().removeIf(p -> p.equals(work.getId()));


        }
        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

    }
}
