package lab6.client.commands;

import lab6.client.inputters.InputUtils;
import lab6.client.memory.LoginPassword;
import lab6.common.Transformer;
import lab6.common.dto.AuthCommandDto;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.RegisterCommandDto;
import lab6.common.exceptions.InvalidDateFormatException;
import lab6.common.exceptions.InvalidEndDateException;
import lab6.common.exceptions.InvalidSalaryException;
import lab6.gui.AuthFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class AuthCommand extends BaseCommand{
    private static final Logger logger
            = LoggerFactory.getLogger(AuthCommand.class);



    @Override
    protected void Execute(List<String> params) throws IOException, InvalidSalaryException, InvalidDateFormatException, ParseException, InvalidEndDateException {
        LoginPassword.setPassword(null); // Типо вышли из системы
        LoginPassword.setLogin(null);

        AuthCommandDto dto = new AuthCommandDto();
        System.out.println(params.get(0));
        System.out.println(params.get(1));
        String login = params.get(0);
        dto.setLogin(login);
        String password = Transformer.Encrypt(params.get(1),"1");
        dto.setPassword(password);

        CommandRequestDto<AuthCommandDto> crd = new CommandRequestDto<>(getName(), dto);
        byte[] buf = serverCaller.sendToServer(transformer.Serialize(crd));
        CommandResponseDto response = (CommandResponseDto) transformer.DeSerialize(buf);

        System.out.println(response.getResponse());
        if (response.getResponse().equals("success")){
            LoginPassword.setLogin(login);
            LoginPassword.setPassword(password);
        }
        AuthFrame.responses.add(response.getResponse());

    }
}
