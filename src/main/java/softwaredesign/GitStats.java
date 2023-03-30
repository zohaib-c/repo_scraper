package softwaredesign;

class GitStats {
    private int additions;
    private int deletions;
    private int totalChanges;

    public GitStats(int additions, int deletions, int totalChanges, int numFilesChanged) {
        this.additions = additions;
        this.deletions = deletions;
        this.totalChanges = totalChanges;
    }

    public int getAdditions() {
        return additions;
    }

    public int getDeletions() {
        return deletions;
    }

    public int getTotalChanges() {
        return totalChanges;
    }
}
