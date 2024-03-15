package softwaredesign;

import java.util.*;

public class RankingContributorTime extends RankingContributor implements Command {

    static class MapValueSorter implements Comparator<String> {
        HashMap<String, Date> uniqueAuthors;

        public MapValueSorter(HashMap<String, Date> uniqueAuthors){
            this.uniqueAuthors = uniqueAuthors;
        }

        @Override
        public int compare(String a, String b) {
            return Integer.compare(uniqueAuthors.get(a).compareTo(uniqueAuthors.get(b)), 0);
        }
    }

    private final List<String> authors = new ArrayList<>();

    private final List<Date> dates = new ArrayList<>();

    private final HashMap<String, Date> uniqueAuthors = new HashMap<>();

    private TreeMap<String, Date> rankedAuthors;

    private void calculateContributorTime(){
        Collections.reverse(dates);
        Collections.reverse(authors);

        for (int i = 0; i < authors.size(); i++){
            if (!uniqueAuthors.containsKey(authors.get(i))){
                uniqueAuthors.put(authors.get(i), dates.get(i));
            }
        }
    }

    private void printResult(){
        int counter = 0;
        int limit;

        if (rankedAuthors.size() < LIMIT) limit = rankedAuthors.size();
        else limit = LIMIT;

        System.out.println("\nList of top contributors ranked by who is in the project for the longest time: ");

        for (Map.Entry<String, Date> entry: rankedAuthors.entrySet()){
            if (counter == limit) break;
            System.out.println(counter + 1 + ". " + entry.getKey() + " - First commit: "  + dateFormat.format(entry.getValue()));
            counter++;
        }
        System.out.println("\n");
    }

    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
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

            //Get list of commit dates
            for (GitCommit commit: repoCommits){
                long unixDate = commit.getUnixDate();
                Date date = new java.util.Date(unixDate * 1000L);
                dates.add(date);
            }

            calculateContributorTime();

            //Sort
            rankedAuthors = new TreeMap<>(new MapValueSorter(uniqueAuthors));
            rankedAuthors.putAll(uniqueAuthors);

            printResult();

            History.getInstance().push("ranking contributor time");

            return Boolean.TRUE;
        }
    }
}
