package softwaredesign;

import java.util.*;

public class RankingContributorCommits extends Application implements Command{

    static class MapValueSorter implements Comparator<String> {
        HashMap<String, Integer> uniqueAuthors;

        public MapValueSorter(HashMap<String, Integer> uniqueAuthors){
            this.uniqueAuthors = uniqueAuthors;
        }

        @Override
        public int compare(String a, String b) {
            if (uniqueAuthors.get(a) >= uniqueAuthors.get(b)){
                return -1;
            }
            else if (uniqueAuthors.get(a) <= uniqueAuthors.get(b)){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public void execute(GitLog log) {
        if (args.length !=0){
            System.err.println("DEBUG: There should be no args here");
        }
        else{
            List<GitCommit> repoCommits = log.getCommits();
            List<String> authors = new ArrayList<>();
            for (GitCommit commit: repoCommits){
                authors.add(commit.getAuthor());
            }

            HashMap<String, Integer> uniqueAuthors = new HashMap<>();
            for (String author: authors){
                if (uniqueAuthors.containsKey(author)){
                    uniqueAuthors.put(author, uniqueAuthors.get(author)+1);
                }
                else{
                    uniqueAuthors.put(author, 1);
                }
            }

            TreeMap<String, Integer> rankedAuthors = new TreeMap<>(new MapValueSorter(uniqueAuthors));
            rankedAuthors.putAll(uniqueAuthors);

            for (HashMap.Entry<String, Integer> entry: rankedAuthors.entrySet()){
                System.out.println(entry.getKey() + ": "  + entry.getValue());
            }
        }
    }
}
