package softwaredesign;

import java.util.Arrays;
import java.util.Scanner;

//small size test repo: https://github.com/zohaib-c/first_website.git
//medium size test repo: https://github.com/fauxpilot/fauxpilot.git
//token test: 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

public class Application {

    private static String takeValidAuthResponse(Scanner scanner){
        String authRes = scanner.nextLine().trim().toLowerCase();
        while (!authRes.equals("yes") && !authRes.equals("no")){
            System.out.println("\u001B[31m'" + authRes + "' is not a valid response.\u001B[0m");
            System.out.println("Please enter 'yes' or 'no'");
            authRes = scanner.nextLine().trim().toLowerCase();
        }

        return authRes;
    }

    private static AuthRequest authenticateUser(Scanner scanner){
        System.out.println("If you would like to clone a private repository, you will need to provide us with a " +
                "Private Access Token.\n" +
                "Type 'yes' if you would to authenticate, otherwise type 'no'");
        String authRes = takeValidAuthResponse(scanner);
        AuthRequest req = null;

        try{
            if (authRes.equals("yes")) {
                do {
                    System.out.println("Input access token: ");
                    String accessToken = scanner.nextLine().trim();

                    req = new AuthRequest(accessToken);
                    req.authenticate();

                    if (Boolean.FALSE.equals(req.isAuthenticated)) {
                        System.out.println("Would you like to try again? yes/no");
                        authRes = takeValidAuthResponse(scanner);
                    }

                } while (authRes.equals("yes") && Boolean.FALSE.equals(req.isAuthenticated));
            }
        }
        catch (Exception e){
            System.err.println("DEBUG issue with authenticating in Application");
            e.printStackTrace();
        }

        assert req != null;
        return req;
    }

    private static void setRepo(Scanner scanner){
        boolean isSet = false;
        String url = "";
        String repoName = "";
        String repoOwner = "";
        while (Boolean.FALSE.equals(isSet)){
            System.out.println("Please enter a valid GitHub repository url you would like to clone:");
            url = scanner.nextLine().trim().toLowerCase();
            try {
                String[] urlParts = url.split("/");
                repoOwner = urlParts[3];
                repoName = urlParts[4].replace(".git", "");
                isSet = true;
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("\u001B[31mInvalid URL. '" + url + "' is not a valid HTTPS git url. "
                        + "Try again.\u001B[0m\n");
            }
        }

        Repository.getInstance(url, repoOwner, repoName);
    }

    private static void cloneRepo(Scanner scanner, AuthRequest request){
        Boolean isCloned = Boolean.FALSE;

        try {
            while (Boolean.FALSE.equals(isCloned)) {
                setRepo(scanner);

                Repository newRepo = Repository.getInstance("url", "repoOwner", "repoName");

                isCloned = newRepo.cloneRepo(request);

                if (Boolean.FALSE.equals(isCloned)){
                    authenticateUser(scanner);
                }
            }
        }
        catch (Exception e){
            System.err.println("DEBUG issue with with cloning repo in Application");
            e.printStackTrace();
        }
    }


    private static Boolean executeCommand(String[] parsedCommand, GitLog log, String repoName){
        try {
            switch (parsedCommand[0]) {
                case "ranking":
                    Command rankingCommand = new Ranking();
                    rankingCommand.setArgs(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                    return rankingCommand.execute(log);

                case "stats":
                    Command statsCommand = new Stats();
                    statsCommand.setArgs(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                    return statsCommand.execute(log);

                case "restart":
                    return new SystemCommands().restart(repoName);

                case "help":
                    return new SystemCommands().help();

                case "quit":
                    return new SystemCommands().quit(repoName);

                case "history":
                    return new SystemCommands().history();

                case "report":
                    return new SystemCommands().report(log, repoName);

                default:
                    System.out.println("\u001B[31mCommand not recognised. Enter 'help' for a list of valid commands.\u001B[0m");
                    return Boolean.TRUE;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("\u001B[31mThere was an issue with parsing the last command.\u001B[0m");
            return Boolean.FALSE;
        }

    }


    private static void mainCommandLoop(Scanner scanner, GitLog gitLog, String repoName){
        while (true) {
            try {
                System.out.println("Enter a command:");
                if (Boolean.FALSE.equals(executeCommand(scanner.nextLine().trim().toLowerCase().split(" "), gitLog, repoName))){
                    System.out.println("\u001B[31mCommand could not be executed. Try again or enter 'restart' to start over.\u001B[0m");
                }
            }
            catch (Exception e){
                System.err.println("DEBUG error taking command input");
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args){
        System.out.println("\nWelcome to the GitHub miner!\n");

        Scanner scanner = new Scanner(System.in);


        AuthRequest request = authenticateUser(scanner);

        cloneRepo(scanner, request);

        Repository repo = Repository.getInstance("url", "repoOwner", "repoName");

        GitLog gitLog = new GitLog();
        if (Boolean.FALSE.equals(gitLog.runGitLog(repo.repoName))){
            System.out.println("\u001B[31mThere was an error while getting commit information. Force restarting application now.\u001B[0m");
            new SystemCommands().restart(repo.repoName);
        }

        System.out.println("To see a list of commands, please enter 'help'.");
        mainCommandLoop(scanner, gitLog, repo.repoName);

        scanner.close();
    }
}
