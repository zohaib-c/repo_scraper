package softwaredesign;

import java.util.*;

public class RankingCommitChurn extends RankingCommit implements Command{
    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length !=0){
            System.err.println("DEBUG: There should be no args here");//TODO: Delete this
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }
}
