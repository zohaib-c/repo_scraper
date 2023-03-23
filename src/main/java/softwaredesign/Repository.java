package softwaredesign;

import java.io.IOException;

public class Repository extends Application{
    private final String repositoryUrl;
    public String repoOwner;
    public String repoName;

    public Repository(String url){
        this.repositoryUrl = url;
        String[] urlParts = url.split("/");
        this.repoOwner = urlParts[3];
        this.repoName = urlParts[4].replace(".git", "");

        System.out.println(url);
    }

    public void cloneRepo() {

        ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repositoryUrl);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Repository cloned successfully.\n");
            } else {
                System.err.println("Failed to clone repository.\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
