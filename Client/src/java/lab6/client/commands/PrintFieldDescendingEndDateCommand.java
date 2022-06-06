package lab6.client.commands;

import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PrintFieldDescendingEndDateCommandDto;
import lab6.gui.main.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrintFieldDescendingEndDateCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(PrintFieldDescendingEndDateCommand.class);
    @Override
    public String getName() {
        return "print_field_descending_end_date";
    }

    /**
     * printFieldDescendingDate command
     * show sorted endDate
     */

    @Override
    protected void Execute(List<String> params) {
        ParamsChecker.checkParams(0, params);
        PrintFieldDescendingEndDateCommandDto dto = new PrintFieldDescendingEndDateCommandDto();
        CommandRequestDto<PrintFieldDescendingEndDateCommandDto> crd = new CommandRequestDto<>(getName(), dto);

        byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));

        CommandResponseDto<PrintFieldDescendingEndDateCommandDto> response = (CommandResponseDto) transformer.DeSerialize(buf);
        dto =  response.getCommandArgs();
        List<Date> responselist = dto.getDates();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        for (Date i : responselist){
            logger.info(simpleDateFormat.format(i) + "; ");
            MainFrame.responses.add(simpleDateFormat.format(i));
            MainFrame.responses.add("\r\n");
        }

    }
}
