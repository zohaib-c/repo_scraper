package softwaredesign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Ranking extends Application implements Command{

    private String[] args;
    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public void execute() {
//        String command = "git log --pretty='%an' | sort | uniq -c | sort -nr";
        if (args.length != 0){
            System.out.println("args != null\n");
            System.out.println(Arrays.toString(Arrays.stream(args).toArray()));
            switch (args[0]){
                case "commit":
                    //
                case "contributor":
                    Command rankCont = new RankingContributor();
                    rankCont.execute();
            }
        }
        else {
            String command = "git log --pretty='%an'";
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

            processBuilder.directory(new File("/Users/zohaibzaheer/Desktop/javaTest/First_Website"));

            try {
                Process process = processBuilder.start();
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } else {
                    throw new RuntimeException("git log failed with exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
