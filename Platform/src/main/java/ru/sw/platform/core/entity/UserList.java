package ru.sw.platform.core.entity;


import ru.sw.platform.modules.role.Role;
import ru.sw.platform.modules.user.User;

import java.util.List;

public class UserList {

    private List<User> userList;
    private List<Role> roles;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
