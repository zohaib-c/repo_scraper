package softwaredesign;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

import java.io.IOException;

public class AuthRequest {
    private final String accessToken;
    public Boolean isAuthenticated = Boolean.FALSE;

    public AuthRequest(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void authenticate(){
        GitHubClient client = new GitHubClient();
        client.setOAuth2Token(accessToken);

        UserService userService = new UserService(client);

        try {
            /*
            If the userService library is able to get the user, the access token is valid. Otherwise, it throws a
            RequestException meaning the token was invalid.
            */

            User user = userService.getUser();
            System.out.println("Authenticated as: " + user.getLogin());
            isAuthenticated = Boolean.TRUE;

        } catch (IOException e) {
            System.out.println("\u001B[31mError retrieving user information: Incorrect information\u001B[0m");
        }
    }
}
