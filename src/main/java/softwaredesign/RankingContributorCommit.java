package softwaredesign;

import java.util.*;

public class RankingContributorCommit extends RankingContributor implements Command{


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

    private final HashMap<String, Integer> uniqueAuthors = new HashMap<>();

    private TreeMap<String, Integer> rankedAuthors;

    private void countCommits(){
        for (String author: authors){
            if (uniqueAuthors.containsKey(author)){
                uniqueAuthors.put(author, uniqueAuthors.get(author)+1);
            }
            else{
                uniqueAuthors.put(author, 1);
            }
        }
    }

    private void printResult(){
        System.out.println("\nList of top contributors ranked by number of commits made: ");

        int counter = 0;
        int limit;

        if (rankedAuthors.size() < LIMIT) limit = rankedAuthors.size();
        else limit = LIMIT;

        for (Map.Entry<String, Integer> entry: rankedAuthors.entrySet()){
            if (Objects.equals(counter, limit)) break;
            System.out.println(counter + 1 + ". " + entry.getKey() + ": "  + entry.getValue());
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
        if (args.length !=0){
            return Boolean.FALSE;
        }
        else{
            List<GitCommit> repoCommits = log.getCommits();

            //Get list of commit authors
            for (GitCommit commit: repoCommits){
                authors.add(commit.getAuthor());
            }

            countCommits();

            //Sort
            rankedAuthors = new TreeMap<>(new MapValueSorter(uniqueAuthors));
            rankedAuthors.putAll(uniqueAuthors);

            printResult();

            History.getInstance().push("ranking contributor commit");

            return Boolean.TRUE;
        }
    }
}
