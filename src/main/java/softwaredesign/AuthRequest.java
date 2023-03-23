package softwaredesign;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

public class AuthRequest extends Application{
    private final String accessToken;
    public Boolean userAuthenticated = Boolean.FALSE;

    public AuthRequest(String accessToken){
        this.accessToken = accessToken;
    }

    public void authenticate(){
        GitHubClient client = new GitHubClient();
        client.setOAuth2Token(accessToken);

        UserService userService = new UserService(client);

        try {
            User user = userService.getUser();
            userAuthenticated = Boolean.TRUE;
            System.out.println("Authenticated as: " + user.getLogin());
        } catch (Exception e) {
            System.err.println("Error retrieving user information: Incorrect information");
        }
    }
}
