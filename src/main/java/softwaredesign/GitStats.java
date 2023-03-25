package softwaredesign;

class GitStats {
    private int additions;
    private int deletions;
    private int totalChanges;
    private int numFilesChanged;

    public GitStats(int additions, int deletions, int totalChanges, int numFilesChanged) {
        this.additions = additions;
        this.deletions = deletions;
        this.totalChanges = totalChanges;
        this.numFilesChanged = numFilesChanged;
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

    public int getNumFilesChanged() {
        return numFilesChanged;
    }
}
