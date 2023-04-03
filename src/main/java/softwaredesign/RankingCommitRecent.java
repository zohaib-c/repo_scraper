package softwaredesign;

import java.util.*;

public class RankingCommitRecent extends RankingCommit implements Command{
    private String[] args;

    private List<GitCommit> repoCommits;

    private void printResult(){
        System.out.println("\nList of most recent commits: ");

        int limit;

        if (repoCommits.size() < LIMIT) limit = repoCommits.size();
        else limit = LIMIT;

        for (int i = 0; i < limit; i++) {
            GitCommit commit = repoCommits.get(i);
            long unixDate = commit.getUnixDate();
            Date date = new java.util.Date(unixDate * 1000L);
            System.out.println(i+1 + ". " + commit.getMessage() + " made by " +
                    commit.getAuthor() + " on " + dateFormat.format(date));
        }

        System.out.println("\n");
    }

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length !=0){
            return Boolean.FALSE;
        }
        else{
            repoCommits = log.getCommits();

            printResult();

            History.getInstance().push("ranking commit recent");

            return Boolean.TRUE;
        }
    }
}
