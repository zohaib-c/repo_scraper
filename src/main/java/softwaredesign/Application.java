package softwaredesign;

import java.util.Arrays;
import java.util.Scanner;

//small size test repo: https://github.com/zohaib-c/first_website.git
//medium size test repo: https://github.com/fauxpilot/fauxpilot.git

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
        Boolean isAuthenticated = Boolean.FALSE;
        AuthRequest req = null;

        try {
            while (authRes.equals("yes") && Boolean.TRUE.equals(!isAuthenticated)) {
                System.out.println("Input access token: ");
                String accessToken = scanner.nextLine().trim();

                req = new AuthRequest(accessToken);
                isAuthenticated = req.authenticate();

                if (!isAuthenticated) {
                    System.out.println("Would you like to try again? yes/no");
                    authRes = takeValidAuthResponse(scanner);
                }
            }
        } catch (Exception e) {
            System.err.println("DEBUG issue with authenticating in Application");
            e.printStackTrace();
        }

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

        Repository repo = Repository.getInstance(url, repoOwner, repoName);
    }

    private static void cloneRepo(Scanner scanner, AuthRequest request){
        Boolean isCloned = Boolean.FALSE;
        String repoName = "";

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


    private static void executeCommand(String[] parsedCommand, GitLog log, String repoName){
        History h = History.getInstance();
        switch (parsedCommand[0]){
            case "ranking":
                Command rankingCommand = new Ranking();
                rankingCommand.setArgs(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                /*if (Boolean.TRUE.equals(rankingCommand.execute(log))){
                    history.push(rankingCommand);
                }*/
                rankingCommand.execute(log);
                break;
            case "restart":
                SystemCommands restart = new SystemCommands();
                restart.restart(repoName);
                break;
            case "stats":
                Command statsCommand = new Stats();
                statsCommand.setArgs(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                statsCommand.execute(log);
                break;
            case "help":
                SystemCommands help = new SystemCommands();
                help.help();
                h.push("help");
                break;
            case "quit":
                SystemCommands quit = new SystemCommands();
                quit.quit(repoName);
                break;
            case "history":
                SystemCommands printHistory = new SystemCommands();
                printHistory.history();
                break;
            case "report":
                SystemCommands report = new SystemCommands();
                report.report(log,repoName);
                h.push("report");
                break;
            default:
                System.err.println("Command not recognised. Enter help for a list of valid commands.");
        }
    }


    private static void mainCommandLoop(Scanner scanner, GitLog gitLog, String repoName){
        while (true) {
            try {
                System.out.println("Enter a command:");
                executeCommand(scanner.nextLine().trim().toLowerCase().split(" "), gitLog, repoName);
            }
            catch (Exception e){
                System.err.println("DEBUG error taking command input");
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args){
        System.out.println("Welcome to the GitHub miner! \n");

        Scanner scanner = new Scanner(System.in);

        AuthRequest request = authenticateUser(scanner);

        cloneRepo(scanner, request);

        Repository repo = Repository.getInstance("url", "repoOwner", "repoName");

        GitLog gitLog = new GitLog();
        Boolean logged = gitLog.runGitLog(repo.repoName); //TODO: something with a failed log

        System.out.println("To see a list of commands, please enter 'help'.");
        mainCommandLoop(scanner, gitLog, repo.repoName);

        scanner.close();
    }
}
