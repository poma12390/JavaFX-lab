package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.AuthCommandDto;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import lab6.common.exceptions.InvalidDateFormatException;
import lab6.common.exceptions.InvalidEndDateException;
import lab6.common.exceptions.InvalidSalaryException;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;
import lab6.server.database.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.Objects;

public class AuthCommand extends BaseCommand{
    private static final Logger logger
            = LoggerFactory.getLogger(AuthCommand.class);

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) throws IOException, InvalidSalaryException, InvalidDateFormatException, ParseException, InvalidEndDateException {
        AuthCommandDto authCommandDto = (AuthCommandDto) params.getCommandArgs();
        Database database = Commands.getDatabase();
        boolean repLogin = false;
        String login = authCommandDto.getLogin();
        try {
            ResultSet set1 = database.executeQuery("select * from users where username = ?", login); //Check repeat login
            while (set1.next()){
                String salt = set1.getString("salt");
                String password1 = set1.getString("password");
                String  password =(Transformer.Encrypt(authCommandDto.getPassword(), salt));
                if (Objects.equals(password, password1)){
                    repLogin = true;
                }

            }database.closeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CommandResponseDto<AuthCommandDto> dto = new CommandResponseDto<>(authCommandDto);
        if (repLogin){
            dto.setResponse("success");
            logger.info(login + " logged in");

        }
        else {
            dto.setResponse("incorrect login or password");
        }
        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

    }
}
