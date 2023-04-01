package softwaredesign;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

public class AuthRequest {
    private final String accessToken;
    public Boolean isAuthenticated = Boolean.FALSE;

    public AuthRequest(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Boolean authenticate(){
        GitHubClient client = new GitHubClient();
        client.setOAuth2Token(accessToken);

        UserService userService = new UserService(client);

        try {
            User user = userService.getUser();
            System.out.println("Authenticated as: " + user.getLogin());
            isAuthenticated = Boolean.TRUE;
            return Boolean.TRUE;
        } catch (Exception e) {
            System.err.println("Error retrieving user information: Incorrect information");
            return Boolean.FALSE;
        }
    }
}
