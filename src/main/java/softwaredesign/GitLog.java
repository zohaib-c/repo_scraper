package softwaredesign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GitLog extends Application{
    private LocalDateTime cloneDate;
    private final List<GitCommit> commits;

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

        String[] statsParts = Arrays.copyOfRange(parts, 5, parts.length);

        int numFilesChanged = statsParts.length/3;
        int additions = 0;
        int deletions = 0;

        for (int i = 0; i < statsParts.length; i++){
            if (i % 3 == 0) {
                additions += Integer.parseInt(statsParts[i]);
            }
            else if (i % 3 == 1){
                deletions += Integer.parseInt(statsParts[i]);
            }
        }

        return new GitCommit(sha, author, email, unixDate, messageSubject, additions, deletions, numFilesChanged);
    }

    public Boolean runGitLog(String dir){
        Boolean logSuccesful = Boolean.FALSE;
        String command = "git log --pretty=format:--BEGIN--\n%H\t%an\t%ae\t%at\t%s --numstat";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

        processBuilder.directory(new File(System.getProperty("user.dir") + "/" + dir));

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                logSuccesful = Boolean.TRUE;
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder singleCommit = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("--BEGIN--")){
                        line = reader.readLine();
                        while (!Objects.equals(line, "") && line != null){
                            singleCommit.append(line).append("\t");
                            line = reader.readLine();
                        }
                    }

                    commits.add(parseCommit(singleCommit.toString()));
                    singleCommit = new StringBuilder();
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
