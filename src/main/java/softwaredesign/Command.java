package softwaredesign;

public interface Command{
    void setArgs(String[] args);
    void execute(GitLog log);
}