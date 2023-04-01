package softwaredesign;

import java.util.*;

public class RankingCommitChurn extends RankingCommit implements Command{
    static class MapValueSorter implements Comparator<String> {
        HashMap<String, Integer> commits;

        public MapValueSorter(HashMap<String, Integer> commits){this.commits = commits;}

        @Override
        public int compare(String a, String b) {
            return commits.get(b).compareTo(commits.get(a));
        }
    }

    private List<GitCommit> repoCommits;

    private final List<Integer> deletions = new ArrayList<>();

    private final List<Integer> additions = new ArrayList<>();

    private final HashMap<String, Integer> commitsChurn = new HashMap<>();

    private TreeMap<String, Integer> rankedCommits;

    private void calculateChurn(){
        for (int i = 0; i < additions.size(); i++){
            int churn = additions.get(i) + deletions.get(i);
            commitsChurn.put(repoCommits.get(i).getMessage(), churn);
        }
    }

    private void printResult(){
        int counter = 0;

        System.out.println("\nList of commits ranked by the highest churn: ");
        for (Map.Entry<String, Integer> entry: rankedCommits.entrySet()){
            if(Objects.equals(counter, LIMIT)) break;
            System.out.println(counter + 1 + ". " + entry.getKey() + " - Churn: "  + entry.getValue());
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
            repoCommits = log.getCommits();

            //Get commit additions
            for (GitCommit commit: repoCommits){
                additions.add(commit.getTotalAdditions());
            }

            //Get commit deletions
            for (GitCommit commit: repoCommits){
                deletions.add(commit.getTotalDeletions());
            }

            calculateChurn();

            //Sort
            rankedCommits = new TreeMap<>(new MapValueSorter(commitsChurn));
            rankedCommits.putAll(commitsChurn);

            printResult();

            return Boolean.TRUE;
        }
    }
}
