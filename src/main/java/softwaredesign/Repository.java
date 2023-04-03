package softwaredesign;

import java.io.IOException;

/* We decided to make this class a Singleton class, because of the following two reasons:
*       1. There is going to be only one cloned repository at a time. Consequently, we need to ensure that there is only
*          one instance of the Repository Class. We do not want multiple objects representing the same repository that
*          was cloned to the user's machine.
*       2. The Repository Object needs to be accessed in multiple different points in the program, such as the execute()
*          method in the Stats class, and the executeCommand() method in the Application class.
*/

public class Repository {
    public static Repository instance;
    private final String repositoryUrl;
    private final String repoOwner;
    private final String repoName;

    private Repository(String url, String repoOwner, String repoName) {
        this.repositoryUrl = url;
        this.repoOwner = repoOwner;
        this.repoName = repoName;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getRepoOwner() {
        return repoOwner;
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
