package softwaredesign;

import java.util.*;

public class RankingContributorWeekend extends RankingContributor implements Command {
    static class MapValueSorter implements Comparator<String> {
        HashMap<String, Integer> uniqueAuthors;

        public MapValueSorter(HashMap<String, Integer> uniqueAuthors){
            this.uniqueAuthors = uniqueAuthors;
        }

        @Override
        public int compare(String a, String b) {
            return uniqueAuthors.get(b).compareTo(uniqueAuthors.get(a));
        }
    }

    private final List<String> authors = new ArrayList<>();

    private final List<Integer> dates = new ArrayList<>();

    private final HashMap<String, Integer> uniqueAuthors = new HashMap<>();

    private TreeMap<String, Integer> rankedAuthors;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    private String[] args;

    private void sumWeekendCommits(){
        for (int i = 0; i < dates.size(); i++){
            if(dates.get(i) == 6 || dates.get(i) == 0){
                if (uniqueAuthors.containsKey(authors.get(i))){
                    uniqueAuthors.put(authors.get(i), uniqueAuthors.get(authors.get(i))+1);
                }
                else{
                    uniqueAuthors.put(authors.get(i), 1);
                }
            }
        }
    }

    private void printResult(){
        int counter = 0;
        int limit = 0;

        if (rankedAuthors.size() < LIMIT) limit = rankedAuthors.size();
        else limit = LIMIT;

        System.out.println("\nList of contributors ranked by who contributed most on the weekends: ");
        for (Map.Entry<String, Integer> entry: rankedAuthors.entrySet()){
            if(Objects.equals(counter, limit)) break;
            System.out.println(counter + 1 + ". " + entry.getKey() + " - Number of commits: "  + entry.getValue());
            counter++;
        }
        System.out.println("\n");
    }


    @Override
    public Boolean execute(GitLog log) {
        if (args.length != 0) {
            return Boolean.FALSE;
        } else {
            List<GitCommit> repoCommits = log.getCommits();

            //Get list of commit authors
            for (GitCommit commit: repoCommits){
                authors.add(commit.getAuthor());
            }

            //Get days of the week from commits log
            for (GitCommit commit: repoCommits){
                long unixDate = commit.getUnixDate();;
                Date date = new java.util.Date(unixDate * 1000L);
                dates.add(date.getDay());
            }

            sumWeekendCommits();

            //Sort
            rankedAuthors = new TreeMap<>(new MapValueSorter(uniqueAuthors));
            rankedAuthors.putAll(uniqueAuthors);

            printResult();

            return Boolean.TRUE;
        }
    }
}
