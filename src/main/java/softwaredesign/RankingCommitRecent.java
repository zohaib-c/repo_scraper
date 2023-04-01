package softwaredesign;

import java.text.SimpleDateFormat;
import java.util.*;

public class RankingCommitRecent extends RankingCommit implements Command{
    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length !=0){
            System.err.println("DEBUG: There should be no args here"); //TODO: Delete
            return Boolean.FALSE;
        }
        else{
            List<GitCommit> repoCommits = log.getCommits();

            System.out.println("\nList of " + limit + "most recent commits: ");

            for (int i = 0; i < limit; i++) {
                GitCommit commit = repoCommits.get(i);
                long unixDate = commit.getUnixDate();
                Date date = new java.util.Date(unixDate * 1000L);
                System.out.println(i+1 + ". " + commit.getMessage() + " made by " +
                        commit.getAuthor() + " on " + dateformat.format(date));
            }


            System.out.println("\n");

            return Boolean.TRUE;
        }
    }
}
