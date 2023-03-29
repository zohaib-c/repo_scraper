package softwaredesign;

public interface Command{
    void setArgs(String[] args);
    Boolean execute(GitLog log);
}
