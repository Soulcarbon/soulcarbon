package ru.sw.platform.modules.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;
import ru.sw.platform.modules.role.Role;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Entity
@ModuleInfo(repositoryName = "UserRepository" , serviceName = "UserService")
@Table(name = "sys_user")
public class User extends AbstractEntity {

    private String login;

    @Column(name = "full_name")
    private String fullName;


    @Column(name = "hash_pwd" , length = 500)
    @JsonIgnore
    private String hashPwd;

    @Transient
    private String pwdTransient;

    public String getPwdTransient() {
        return pwdTransient;
    }

    public void setPwdTransient(String pwdTransient) {
        this.pwdTransient = pwdTransient;
    }

    private boolean enabled=true;

    @ManyToOne
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getHashPwd() {
        return hashPwd;
    }

    public void setHashPwd(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.hashPwd = bCryptPasswordEncoder.encode(password);
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    @JsonIgnore
    public UserList getOwners() {
        UserList userList = new UserList();
        userList.setUserList(Arrays.asList(this));
        return userList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
