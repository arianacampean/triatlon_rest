package dto;

import java.io.Serializable;

public class ArbitruDto implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArbitruDto(String username, String password) {

        this.username = username;
        this.password = password;
    }
}
