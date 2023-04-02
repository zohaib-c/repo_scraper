package softwaredesign;

import java.util.Arrays;

public class RankingContributor extends Ranking implements Command{

    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        History h = History.getInstance();
        if (args.length != 0){
            switch (args[0]){
                case "commit":
                    Command rankContCommits = new RankingContributorCommit();
                    rankContCommits.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    h.push("ranking contributor commit");
                    return rankContCommits.execute(log);
                case "time":
                    Command rankContTime = new RankingContributorTime();
                    rankContTime.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    h.push("ranking contributor time");
                    return rankContTime.execute(log);
                case "weekend":
                    Command rankContWeekend = new RankingContributorWeekend();
                    rankContWeekend.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    h.push("ranking contributor weekend");
                    return rankContWeekend.execute(log);
                case "weekday":
                    Command rankContWeekday = new RankingContributorWeekday();
                    rankContWeekday.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    h.push("ranking contributor weekday");
                    return rankContWeekday.execute(log);
                default:
                    System.out.println("\u001B[31mPlease enter a valid command, type 'help' for more info \u001B[0m");
                    break;
            }
        }
        else {
            Command rankContCommits = new RankingContributorCommit();
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

            h.push("ranking contributor");
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
