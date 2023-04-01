package softwaredesign;

import java.util.Arrays;

public class RankingCommit extends Ranking implements Command {

    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length != 0){
            switch (args[0]){
                case "churn":
                    Command rankCommChurn = new RankingCommitChurn();
                    rankCommChurn.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    return rankCommChurn.execute(log);
                case "recent":
                    Command rankCommRecent = new RankingCommitRecent();
                    rankCommRecent.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    return rankCommRecent.execute(log);
                default:
                    System.out.println("\u001B[31mPlease enter a valid command, type 'help' for more info \u001B[0m");
                    break;
            }
        }
        else {
            Command rankCommChurn = new RankingCommitChurn();
            rankCommChurn.setArgs(Arrays.copyOfRange(args, 0, args.length));
            rankCommChurn.execute(log);

            Command rankCommRecent = new RankingCommitRecent();
            rankCommRecent.setArgs(Arrays.copyOfRange(args, 0, args.length));
            rankCommRecent.execute(log);

            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
