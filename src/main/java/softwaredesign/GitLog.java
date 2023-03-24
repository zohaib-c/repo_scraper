package softwaredesign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GitLog extends Application{
    private LocalDateTime cloneDate;
    private List<GitCommits> commits;

    public GitLog(){
        cloneDate = LocalDateTime.now();
        this.commits = new ArrayList<>();
    }

    public void runGitLog(String dir){
        String command = "git log --pretty=format:\\\"%H\\t%an\\t%ae\\t%ad\\t%s\\t%P\\t%t\\t%D\\t%P\\t%ct\\t%ctz\\t%b---GITSTATS---%n%ai%d%cN%f%l%d%b\\\"";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

        processBuilder.directory(new File(System.getProperty("user.dir") + "/" + dir));

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
