package softwaredesign;

public class GitCommit {
    private final String author;
    private final long unixDate;
    private final String message;
    private final int totalAdditions;
    private final int totalDeletions;

    public GitCommit(String author, long unixDate, String message, int totalAdditions, int totalDeletions){
        this.author = author;
        this.unixDate = unixDate;
        this.message = message;
        this.totalAdditions = totalAdditions;
        this.totalDeletions = totalDeletions;
    }

    public String getAuthor() {
        return author;
    }

    public long getUnixDate() {
        return unixDate;
    }

    public String getMessage() {return message;}

    public int getTotalAdditions() {return totalAdditions;}

    public int getTotalDeletions(){return totalDeletions;}
}
