package softwaredesign;

import java.io.IOException;

public class Repository {
    public static Repository instance;
    private final String repositoryUrl;
    public String repoOwner;
    public String repoName;

    private Repository(String url, String repoOwner, String repoName) {
        this.repositoryUrl = url;
        this.repoOwner = repoOwner;
        this.repoName = repoName;
    }

    public static Repository getInstance(String url, String repoOwner, String repoName) {
        if (instance == null) {
            instance = new Repository(url, repoOwner, repoName);
        }
        return instance;
    }

    public Boolean cloneRepo(AuthRequest request) {
        String cloneCommand = "git clone " + repositoryUrl;

        if (request != null && request.isAuthenticated){
            cloneCommand = "git clone https://" + request.getAccessToken() + "@github.com/" + repoOwner + "/" + repoName + ".git";
        }

        ProcessBuilder processBuilder = new ProcessBuilder(cloneCommand.split(" "));
        try {
            Process process = processBuilder.start();

            System.out.println("Cloning " + repoName + "... \n");

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Repository cloned successfully.");
                return Boolean.TRUE;
            } else {
                System.out.println("\u001B[31mFailed to clone repository.\u001B[0m");
                System.out.println("This could be because you are trying to clone a private repository.");
                return Boolean.FALSE;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
