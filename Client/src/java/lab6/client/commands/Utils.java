package lab6.client.commands;


import lab6.client.memory.LoginPassword;
import lab6.client.setterrs.*;

import lab6.common.Coordinates;
import lab6.common.Person;
import lab6.common.Position;
import lab6.common.Worker;
import lab6.common.exceptions.EndStreamException;
import lab6.common.exceptions.InvalidDataException;
import lab6.common.exceptions.MissedCommandArgumentException;
import lab6.gui.main.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.*;

import static lab6.client.inputters.InputUtils.*;


public class Utils {
    private static final Logger logger
            = LoggerFactory.getLogger(Utils.class);


    public static BufferedReader currentBufferedReader;

    public static boolean blockPrompts = false;

    public static void setBlockPrompts(boolean blockPrompts) {
        Utils.blockPrompts = blockPrompts;
    }

    public static void setIsFileExecuted(boolean isFileExecuted) {
        Utils.isFileExecuted = isFileExecuted;
    }

    public static boolean isFileExecuted = false;

    public static BufferedReader getCurrentBufferedReader() {
        return currentBufferedReader;
    }

    public static void setCurrentBufferedReader(BufferedReader currentBufferedReader) {
        Utils.currentBufferedReader = currentBufferedReader;
    }
    private static List<lab6.client.commands.BaseCommand> commands = Arrays.asList(
            new ShowCommand(),
            new ExitCommand(),
            new HelpCommand(),
            new InfoCommand(),
            new AddCommand(),
            new AddIfMinCommand(),
            new ClearCommand(),
            new ExecuteScriptCommand(),
            new FilterBySalaryCommand(),
            new HistoryCommand(),
            new PrintFieldDescendingEndDateCommand(),
            new RemoveLowerCommand(),
            new RemoveByIdCommand(),
            new RemoveAllByEndDateCommand(),
            new SaveCommand(),
            new UpdateIdCommand(),
            new AuthCommand(),
            new RegisterCommand(),
            new QuitCommand()
    );


    /**
     * updateAll command
     * @param bum Woker to update it's stats
     */

    public static void updateAll(Worker bum) {

        String input = inputString("(string) name");
        bum.setName(input);

        Coordinates cord = new Coordinates();
        long x = inputLong("(int) x");
        int y = inputInt("(long) y");

        Float salary = inputFloat("(float) salary");
        bum.setSalary(salary);
        cord.setXY(x, y);
        bum.setCoordinates(cord);

        Date startDate = inputData("(date) start date");
        bum.setStartDate(startDate);
        Date endDate;
        do {
            endDate = inputData("(date) end date > start date");
            bum.setEndDate(endDate);
        } while (startDate.after(endDate) | startDate.compareTo(endDate)==0);

        Person pers = new Person();
        ZonedDateTime birthday = inputZonedDate("(date) birthday");
        pers.setBirthday(birthday);

        Float height = inputFloat("(float) height");
        pers.setHeight(height);
        Float weight = inputFloat("(float) weight");
        pers.setWeight(weight);

        bum.setPerson(pers);

        bum.setPosition(inputPosition(
                new StringJoiner(",")
                        .add(Position.BAKER.toString())
                        .add(Position.DIRECTOR.toString())
                        .add(Position.ENGINEER.toString())
                        .add(Position.LABORER.toString())
                        .add(Position.MANAGER.toString())
                        .toString()
        ));
        bum.setCreationDate(new Date());
    }
    public static Worker upload(String[] sts, Worker bum) {

        try {
            System.out.println(Arrays.toString(sts));
            String name = sts[0].trim();
            SetName.setname(name, bum);
            String x = sts[1].trim();
            String y = sts[2].trim();
            SetCordinates.setcordinates(x, y, bum);
            String salary = sts[3].trim();
            SetSalary.setSalary(salary, bum);
            String startDate = sts[4].trim();
            SetData.setStartData(startDate, bum);
            String endDate = sts[5].trim();
            SetData.setEndData(endDate, bum);
            String birthday = sts[6].trim();
            Person person = new Person();
            SetPersParams.setBirthday(birthday, person);
            String height = sts[7].trim();
            SetPersParams.setHeight(height, person);
            String weight = sts[8].trim();
            SetPersParams.setWeight(weight, person);
            bum.setPerson(person);
            String pos = sts[9].trim();
            SetPosition.setPosition(pos, bum);
        }
        catch (Exception e){
            bum.setId(1);
            MainFrame.errors.add(e.getMessage());
        }
        return bum;
    }




    static boolean test = true;
    public static void runCommandFromString(String input) {

        try {
            test = false;
            String[] items = input.split(" ");
            String cmd = items[0].toLowerCase();
            List<String> params = new ArrayList<>();
            for (int i = 1; i < items.length; i++) {
                params.add(items[i]);
            }

            //runCommand(workers, cmd, params);
            runCommand2(cmd, params);
            if (!test){
                logger.warn("no such method");
            }
        } catch (NullPointerException | NoSuchElementException e) {
            funExit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void runCommand2(String commandName, List<String> commandParams) {
        for (BaseCommand command: commands) {
            if (command.getName().equalsIgnoreCase(commandName)) {
                try {
                    command.ExecuteCommand(commandParams);

                    test = true;
                }catch (MissedCommandArgumentException | MissingFormatArgumentException e) {
                    logger.warn(e.getMessage());
                    test = true;

                }catch (EndStreamException ignored){

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void funExit(){
        System.out.println("для выхода я написал комманду exit");
        System.out.println(" +\"\"\"\"\"+ ");
        System.out.println("[| o o |]");
        System.out.println(" |  ^  | ");
        System.out.println(" | '-' | ");
        System.out.println(" +-----+ ");
        System.exit(1);
    }

}
