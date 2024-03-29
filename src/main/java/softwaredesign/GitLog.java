package softwaredesign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GitLog {
    private final List<GitCommit> commits;

    public GitLog(){
        this.commits = new ArrayList<>();
    }

    public List<GitCommit> getCommits() {
        return commits;
    }

    private GitCommit parseCommit(String log){
        String[] parts = log.split("\t");

        String author = parts[0];
        long unixDate = Long.parseLong(parts[1]);
        String messageSubject = parts[2];

        String[] statsParts = Arrays.copyOfRange(parts, 3, parts.length);

        int additions = 0;
        int deletions = 0;

        for (int i = 0; i < statsParts.length; i++){
            if (i % 3 == 0) { // every third entry (including the 0th) is additions
                try {
                    additions += Integer.parseInt(statsParts[i]);
                }
                catch (NumberFormatException e){
                    /*
                    sometimes the file added is, for example, a picture and git uses the '-' to represent no lines added,
                    in that case the Integer.parseInt function throws this exception. We catch this exception and add 0
                    to deletions.
                     */
                    additions += 0;
                }
            }
            else if (i % 3 == 1){ // every fourth entry (including the 1st) is deletions
                try {
                    deletions += Integer.parseInt(statsParts[i]);
                }
                catch (NumberFormatException e){
                    /*
                    sometimes the file added is, for example, a picture and git uses the '-' to represent no lines added,
                    in that case the Integer.parseInt function throws this exception. We catch this exception and add 0
                    to deletions.
                     */
                    deletions += 0;
                }
            }
        }

        return new GitCommit(author, unixDate, messageSubject, additions, deletions);
    }

    public Boolean runGitLog(String dir){
        Boolean logSuccessful = Boolean.FALSE;
        /*
        Ths git log command uses pretty format argument to get the following attributes of commits:
            - an = author name
            - at = unix time of commit
            - s = commit message subject

        The numstat argument is used to get a concise version of commit stats as needed, it outputs:
            additions deletions fileChanged
         */
        String command = "git log --pretty=format:--BEGIN--\n%an\t%at\t%s --numstat";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

        processBuilder.directory(new File(System.getProperty("user.dir") + "/" + dir));

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                logSuccessful = Boolean.TRUE;
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder singleCommit = new StringBuilder();
                String line;

                /*
                This loop starts reading at "--BEGIN--" and reads until it encounters another "--BEGIN--" or an empty line.
                This is because of the format in which git log function returns (see report)
                 */
                while ((line = reader.readLine()) != null) {
                    if (line.equals("--BEGIN--")){
                        line = reader.readLine();
                        while (!Objects.equals(line, "--BEGIN--") && line != null){
                            if (line.equals("")){
                                break;
                            }
                            singleCommit.append(line).append("\t");
                            line = reader.readLine();
                        }
                    }
                    if (singleCommit.length() != 0){
                        commits.add(parseCommit(singleCommit.toString()));
                        singleCommit = new StringBuilder();
                    }

                }
            } else {
                throw new RuntimeException("git log failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    return logSuccessful;
    }
}
