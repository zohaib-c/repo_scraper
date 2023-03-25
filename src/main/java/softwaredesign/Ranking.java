package softwaredesign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ranking extends Application implements Command{

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
                case "commit":
                    //
                case "contributor":
                    Command rankCont = new RankingContributor();
                    rankCont.setArgs(Arrays.copyOfRange(args, 1, args.length));
                    rankCont.execute(log);
                    break;
            }
        }
        else {
            List<GitCommit> repoCommits = log.getCommits();
            //
        }
    }
}
