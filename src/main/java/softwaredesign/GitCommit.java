package softwaredesign;

import java.util.List;

public class GitCommit {
    private final String sha;
    private final String author;
    private final String email;
    private final long unixDate;
    private final String message;
    private GitStats stats;

    public GitCommit(String sha, String author, String email, long unixDate, String message){
        this.sha = sha;
        this.author = author;
        this.email = email;
        this.unixDate = unixDate;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public GitStats getStats() {
        return stats;
    }
}
