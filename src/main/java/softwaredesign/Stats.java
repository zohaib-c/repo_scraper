package softwaredesign;

import java.util.Arrays;

/*
* Repo name: x
* repo owner: x
* total number of commits: x
* commit with the highest churn: x
* first commit: x
* most recent commit: x
* commit with the most additions: x
* list of all contributors: x
* total number of contributors: x
* contributors who worked most on weekends: x
* contributor with the most/least commits: x
* contributor who has worked on it for the longest/shortest: x*/

public class Stats extends Application implements Command {
    private String[] args;
    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        System.out.println();
        if (args.length != 0){
            System.out.println("args != null\n");
            System.out.println(Arrays.toString(Arrays.stream(args).toArray()));
            switch (args[0]){
                case "commit":
                    Command statsCom = new StatsCommits();
                    statsCom.execute(log);
                    break;
                case "contributor":
                    Command statsCont = new StatsContributors();
                    statsCont.execute(log);
                    break;
                default:
                    System.out.println("Unrecognized argument for stats");
                    return Boolean.FALSE;
            }
        }
        else {
            System.out.println("COMMITS");
            Command statsCom = new StatsCommits();
            statsCom.execute(log);
            System.out.println("\nCONTRIBUTORS");
            Command statsCont = new StatsContributors();
            statsCont.execute(log);
        }
        return Boolean.TRUE; //???
    }
}
