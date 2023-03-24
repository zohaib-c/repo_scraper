package softwaredesign;

class GitStats {
    private int additions;
    private int deletions;
    private int totalChanges;
    private int numFilesChanged;
    private float linesAddedPerFile;
    private float linesDeletedPerFile;
    private float netLinesChangedPerFile;

    public GitStats(int additions, int deletions, int totalChanges, int numFilesChanged,
                    float linesAddedPerFile, float linesDeletedPerFile, float netLinesChangedPerFile) {
        this.additions = additions;
        this.deletions = deletions;
        this.totalChanges = totalChanges;
        this.numFilesChanged = numFilesChanged;
        this.linesAddedPerFile = linesAddedPerFile;
        this.linesDeletedPerFile = linesDeletedPerFile;
        this.netLinesChangedPerFile = netLinesChangedPerFile;
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

    public float getLinesAddedPerFile() {
        return linesAddedPerFile;
    }

    public float getLinesDeletedPerFile() {
        return linesDeletedPerFile;
    }

    public float getNetLinesChangedPerFile() {
        return netLinesChangedPerFile;
    }
}
