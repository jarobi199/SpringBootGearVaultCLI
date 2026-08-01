package io.gearvault.model;

import io.gearvault.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    private Role role;
    private int overdueGraceDays ;

    public User(String name, String username, String password, Role role, int overdueGraceDays ) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.overdueGraceDays  = overdueGraceDays ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getOverdueGraceDays() {
        return overdueGraceDays;
    }

    public void setOverdueGraceDays(int overdueGraceDays) {
        this.overdueGraceDays = overdueGraceDays;
    }

}
