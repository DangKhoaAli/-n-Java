package BLL;

import java.util.HashMap; 
import java.util.Map;     

public class LoginPanel_BLL {
    private Map<String, String> users;

    public LoginPanel_BLL() {
        users = new HashMap<>();
        // Add predefined users
        users.put("admin", "password");
        users.put("staff", "1234");
    }

    /**
     * Authenticate user credentials.
     * @param username The username.
     * @param password The password.
     * @return The role if authentication is successful, otherwise null.
     */
    public String authenticate(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            return username; // Role is the same as the username for simplicity
        }
        return null;
    }
}