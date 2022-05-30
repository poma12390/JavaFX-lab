package lab6.server.commands;

import lab6.common.Transformer;
import lab6.common.Worker;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.CommandResponseDto;
import lab6.common.dto.PackageDto;
import lab6.common.dto.RegisterCommandDto;
import lab6.common.exceptions.InvalidDateFormatException;
import lab6.common.exceptions.InvalidEndDateException;
import lab6.common.exceptions.InvalidSalaryException;
import lab6.server.ClientCaller;
import lab6.server.ServerRunner;
import lab6.server.database.Database;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashSet;

public class RegisterCommand extends BaseCommand {

    @Override
    protected void Execute(CommandRequestDto<? extends Serializable> params, LinkedHashSet<Worker> set, Transformer transformer, ClientCaller clientCaller) throws IOException, InvalidSalaryException, InvalidDateFormatException, ParseException, InvalidEndDateException {
        RegisterCommandDto registerCommandDto = (RegisterCommandDto) params.getCommandArgs();
        CommandResponseDto<RegisterCommandDto> dto = new CommandResponseDto<>(registerCommandDto);
        int random = Commands.getRandomNumber();
        boolean repLogin = false;

        Database database = Commands.getDatabase();
        String  password =(Transformer.Encrypt(registerCommandDto.getPassword(), String.valueOf(random)));
//        try {
//            int request1 = database.executeUpdate("INSERT INTO users VALUES (nextval('IdSetter')," + "\'"+ registerCommandDto.getLogin()+ "\', \'" + password +"\', \'" + random+ "\');");
//            dto.setResponse("success");
//        } catch (SQLException e) {
//            dto.setResponse(e.getMessage());
//        }

        try {
            ResultSet set1 = database.executeQuery("select * from users where username = ?", registerCommandDto.getLogin()); //Check repeat login
            while (set1.next()){
                repLogin = true;
            }
            if (!repLogin){
                int request = database.executeUpdate("INSERT INTO users VALUES (nextval('IdSetter'), ?, ?, ?)",
                        registerCommandDto.getLogin(), password, random);
                dto.setResponse("success");
            }
            else {
                dto.setResponse("Login already exists");
            }database.closeQuery();
//              String sql="INSERT INTO users VALUES (nextval('IdSetter')," + "\'"+ registerCommandDto.getLogin()+ "\', \'" + password +"\', \'" + random+ "\');";
//          System.out.println(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PackageDto packageDto = new PackageDto(dto,params.getHost(),params.getPort(), params.getDs());
        ServerRunner.queueToSend.add(packageDto);

    }
}
