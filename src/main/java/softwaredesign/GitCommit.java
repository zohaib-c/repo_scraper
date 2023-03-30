package softwaredesign;

import java.util.List;

public class GitCommit {
    private final String sha;
    private final String author;
    private final String email;
    private final long unixDate;
    private final String message;
    private final int totalAdditions;
    private final int totalDeletions;
    private final int numFilesChanged;

    public GitCommit(String sha, String author, String email, long unixDate, String message, int totalAdditions, int totalDeletions, int numFilesChanged){
        this.sha = sha;
        this.author = author;
        this.email = email;
        this.unixDate = unixDate;
        this.message = message;
        this.totalAdditions = totalAdditions;
        this.totalDeletions = totalDeletions;
        this.numFilesChanged = numFilesChanged;
    }

    public String getSha(){return sha;}

    public String getAuthor() {
        return author;
    }

    public String getEmail() {
        return email;
    }

    public long getUnixDate() {
        return unixDate;
    }

    public String getMessage() {return message;}

    public int getTotalAdditions() {return totalAdditions;}

    public int getTotalDeletions(){return totalDeletions;}

    public int getNumFilesChanged() {return numFilesChanged;}
}
