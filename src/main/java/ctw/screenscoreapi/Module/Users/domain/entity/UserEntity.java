package ctw.screenscoreapi.Module.Users.domain.entity;

import ctw.screenscoreapi.Module.Users.domain.enums.Role;

public class UserEntity {
    private Long id;
    private String password;
    private String name;
    private String email;
    private Role role;

    public UserEntity(Long id, String password, String name, String email ,Role role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
