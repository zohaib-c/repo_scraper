package softwaredesign;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.*;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.awt.Desktop;


public class SystemCommands {

    private PrintStream redirectOutput(String repoName) throws IOException {
        String fileName = "report.txt";
        Path path = Paths.get(fileName);
        if (Files.notExists(path)) {
            Files.createFile(path);
        } else {
            // Clear the file contents if it exists
            Files.write(path, new byte[0]);
        }

        PrintStream outReference = System.out;

        FileOutputStream fos = new FileOutputStream(fileName, false); // Append mode
        PrintStream fileOut = new PrintStream(fos);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        String currentDateAndTime = formatter.format(new Date());
        fileOut.println("This report was created on " + currentDateAndTime + " and is based on the repository " + repoName + ".");
        fileOut.println();

        System.setOut(fileOut); // Redirect the standard output stream to the file

        return outReference;
    }

    private static List<String[]> getReportCommands() {
        System.out.println("Please enter a list of commands you want included in the report. Enter 'quit' to finish.");
        List<String[]> reportCommands = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                break;
            }
            String[] commandParts = input.split(" ");
            reportCommands.add(commandParts);
        }

        return reportCommands;
    }

    public static void openReport() throws IOException {
        String fileName = "report.txt";
        if (!Desktop.isDesktopSupported()) {
            throw new UnsupportedOperationException("Desktop is not supported on this platform.");
        }

        Desktop desktop = Desktop.getDesktop();

        if (!desktop.isSupported(Desktop.Action.OPEN)) {
            throw new UnsupportedOperationException("The open action is not supported on this platform.");
        }

        File file = new File(fileName);
        desktop.open(file);
    }

    private static void helperDelRepo(File directory){
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    helperDelRepo(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    private static void deleteRepo(String repoName){

        if(repoName.isEmpty()) return;

        String dirPath = System.getProperty("user.dir") + "/" + repoName;
        File dir = new File(dirPath);

        if(!dir.exists()){
            System.err.println("DEBUG: some error with path or repo name, dir not deleted");
            return;
        }

        helperDelRepo(dir);
    }

    public Boolean quit(String repoName){
        deleteRepo(repoName);

        try {
            System.exit(0);
            return Boolean.TRUE;
        }
        catch (Exception e){
            System.err.println("Could not exit process");
            return Boolean.FALSE;
        }
    }

    public Boolean help(){
        System.out.println("The user can mine different information from the github repo by issuing command-line instructions following this syntax.\n" +
                "In order to use the ranking option adhere to the following usage:\n" +
                "\n" +
                "Usage:\n" +
                "  ranking <command> <subcommand>\n" +
                "  stats <command>\n" +
                "\n" +
                "where command and subcommand are optional parameters.\n" +
                "\n" +
                "  ranking \t\t\t\t- rank all possible information\n" +
                "  stats \t\t\t\t- print all statistics\n" +
                "\n" +
                "Available commands and sub commands for ranking:\n" +
                "  commit \t\t\t\t- rank commits based on churn and recent\n" +
                "  commit churn \t\t- rank commits based on churn\n" +
                "  commit recent \t\t- rank commits based on recent\n" +
                "  contributor \t\t\t- rank contributors based on commits, weekend, time, and weekday\n" +
                "  contributor commit \t- rank contributors based on the numbers of commits\n" +
                "  contributor weekend \t- rank contributors based on who worked most on the weekends\n" +
                "  contributor time \t\t- rank contributors based on who is in the project for the longest time\n" +
                "  contributor weekday \t- rank contributors based on who worked most on given weekday\n" +
                "\n" +
                "Available commands for stats:\n" +
                "  commit \t\t\t\t- print stats of commits\n" +
                "  contributor \t\t\t- print stats of contributors\n" +
                "\n" +
                "Available system commands:\n" +
                "  history \t\t\t\t- prints history of all commands (please note that history also contains all subcommands, if there are any, of any entered command)\n" +
                "  help \t\t\t\t\t- prints possible commands\n" +
                "  report \t\t\t\t- create a report as a text file with your desired commands\n" +
                "  restart \t\t\t\t- restarts the program\n" +
                "  quit \t\t\t\t\t- quits the program\n" +
                " \n" +
                "Examples:\n" +
                "  $ ranking commit\n" +
                "  $ ranking contributor commit\n" +
                "  $ stats commit\n" +
                "  $ ranking\n" +
                "  $ stats contributor\n" +
                "  $ history\n");

        return Boolean.TRUE;
    }

    public Boolean report(GitLog log,String repoName){

        List<String[]> reportCommands = getReportCommands();
        PrintStream originalOutput;

        try {
            originalOutput = redirectOutput(repoName);
        } catch (IOException e) {
            System.err.println("Failed to redirect output: " + e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }

        for(String[] command : reportCommands) {
            switch (command[0]) {
                case "ranking":
                    Command rankingCommand = new Ranking();
                    rankingCommand.setArgs(Arrays.copyOfRange(command, 1, command.length));
                    rankingCommand.execute(log);
                    break;
                case "stats":
                    Command statsCommand = new Stats();
                    statsCommand.setArgs(Arrays.copyOfRange(command, 1, command.length));
                    statsCommand.execute(log);
                    break;
                default:
                    System.out.println("Your command " + command[0] + " could not be recognized!");
            }
        }

        System.setOut(originalOutput);

        try{
            openReport();
            return Boolean.TRUE;
        } catch (IOException e) {
            System.err.println("Error opening the report.txt file: " + e.getMessage());
            return Boolean.FALSE;
        }
    }

    public Boolean history(){
        if (History.getInstance().isEmpty()){
            System.out.println("There are no commands in history yet. As you start entering commands they will be " +
                    "stored in history and then you can view them.");
            return Boolean.TRUE;
        }

        try {
            History history = History.getInstance();
            System.out.println("\nHistory of the previously entered commands: ");
            while (!history.isEmpty()) {
                System.out.println("\t\t" + history.pop());
            }
            System.out.println("\n");
            return Boolean.TRUE;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Could not print history");
            return Boolean.FALSE;
        }
    }

    public Boolean restart(String repoName) {
        try {
            String javaBin = System.getProperty("java.home") +
                    File.separator + "bin" + File.separator + "java";
            List<String> command = new ArrayList<>();
            command.add(javaBin);

            // Add JVM arguments
            List<String> jvmArguments =
                    ManagementFactory.getRuntimeMXBean().getInputArguments();
            command.addAll(jvmArguments);

            // Add classpath
            command.add("-cp");
            command.add(System.getProperty("java.class.path"));

            // Delete cloned repository
            deleteRepo(repoName);

            command.add(Application.class.getName());
            ProcessBuilder builder = new ProcessBuilder(command);

            builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = builder.start();

            try {
                process.waitFor();
            } catch (InterruptedException e) {
                System.err.println("Process interrupted: " + e.getMessage());
                return Boolean.FALSE;
            }

        } catch (IOException e) {
            System.err.println("Failed to restart the application: " + e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }

        System.exit(0);
        return Boolean.TRUE;
    }
}
