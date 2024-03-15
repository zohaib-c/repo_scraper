package softwaredesign;

import java.util.*;

public class StatsContributor extends Stats implements Command {

    private final List<String> allContributors = new ArrayList<>();
    private final List<Integer> dates = new ArrayList<>();

    private Map.Entry<String, Integer> workDistributionWeek(int from, int until) {
        HashMap<String, Integer> uniqueAuthors = new HashMap<>();
        for (int i = 0; i < dates.size(); i++){
            if(dates.get(i) == from || dates.get(i) == until || (dates.get(i) > from && dates.get(i) < until)){
                if (uniqueAuthors.containsKey(allContributors.get(i))){
                    uniqueAuthors.put(allContributors.get(i), uniqueAuthors.get(allContributors.get(i))+1);
                }
                else{
                    uniqueAuthors.put(allContributors.get(i), 1);
                }
            }
        }

        if (uniqueAuthors.isEmpty()) {
            uniqueAuthors.put("empty", 0);
        }

        TreeMap<String, Integer> rankedAuthors = new TreeMap<>(new RankingContributorCommit.MapValueSorter(uniqueAuthors));
        rankedAuthors.putAll(uniqueAuthors);

        return rankedAuthors.firstEntry();
    }

    @Override
    public Boolean execute(GitLog log) {
        List<GitCommit> repoCommits = log.getCommits();
        Map.Entry<String, Integer> mostCommits;
        Map.Entry<String, Integer> mostWeekends;
        Map.Entry<String, Integer> mostWeekdays;

        int numOfContr = 0;
        for (GitCommit commit: repoCommits){
            long unixDate = commit.getUnixDate();
            Date date = new java.util.Date(unixDate * 1000L);
            dates.add(date.getDay());
            allContributors.add(commit.getAuthor());
        }

        // We decided to use a HashMap to be able to look up the amount of commits for each contributor
        HashMap<String, Integer> uniqueAuthors = new HashMap<>();
        for (String author: allContributors){
            if (uniqueAuthors.containsKey(author)){
                uniqueAuthors.put(author, uniqueAuthors.get(author)+1);
            }
            else{
                uniqueAuthors.put(author, 1);
                numOfContr++;
            }
        }

        // The TreeMap is a Data Structure that allows for sorted key-value pairs
        TreeMap<String, Integer> rankedAuthors = new TreeMap<>(new RankingContributorCommit.MapValueSorter(uniqueAuthors));
        rankedAuthors.putAll(uniqueAuthors);
        mostCommits = rankedAuthors.firstEntry();

        mostWeekends = workDistributionWeek(6,0);
        mostWeekdays = workDistributionWeek(1, 5);


        System.out.println("Number of contributors: " + numOfContr);
        System.out.println("Contributor with the most commits: " + mostCommits.getKey() + " with " + mostCommits.getValue() + " commits");
        if(Objects.equals(mostWeekdays.getValue(), 0)) {
            System.out.println("No one contributed on weekdays.");
        }
        else {
            System.out.println("Contributor who worked most from Monday to Friday: " + mostWeekdays.getKey() + " with " + mostWeekdays.getValue() + " commits");
        }
        if(Objects.equals(mostWeekends.getValue(), 0)) {
            System.out.println("No one contributed during the weekend.");
        }
        else {
            System.out.println("Contributor who worked most during the weekends: " + mostWeekends.getKey() + " with " + mostWeekends.getValue() + " commits");
        }
        System.out.println("List of all contributors: ");
        for (String contr: uniqueAuthors.keySet()) {
            System.out.println(contr);
        }
        System.out.println("\n");

        History.getInstance().push("stats contributor");

        return Boolean.TRUE;
    }
}
