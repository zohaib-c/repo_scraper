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
    private List<GitCommit> commits;

    public GitLog(){
        cloneDate = LocalDateTime.now();
        this.commits = new ArrayList<>();
    }

    public List<GitCommit> getCommits() {
        return commits;
    }

    private GitCommit parseCommit(String log){
        String[] parts = log.split("\t");

        String sha = parts[0];
        String author = parts[1];
        String email = parts[2];
        long unixDate = Long.parseLong(parts[3]);
        String messageSubject = parts[4];

        return new GitCommit(sha, author, email, unixDate, messageSubject);
    }

    public Boolean runGitLog(String dir){
        Boolean logSuccesful = Boolean.FALSE;
        String command = "git log --pretty=format:%H\t%an\t%ae\t%at\t%s";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

        processBuilder.directory(new File(System.getProperty("user.dir") + "/" + dir));

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                logSuccesful = Boolean.TRUE;
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
                    commits.add(parseCommit(line));
                }
            } else {
                throw new RuntimeException("git log failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    return logSuccesful;
    }
}
