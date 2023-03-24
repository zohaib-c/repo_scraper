package softwaredesign;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

//https://github.com/zohaib-c/first_website.git
//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
//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

public class Application {

    private static String[] setRepo(String url){
        Repository newRepo = new Repository(url);
        newRepo.cloneRepo();

        return new String[]{newRepo.repoName, newRepo.repoOwner};
    }

    private static String[] parseCommand(String inputCommand){
        return inputCommand.split(" ");
    }

    private static void createCommand(String[] parsedCommand){
        switch (parsedCommand[0]){
            case "ranking":
                Command rankingCommand = new Ranking();
                rankingCommand.setArgs(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                rankingCommand.execute();
            case "stats":
                //
            default: //system commands
                //
        }
    }

    private static Boolean compareAuthRes(String res){
        if (Objects.equals(res, "yes")){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static Boolean createAuthRequest(String accessToken){
        AuthRequest req = new AuthRequest(accessToken);
        req.authenticate();
        return req.userAuthenticated;
    }

    public static void main(String[] args){
        Boolean userAuthenticated = Boolean.FALSE;
        String accessToken;

        System.out.println("Welcome to the GitHub miner! \n");

        System.out.println("If you would like to clone a private repository, you will need to provide us with a Private Access Token.\n" +
                "Type 'yes' if you would to authenticate, otherwise type 'no'");

        Scanner scanner = new Scanner(System.in);

        String authRes = scanner.nextLine().trim().toLowerCase();

        while (compareAuthRes(authRes) & !userAuthenticated){
            System.out.println("Input access token: ");
            accessToken = scanner.nextLine().trim();

            userAuthenticated = createAuthRequest(accessToken);

            if (!userAuthenticated){
                System.out.println("Would you like to try again? Yes/No");
                authRes = scanner.nextLine().trim().toLowerCase();
            }
        }

        System.out.println("Please enter a valid GitHub repository url you would like to clone:");
        String[] repoDetails = setRepo(scanner.nextLine().trim().toLowerCase());

        GitLog gitLog = new GitLog();
        gitLog.runGitLog(repoDetails[0]);

        System.out.println("Enter a command:");
        createCommand(parseCommand(scanner.nextLine().trim().toLowerCase()));

        scanner.close();
    }
}
