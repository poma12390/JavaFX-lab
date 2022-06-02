package lab6.client.commands;

import lab6.client.memory.LoginPassword;
import lab6.common.Transformer;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.RegisterCommandDto;
import lab6.common.exceptions.InvalidDateFormatException;
import lab6.common.exceptions.InvalidEndDateException;
import lab6.common.exceptions.InvalidSalaryException;
import lab6.gui.RegisterFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class RegisterCommand extends BaseCommand {
    private static final Logger logger
            = LoggerFactory.getLogger(RegisterCommand.class);

    @Override
    protected void Execute(List<String> params) throws IOException, InvalidSalaryException, InvalidDateFormatException, ParseException, InvalidEndDateException {
        LoginPassword.setPassword(null); // Типо вышли из системы
        LoginPassword.setLogin(null);

            RegisterCommandDto dto = new RegisterCommandDto();
            dto.setLogin(params.get(0));
            dto.setPassword(Transformer.Encrypt(params.get(1), "1"));

            CommandRequestDto<RegisterCommandDto> crd = new CommandRequestDto<>(getName(), dto);
            byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));
            CommandResponseDto response = (CommandResponseDto) transformer.DeSerialize(buf);
            RegisterFrame.responses.add(response.getResponse());
    }
}
