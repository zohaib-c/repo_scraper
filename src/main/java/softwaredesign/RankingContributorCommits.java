package softwaredesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingContributorCommits extends Application implements Command{
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

            for (HashMap.Entry<String, Integer> entry: uniqueAuthors.entrySet()){
                System.out.println(entry.getKey() + ": "  + entry.getValue());
            }
        }
    }
}
