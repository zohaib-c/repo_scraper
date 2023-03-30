package softwaredesign;

/*
 * total number of commits: x
 * commit with the highest churn: x
 * first commit: x
 * most recent commit: x
 * commit with the most additions: x
 * */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatsCommits extends Stats implements Command {

    @Override
    public Boolean execute(GitLog log) {
        List<GitCommit> repoCommits = log.getCommits();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");

        int numOfCommits = 0;
        for (GitCommit commit: repoCommits){
            numOfCommits++;
        }

        System.out.println("Total number of commits: " + numOfCommits);
        Date date = new java.util.Date(repoCommits.get(repoCommits.size()-1).getUnixDate() * 1000L);
        System.out.println("First commit: '" + repoCommits.get(repoCommits.size()-1).getMessage() + "' written by " + repoCommits.get(repoCommits.size()-1).getAuthor() + " on " + sdformat.format(date));
        date = new java.util.Date(repoCommits.get(0).getUnixDate() * 1000L);
        System.out.println("Most recent commit: '" + repoCommits.get(0).getMessage() + "' written by " + repoCommits.get(0).getAuthor() + " on " + sdformat.format(date));
        date = new java.util.Date(repoCommits.get(repoCommits.size()-1).getUnixDate() * 1000L);
        System.out.println("Commit with the highest Churn: '" + repoCommits.get(0).getMessage() + "' written by " + repoCommits.get(0).getAuthor() + " on " + sdformat.format(date));

        return Boolean.TRUE;
    }
}
