package com.redstar.infosystem;

/**
 * User Data
 */
public class User {

    /**
     * Permissions codes:
     * 0 - nobody
     * 1 - user
     * 2 - employee
     * 3 - admin
    */
    private int id;
    private String login;
    private String password;
    private int permission;
    private String permissionName;

    public User()
    {
        id = 0;
        login = "";
        password = "";
        permissionName = "Nobody";
        permission = 0;
    }

    public User(int id, String login, String password, int permission)
    {
        this.id = id;
        this.login = login;
        this.password = password;
        this.permission = permission;
        updatePermissionName();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void updatePermissionName()
    {
        if (permission == 1) permissionName = "User";
        else if (permission == 2) permissionName = "Employee";
        else if (permission == 3) permissionName = "Admin";
        else permissionName = "Nobody";
    }


}

