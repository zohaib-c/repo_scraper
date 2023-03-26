package softwaredesign;

import java.util.Arrays;
import java.util.List;

public class RankingContributor extends Ranking implements Command{

    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public void execute(GitLog log) {
        if (args.length != 0){
            System.out.println("args != null\n");
            System.out.println(Arrays.toString(Arrays.stream(args).toArray()));
            switch (args[0]){
                case "commits":
                    Command rankContCommits = new RankingContributorCommits();
                    rankContCommits.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    rankContCommits.execute(log);
                case "time":
                    //
                case "weekend":
                    //
                case "weekday":
                    //
            }
        }
        else {
            List<GitCommit> repoCommits = log.getCommits();
            //
        }
    }
}
