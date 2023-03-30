package softwaredesign;

/*
        * list of all contributors: x
        * total number of contributors: x
        * contributor who worked most on weekends: x
        * contributor with the most/least commits: x
        * contributor who has worked on it for the longest/shortest: x
*/

import java.util.*;

public class StatsContributors extends Stats implements Command {
    @Override
    public Boolean execute(GitLog log) {
        List<GitCommit> repoCommits = log.getCommits();
        List<String> allContributors = new ArrayList<>();
        List<Integer> dates = new ArrayList<>();

        int numOfContr = 0;
        for (GitCommit commit: repoCommits){
            long unixDate = commit.getUnixDate();
            Date date = new java.util.Date(unixDate * 1000L);
            dates.add(date.getDay());
            allContributors.add(commit.getAuthor());
        }

        HashMap<String, Integer> uniqueAuthors = new HashMap<>(); //<authorName, numOfCommits>
        for (String author: allContributors){
            if (uniqueAuthors.containsKey(author)){
                uniqueAuthors.put(author, uniqueAuthors.get(author)+1);
            }
            else{
                uniqueAuthors.put(author, 1);
                numOfContr++;
            }
        }

        TreeMap<String, Integer> rankedAuthors = new TreeMap<>(new RankingContributorCommits.MapValueSorter(uniqueAuthors));
        rankedAuthors.putAll(uniqueAuthors);

        Map.Entry<String, Integer> mostCommits = rankedAuthors.firstEntry();

        for (int i = 0; i < dates.size(); i++){
            if(dates.get(i) == 6 || dates.get(i) == 0){
                if (uniqueAuthors.containsKey(allContributors.get(i))){
                    uniqueAuthors.put(allContributors.get(i), uniqueAuthors.get(allContributors.get(i))+1);
                }
                else{
                    uniqueAuthors.put(allContributors.get(i), 1);
                }
            }
        }

        rankedAuthors = new TreeMap<>(new RankingContributorCommits.MapValueSorter(uniqueAuthors));
        rankedAuthors.putAll(uniqueAuthors);

        Map.Entry<String, Integer> mostWeekends = rankedAuthors.firstEntry();

        System.out.println("Number of contributors: " + numOfContr);
        System.out.println("List of all contributors: ");
        for (String contr: uniqueAuthors.keySet()) {
            System.out.println(contr);
        }
        System.out.println("Contributor with the most commits: " + mostCommits.getKey() + " with " + mostCommits.getValue() + " commits");
        System.out.println("Contributor who worked most during the weekends: " + mostWeekends.getKey()+ "\n");

        return Boolean.TRUE;
    }
}
