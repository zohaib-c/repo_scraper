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
                default:
                    System.out.println("\u001B[31mPlease enter a valid command, type 'help' for more info \u001B[0m");
                    break;
            }
        }
        else {
            Command rankContCommits = new RankingContributorCommits();
            rankContCommits.setArgs(Arrays.copyOfRange(args, 0, args.length));
            rankContCommits.execute(log);

            Command rankContTime = new RankingContributorTime();
            rankContTime.setArgs(Arrays.copyOfRange(args, 0, args.length));
            rankContTime.execute(log);

            Command rankContWeekend = new RankingContributorWeekend();
            rankContWeekend.setArgs(Arrays.copyOfRange(args, 0, args.length));
            rankContWeekend.execute(log);

            Command rankContWeekday = new RankingContributorWeekday();
            rankContWeekday.setArgs(Arrays.copyOfRange(args, 0, args.length));
            rankContWeekday.execute(log);

            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
