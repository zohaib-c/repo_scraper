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
    public Boolean execute(GitLog log) {
        if (args.length != 0){
            System.out.println("args != null\n");
            System.out.println(Arrays.toString(Arrays.stream(args).toArray()));
            switch (args[0]){
                case "commits":
                    Command rankContCommits = new RankingContributorCommits();
                    rankContCommits.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    return rankContCommits.execute(log);
                case "time":
                    Command rankContTime = new RankingContributorTime();
                    rankContTime.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    return rankContTime.execute(log);
                case "weekend":
                    Command rankContWeekend = new RankingContributorWeekend();
                    rankContWeekend.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    return rankContWeekend.execute(log);
                case "weekday":
                    Command rankContWeekday = new RankingContributorWeekday();
                    rankContWeekday.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    return rankContWeekday.execute(log);
            }
        }
        else {
            //
        }
        return Boolean.FALSE;
    }
}
