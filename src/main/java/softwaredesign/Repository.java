package softwaredesign;

import java.io.IOException;
import java.util.TreeMap;

public class Repository extends Application{
    private final String repositoryUrl;
    public String repoOwner;
    public String repoName;

    public Repository(String url){
        this.repositoryUrl = url;
        try {
            String[] urlParts = url.split("/");
            this.repoOwner = urlParts[3];
            this.repoName = urlParts[4].replace(".git", "");

            System.out.println(url);
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    public Boolean cloneRepo() {
        Boolean validRepo = Boolean.FALSE;

        ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repositoryUrl);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Repository cloned successfully.\n");
                validRepo = Boolean.TRUE;
            } else {
                System.err.println("Failed to clone repository.\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    return validRepo;
    }
}
