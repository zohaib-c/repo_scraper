package softwaredesign;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatsCommits extends Stats implements Command {

    private List<GitCommit> repoCommits;

    private int calculateChurn(){
        List<Integer> deletions = new ArrayList<>();
        List<Integer> additions = new ArrayList<>();

        for (GitCommit commit: repoCommits){
            additions.add(commit.getTotalAdditions());
        }

        for (GitCommit commit: repoCommits){
            deletions.add(commit.getTotalDeletions());
        }

        int churn =  additions.get(0) + deletions.get(0);
        int indexHighestChurn = 0;

        for (int i = 1; i < additions.size(); i++){
            if (additions.get(i) + deletions.get(i) > churn) {
                churn = additions.get(i) + deletions.get(i);
                indexHighestChurn = i;
            }
        }

        return indexHighestChurn;
    }

    @Override
    public Boolean execute(GitLog log) {
        repoCommits = log.getCommits();

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");

        int numOfCommits = repoCommits.size();
        int commitIndexHighestChurn = calculateChurn();

        System.out.println("Total number of commits: " + numOfCommits);
        Date date = new java.util.Date(repoCommits.get(repoCommits.size()-1).getUnixDate() * 1000L);
        System.out.println("First commit: '" + repoCommits.get(repoCommits.size()-1).getMessage() + "' written by " + repoCommits.get(repoCommits.size()-1).getAuthor() + " on " + sdformat.format(date));
        date = new java.util.Date(repoCommits.get(0).getUnixDate() * 1000L);
        System.out.println("Most recent commit: '" + repoCommits.get(0).getMessage() + "' written by " + repoCommits.get(0).getAuthor() + " on " + sdformat.format(date));
        date = new java.util.Date(repoCommits.get(repoCommits.size()-1).getUnixDate() * 1000L);
        System.out.println("Commit with the highest Churn: '" + repoCommits.get(commitIndexHighestChurn).getMessage() + "' written by " + repoCommits.get(commitIndexHighestChurn).getAuthor() + " on " + sdformat.format(date));

        return Boolean.TRUE;
    }
}
