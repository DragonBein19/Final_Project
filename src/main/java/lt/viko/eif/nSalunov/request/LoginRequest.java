package lt.viko.eif.nSalunov.request;

/**
 * Represents a login request containing user credentials.
 * 
 * This class holds the username and password required for authentication.
 */

public class LoginRequest {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
