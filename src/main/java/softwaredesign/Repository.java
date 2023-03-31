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

        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Invalid URL. Try again");
        }

    }

    public Boolean cloneRepo(AuthRequest request) {
        String cloneCommand = "git clone " + repositoryUrl;

        if (request != null && request.isAuthenticated){
            cloneCommand = "git clone https://" + request.getAccessToken() + "@github.com/" + repoOwner + "/" + repoName + ".git";
        }

        ProcessBuilder processBuilder = new ProcessBuilder(cloneCommand.split(" "));
        try {
            Process process = processBuilder.start();

            System.out.println("Cloning " + repoName + "... ");

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Repository cloned successfully.\n");
                return Boolean.TRUE;
            } else {
                System.err.println("Failed to clone repository.\n");
                return Boolean.FALSE;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
