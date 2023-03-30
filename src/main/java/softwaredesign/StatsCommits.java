package softwaredesign;

public class StatsCommits extends Stats implements Command {

    @Override
    public Boolean execute(GitLog log) {


        return Boolean.TRUE;
    }
}
