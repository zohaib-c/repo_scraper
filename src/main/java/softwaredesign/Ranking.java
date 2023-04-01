package softwaredesign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ranking extends Application implements Command{

    public static Integer limit = 15;
    public static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");

    private String[] args;
    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length != 0){
            System.out.println("args != null\n"); //TODO: delete this
            System.out.println(Arrays.toString(Arrays.stream(args).toArray()));
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
