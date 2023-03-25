package softwaredesign;

import java.io.File;

public class SystemCommands {

    private static void helperDelRepo(File directory){
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    helperDelRepo(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    public void quit(String repoName){
        String dirPath = System.getProperty("user.dir") + "/" + repoName;
        File dir = new File(dirPath);

        if (dir.exists()){
            helperDelRepo(dir);
        }
        else {
            System.err.println("DEBUG: some error with path or repo name, dir not deleted");
        }

        System.exit(0);
    }

    public void restart(){
        //
    }

    public void help(){
        //
    }

    public void history(){
        //
    }

    public void printReport(){
        //
    }

    public void authenticate(String accessToken){
        AuthRequest authRes = new AuthRequest(accessToken);
        authRes.authenticate();
    }

}
