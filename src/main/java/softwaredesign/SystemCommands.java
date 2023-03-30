package softwaredesign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;


public class SystemCommands {

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

    public void quit(String repoName){
        if (!repoName.isEmpty()) {
            String dirPath = System.getProperty("user.dir") + "/" + repoName;
            File dir = new File(dirPath);

            if (dir.exists()) {
                helperDelRepo(dir);
            } else {
                System.err.println("DEBUG: some error with path or repo name, dir not deleted");
            }
        }

        System.exit(0);
    }

    public void help(){
        System.out.println("The user can mine different information from the github repo by issuing command-line instructions following this syntax.\n" +
                "In order to use the ranking option adhere to the following usage:\n" +
                "\n" +
                "Usage:\n" +
                "  ranking <command> <subcommand>\n" +
                "  stats <command> <subcommand>\n" +
                "\n" +
                "where command and subcommand are optional parameters.\n" +
                "\n" +
                "  ranking \t\t\t\t- rank all possible information\n" +
                "  stats \t\t\t\t- print all statistics\n" +
                "\n" +
                "Available commands and sub commands for ranking:\n" +
                "  commits \t\t\t\t- rank commits based on churn and recent\n" +
                "  commits churn \t\t- rank commits based on churn\n" +
                "  commits recent \t\t- rank commits based on recent\n" +
                "  contributor \t\t\t- rank contributors based on commits, weekend, time, and weekday\n" +
                "  contributor commits \t- rank contributors based on the numbers of commits\n" +
                "  contributor weekend \t- rank contributors based on who worked most on the weekends\n" +
                "  contributor time \t\t- rank contributors based on who is in the project for the longest time\n" +
                "  contributor weekday \t- rank contributors based on who worked most on given weekday\n" +
                "\n" +
                "Available commands for stats:\n" +
                "  commits \t\t\t\t- print stats of commits\n" +
                "  contributors \t\t\t- print stats of contributors\n" +
                "\n" +
                "Available system commands:\n" +
                "  history \t\t\t\t- prints history of all commands\n" +
                "  help \t\t\t\t\t- prints possible commands\n" +
                "  restart \t\t\t\t- restarts the program\n" +
                "  quit \t\t\t\t\t- quits the program\n" +
                " \n" +
                "Examples:\n" +
                "  $ ranking commits\n" +
                "  $ rankin contributor commits\n" +
                "  $ stats commits\n" +
                "  $ ranking\n" +
                "  $ stats contributor\n" +
                "  $ history\n");
    }

    public void history(History history){
        //
    }

    public void restart() {
        try {
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            List<String> command = new ArrayList<>();
            command.add(javaBin);

            // Add JVM arguments
            List<String> jvmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            command.addAll(jvmArguments);

            // Add classpath
            command.add("-cp");
            command.add(System.getProperty("java.class.path"));

            command.add(Application.class.getName());
            ProcessBuilder builder = new ProcessBuilder(command);

            builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectError(ProcessBuilder.Redirect.INHERIT);

            builder.start();
            System.exit(0);

        } catch (IOException e) {
            System.err.println("Failed to restart the application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //TODO @zohaib make sure this works
    public void authenticate(String accessToken){
        AuthRequest authRes = new AuthRequest(accessToken);
        authRes.authenticate();
    }

}
