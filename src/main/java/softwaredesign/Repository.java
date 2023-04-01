package softwaredesign;

import java.io.IOException;

public class Repository extends Application{
    private String repositoryUrl;
    public String repoOwner;
    public String repoName;


    public Boolean setRepositoryUrl(String url) {
        this.repositoryUrl = url;
        try {
            String[] urlParts = url.split("/");
            setRepoOwner(urlParts[3]);
            setRepoName(urlParts[4].replace(".git", ""));
            return Boolean.TRUE;

        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("\u001B[31mInvalid URL. '" + url + "' is not a valid HTTPS git url. "
                    + "Try again.\u001B[0m\n");
            return Boolean.FALSE;
        }
    }

    private void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    private void setRepoName(String repoName) {
        this.repoName = repoName;
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
