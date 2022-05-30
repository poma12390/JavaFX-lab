package lab6.server;


import lab6.common.dto.ClearCommandDto;
import lab6.common.dto.CommandRequestDto;
import lab6.common.dto.PackageDto;
import lab6.server.commands.Commands;
import lab6.server.setters.DiagnosticSignalHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;


public class ServerRunner implements SignalHandler{
    public static BlockingQueue<CommandRequestDto<? extends Serializable>> queueToProcess = null;
    public static BlockingQueue<PackageDto> queueToSend = null;

    private static final Logger logger
            = LoggerFactory.getLogger(ServerRunner.class);

    public ServerRunner() {
        queueToProcess  = new LinkedBlockingDeque<>();
        queueToSend = new LinkedBlockingDeque<>();
    }

    public static void main(String[] args) {

        ServerRunner serverRunner = new ServerRunner();


        Commands.temporaryStart();
        Commands.dataBaseToCollection();


        new Thread(() -> {
            ClientReceiver receiver = new ClientReceiver();
            receiver.run();
            // выполнение в отдельном потоке

        }).start();
        System.out.println("                                     ....................  ................                                                    \n" +                "                                      . .. ...     ...........-*++... .. ...                                                   \n" +                "                                      ............ .......*#######*.. .......                                                  \n" +                "                                      .....................@@+*@##:...... ...                                                  \n" +                "                          .  ..............-%######%...........###:..........                                                  \n" +                "             .   ....   .........-+=:.......*##@:*##=.........-###:.........                                                   \n" +                "                .. ...   ......######*.......%##+-##*.........:###-.... ...                                                    \n" +                "              .......... ......##=.@##-......:########+.......+###.... ....                                                    \n" +                "            ....-##@...........%#%--###.......###+.-%##-......=@+.........                                                     \n" +                "             ....=##-..........=#######%......:######%............... .... .                                                   \n" +                "            .....*##%..........*##*..%##*......@@=:........ .............. ..                                                  \n" +                "             ....-@##-..........##+..-@@*. .  .....             ....  ........ -*=%@%%+-.. ..... .....                         \n" +                "            ......=##@@##@-.....##=....... ..                   ..  .....:*##@+-.......:=@#@:-........                         \n" +                "            ......-######@-............ .....                  .......@#%:............. .....:%#+....                          \n" +                "             ..   .**-................                   ..........=#%. .. ..... .... ...      ..=@*. ......                   \n" +                "            ... ......................                   .......:@#-..  . ..%%......+%...      ....:#=.....                    \n" +                "            ... ... ......                               .....-@%...--:::+#@- .......#:..      ......+@-....                   \n" +                "             .     ......                                 ...%#-:@##=+:%##=... .......=@-.... ...... .:#-...                   \n" +                "                                                         ..-%-*#########=.-%@:  .... ...+%#%+**::-......#*..                   \n" +                "                                                         .+@=#####...#####:.-#%.. ..  .-@#+----*##@@#=...#:.                   \n" +                "                                                   ..  ..@%+############%#@...#*... -+########=:..*#*....:=.                   \n" +                "                                                   .. ..%=-@%#########%..@#*..#+..-%#####:%######%..@#-. .=-                   \n" +                "                                                   ....*@-#*%###%:*+-...:##-..#*.*@*####:.-@###@=##-.=%. .+%                   \n" +                "                                                   ....#*.#*.###=....-###-...%@.=#.=##########:. :#@.-@:..-#                   \n" +                "                                                  ....-@..*%--*%###%==-.....%@..@-.=####=%%+.....:#=..=#. .#....               \n" +                "                                                   ...=@..-%*:::-....-::*.:#*...@-.:###=........@#=...+# ..#:..                \n" +                "                                                   ...%@....+=--:::::--:+#+.....@=..:#####%%%###=-....:#.. #+.. ..             \n" +                "                                                  ....%@..-*..+########*.........#*...*######+........+#.  #+....              \n" +                "                                                  ....-@-.......-#*...............%*::-..........-::-%#* ..#-.....             \n" +                "                                                   ....#+..........................*#*-::::::::::-:@#-..  :#  .. ..            \n" +                "                                                   ....:#-............ ...............+@@@@@#####+*.......=+                   \n" +                "                                                    ....*#. .. ...  .. .. ..  ...  ...-..........#=......:#-                   \n" +                "                                                   ... ...%=........ .*===-...--..... ..................-#*.                   \n" +                "                                                          .*#:..  . ...+#.:%*-.-*%###*-................+#:..                   \n" +                "                                                          ...*#*.........:=@=+.......:@#@=*..........-@%....                   \n" +                "                                                          .. . -%@...  ....... ..-:-...............-#@.....                    \n" +                "                                                         .. . ....-+%%-.......... ..............+%#+.. . ..                    \n" +                "                                                                . .. ..+##+--.. .........--*###+..:#-                          \n" +                "                                                               .........+%*%#*+=#####%%=**:. . ....=#-                         \n" +                "                                                                ..... *+. . .@#=....@#.-----:::::::-#@                         \n" +                "                                                                      @*......#+.....%@**:--..-----.-#*    .                   \n" +                "                                                                      .%@++*+@#:....-@=... .  ..  ...@=..  .                   \n" +                "                                                                      .*#.:%- -=#@+*:*#@*..  ...... .:#:. ..                   \n" +                "                                                                      ..%#:%-      .....*@##=:-......-#: ...                   \n" +                "                                                                      ...##%.     ....... ..-:+##@+...=@....                   \n" +                "                                                                      ....%%.      ...............*@%.+#. .                    \n" +                "                                                                       ...+@.                         -#*..                    \n" +                "                                                                      ... =%.                         .@@  .                   \n" +                "                                                                      . . %=.                         .@# .                    \n" +                "                                                                      ..  #+                           @# .                    \n" +                "                                                                      ....#++++===%%%%%%%%%%%%%%%%%%%%=##..                    \n" +                "                                                                      . ..==%%%==++++++++++++++****:*+++=...                   \n" +                "                                                                      . .  .       ............      ... .                     \n" +                "                                                                                                                               \n" +                "                                                                                                                               \n" +                "                                                                                                                               \n" +                "                                                                                                                               \n");
        new Thread(() -> {
            // process queue
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            while (true){
                try {
                    CommandRequestDto<? extends Serializable> commandRequestDto = ServerRunner.queueToProcess.take();
                    //logger.info("Receive command " + commandRequestDto.getCommandName());
                    logger.info("doing " + commandRequestDto.getCommandName());
                    executorService.execute(() -> Commands.runCommandFromString(Commands.getWorkersSet(), commandRequestDto.getCommandName(), commandRequestDto));

                } catch (InterruptedException e) {
                    executorService.shutdown();
                    break;
                }
            }
        }).start();


        new Thread(() -> {
            // send queue

            ExecutorService executorService = Executors.newFixedThreadPool(5);
            while (true){
                try {
                    PackageDto packageDto = ServerRunner.queueToSend.take();
                    ClientCaller clientCaller = new ClientCaller();
                    executorService.execute(() -> clientCaller.send(packageDto));
                } catch (InterruptedException e) {
                    executorService.shutdown();
                    break;
                }

            }

        }).start();


        new Thread(() -> {
            InputStream inputStream = System.in;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String input ="";
            while (true) {
                try {
                    input = bufferedReader.readLine();
                    if (input.equals("save")){
                        Commands.runCommandFromString(Commands.getWorkersSet(), "save",new CommandRequestDto<>("save", new ClearCommandDto()));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }catch (NullPointerException ignored){
                }
            }

            // выполнение в отдельном потоке

        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    logger.info("Shutting down ...");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });

        SignalHandler signalHandler = new SignalHandler() {

            @Override
            public void handle(Signal signal) {
                logger.info("saving collection");
                Commands.runCommandFromString(Commands.getWorkersSet(), "save",new CommandRequestDto<>("save", new ClearCommandDto()));
            }
        };
        DiagnosticSignalHandler.install("TERM", signalHandler);
        DiagnosticSignalHandler.install("INT", signalHandler);
        DiagnosticSignalHandler.install("ABRT", signalHandler);


    }

    @Override
    public void handle(Signal signal) {

    }
}