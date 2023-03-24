package softwaredesign;

import java.util.List;

public class GitCommit {
    private String sha;
    private String author;
    private String email;
    private long unixDate;
    private String messageSubject;
    private String messageBody;
    private GitStats stats;
    private List<String> changedFiles;
    private List<String> branches;
    private List<String> tags;
    private String parentCommitSha;
    private String commitType;

//    public GitCommit(String sha, String committer, String email, Date unixDate, String message,
//                     GitStats stats, List<String> changedFiles, List<String> branches,
//                     List<String> tags, String parentCommitSha, String commitType) {
//        this.sha = sha;
//        this.committer = committer;
//        this.email = email;
//        this.unixDate = unixDate;
//        this.message = message;
//        this.stats = stats;
//        this.changedFiles = changedFiles;
//        this.branches = branches;
//        this.tags = tags;
//        this.parentCommitSha = parentCommitSha;
//        this.commitType = commitType;
//    }

    public GitCommit(String sha, String author, String email, long unixDate, String messageSubject, String messageBody){
        this.sha = sha;
        this.author = author;
        this.email = email;
        this.unixDate = unixDate;
        this.messageSubject = messageSubject;
        this.messageBody = messageBody;
    }

    public String getAuthor() {
        return author;
    }

    public String getEmail() {
        return email;
    }

    public long getUnixDate() {
        return unixDate;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public GitStats getStats() {
        return stats;
    }

    public List<String> getChangedFiles() {
        return changedFiles;
    }

    public List<String> getBranches() {
        return branches;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getParentCommitSha() {
        return parentCommitSha;
    }

    public String getCommitType() {
        return commitType;
    }
}
