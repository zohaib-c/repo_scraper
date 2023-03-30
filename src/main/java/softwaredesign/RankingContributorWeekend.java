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

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    private String[] args;

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

            List<Integer> dates = new ArrayList<>();

            for (GitCommit commit: repoCommits){
                long unixDate = commit.getUnixDate();;
                Date date = new java.util.Date(unixDate * 1000L);
                dates.add(date.getDay());
            }

            HashMap<String, Integer> uniqueAuthors = new HashMap<>();


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


            TreeMap<String, Integer> rankedAuthors = new TreeMap<>(new MapValueSorter(uniqueAuthors));
            rankedAuthors.putAll(uniqueAuthors);

            Integer counter = 0;

            System.out.println("\nList of contributors ranked by who contributed most on the weekends: ");
            for (HashMap.Entry<String, Integer> entry: rankedAuthors.entrySet()){
                if(counter == limit) break;
                System.out.println(counter + 1 + ". " + entry.getKey() + " - Number of commits: "  + entry.getValue());
                counter++;
            }
            System.out.println("\n");

            return Boolean.TRUE;
        }
    }
}
