package softwaredesign;

/* The stats command outputs an overview of information about the repository, its contributors and their commits.
 * As a result, there are similar calculations in Stats and Ranking, because the same metrics (e.g. commit with the
 * highest churn) are used. However, creating an additional abstract class that includes shared methods would add a lot
 * of complexity, because one method would only be useful to 2 out of 12 subclasses. For this reason, similar code in
 * the implementations of ranking and stats can be found. */

import java.util.List;

public class Stats implements Command {
    private String[] args;

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        History h = History.getInstance();
        List<GitCommit> repoCommits = log.getCommits();
        Repository repo = Repository.getInstance("url", "repoOwner", "repoName");

        System.out.println("Summary of Stats for the repository '" + repo.repoName + "' owned by " + repo.repoOwner);

        if (repoCommits.size() < 1) {
            System.out.println("There are no commits (and consequently no contributors) in this repository");
            return Boolean.TRUE;
        }

        System.out.println();
        if (args.length != 0){
            switch (args[0]){
                case "commit":
                    Command statsCom = new StatsCommit();
                    statsCom.execute(log);
                    h.push("stats commit");
                    break;
                case "contributor":
                    Command statsCont = new StatsContributor();
                    statsCont.execute(log);
                    h.push("stats contributor");
                    break;
                default:
                    System.out.println("Unrecognized argument for stats. Type 'help' for a list of commands.");
                    return Boolean.FALSE;
            }
        }
        else {
            System.out.println("COMMITS");
            Command statsCom = new StatsCommit();
            statsCom.execute(log);
            System.out.println("\nCONTRIBUTORS");
            Command statsCont = new StatsContributor();
            statsCont.execute(log);
            h.push("stats");
        }
        return Boolean.TRUE;
    }
}
