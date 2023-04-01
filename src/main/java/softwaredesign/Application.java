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
            while (authRes.equals("yes") && !isAuthenticated) {
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

    private static Repository setRepo(Scanner scanner){
        Boolean isSet = Boolean.FALSE;
        Repository newRepo = new Repository();
        while (!isSet){
            System.out.println("Please enter a valid GitHub repository url you would like to clone:");
            String url = scanner.nextLine().trim().toLowerCase();
            isSet = newRepo.setRepositoryUrl(url);
        }
        return newRepo;
    }

    private static String cloneRepo(Scanner scanner, AuthRequest request){
        Boolean isCloned = Boolean.FALSE;
        String repoName = "";

        try {
            while (!isCloned) {
                Repository newRepo = setRepo(scanner);

                isCloned = newRepo.cloneRepo(request);

                if (!isCloned){
                    authenticateUser(scanner);
                }

                repoName = newRepo.repoName;
            }
        }
        catch (Exception e){
            System.err.println("DEBUG issue with with cloning repo in Application");
            e.printStackTrace();
        }

        return repoName;
    }


    private static void executeCommand(String[] parsedCommand, GitLog log, String repoName, History history){ //What is repoName used for? =================
        switch (parsedCommand[0]){
            case "ranking":
                Command rankingCommand = new Ranking();
                rankingCommand.setArgs(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                if (rankingCommand.execute(log)){
                    history.push(rankingCommand);
                }
                break;
            case "restart":
                SystemCommands restart = new SystemCommands();
                restart.restart();
                break;
            case "stats":
                Command statsCommand = new Stats();
                statsCommand.setArgs(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
                if (statsCommand.execute(log)){
                    history.push(statsCommand);
                }
                break;
            case "help":
                SystemCommands help = new SystemCommands();
                help.help();
                break;
            case "quit":
                SystemCommands quit = new SystemCommands();
                quit.quit(repoName);
                break;
            case "history":
                SystemCommands printHistory = new SystemCommands();
                printHistory.history(history);
                break;
            case "report":
                SystemCommands report = new SystemCommands();
                report.report(log,repoName);
                break;
            default:
                System.err.println("Command not recognised. Enter help for a list of valid commands.");
        }
    }


    private static void mainCommandLoop(Scanner scanner, GitLog gitLog, String repoName, History history){
        while (true) {
            try {
                System.out.println("Enter a command:");
                executeCommand(scanner.nextLine().trim().toLowerCase().split(" "), gitLog, repoName, history);
            }
            catch (Exception e){
                System.err.println("DEBUG error taking command input");
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args){
        History history = new History();

        System.out.println("Welcome to the GitHub miner! \n");

        Scanner scanner = new Scanner(System.in);

        AuthRequest request = authenticateUser(scanner);

        String repoName = cloneRepo(scanner, request);

        GitLog gitLog = new GitLog();
        Boolean logged = gitLog.runGitLog(repoName); //TODO: something with a failed log

        System.out.println("To see a list of commands, please enter 'help'.");
        mainCommandLoop(scanner, gitLog, repoName, history);

        scanner.close();
    }
}
