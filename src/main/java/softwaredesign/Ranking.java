package softwaredesign;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Ranking implements Command{

    public static final Integer LIMIT = 15;
    public final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");

    private String[] args;
    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length != 0){
            switch (args[0]){
                case "commit":
                    Command rankComm = new RankingCommit();
                    rankComm.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    rankComm.execute(log);
                    break;
                case "contributor":
                    Command rankCont = new RankingContributor();
                    rankCont.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    rankCont.execute(log);
                    break;
                default:
                    System.out.println("\u001B[31mPlease enter a valid command, type 'help' for more info \u001B[0m");
                    break;
            }
        }
        else {
            Command rankComm = new RankingCommit();
            rankComm.setArgs(Arrays.copyOfRange(args, 0, args.length));
            rankComm.execute(log);

            Command rankCont = new RankingContributor();
            rankCont.setArgs(Arrays.copyOfRange(args, 0, args.length));
            rankCont.execute(log);
        }
        return Boolean.FALSE;
    }
}
