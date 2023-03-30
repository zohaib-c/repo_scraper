package softwaredesign;

/*
        * list of all contributors: x
        * total number of contributors: x
        * contributors who worked most on weekends: x
        * contributor with the most/least commits: x
        * contributor who has worked on it for the longest/shortest: x
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatsContributors extends Stats implements Command {
    @Override
    public Boolean execute(GitLog log) {
        List<GitCommit> repoCommits = log.getCommits();
        List<String> allContributors = new ArrayList<>();
        int numOfContr = 0;
        for (GitCommit commit: repoCommits){
            boolean alreadyThere = false;
            for (String contributor: allContributors) {
                if (Objects.equals(contributor, commit.getAuthor())) {
                    alreadyThere = true;
                    break;
                }
            }
            if (alreadyThere) continue;
            allContributors.add(commit.getAuthor());
            numOfContr++;
        }

        System.out.println("Number of contributors: " + numOfContr);
        System.out.println("List of all contributors: \n");
        for (String contr: allContributors) {
            System.out.println(contr);
        }


        return Boolean.TRUE;
    }
}
