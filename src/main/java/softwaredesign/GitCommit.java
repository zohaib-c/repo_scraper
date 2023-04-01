package softwaredesign;

public class GitCommit {
    private final String author;
    private final long unixDate;
    private final String message;
    private final int totalAdditions;
    private final int totalDeletions;
    private final int numFilesChanged;

    public GitCommit(String author, long unixDate, String message, int totalAdditions, int totalDeletions, int numFilesChanged){
        this.author = author;
        this.unixDate = unixDate;
        this.message = message;
        this.totalAdditions = totalAdditions;
        this.totalDeletions = totalDeletions;
        this.numFilesChanged = numFilesChanged;
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

    public int getNumFilesChanged() {return numFilesChanged;}
}
