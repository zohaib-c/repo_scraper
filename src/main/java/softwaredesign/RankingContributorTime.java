package softwaredesign;

import java.util.*;
import java.text.*;

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

    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length != 0) {
            System.err.println("DEBUG: There should be no args here");
            return Boolean.FALSE;
        } else {
            List<GitCommit> repoCommits = log.getCommits();

            List<String> authors = new ArrayList<>();
            for (GitCommit commit: repoCommits){
                authors.add(commit.getAuthor());
            }

            List<Date> dates = new ArrayList<>();
            for (GitCommit commit: repoCommits){
                long unixDate = commit.getUnixDate();
                Date date = new java.util.Date(unixDate * 1000L);
                dates.add(date);
            }

            Collections.reverse(dates);
            Collections.reverse(authors);

            HashMap<String, Date> uniqueAuthors = new HashMap<>();

            for (int i = 0; i < authors.size(); i++){
                if (!uniqueAuthors.containsKey(authors.get(i))){
                    uniqueAuthors.put(authors.get(i), dates.get(i));
                }
            }

            TreeMap<String, Date> rankedAuthors = new TreeMap<>(new MapValueSorter(uniqueAuthors));
            rankedAuthors.putAll(uniqueAuthors);

            Integer counter = 0;

            System.out.println("\nList of top " + limit + " contributors ranked by who is in the project for the longest time: ");

            for (HashMap.Entry<String, Date> entry: rankedAuthors.entrySet()){
                if (counter == limit) break;
                System.out.println(counter + 1 + ". " + entry.getKey() + " - First commit: "  + dateformat.format(entry.getValue()));
                counter++;
            }
            System.out.println("\n");

            return Boolean.TRUE;
        }
    }
}
