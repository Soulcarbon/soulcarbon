package ru.sw.modules.userInfo;

import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;
import ru.sw.platform.modules.user.User;

import javax.persistence.*;

@Entity
@ModuleInfo(repositoryName = "UserInfoRepository" , serviceName = "UserInfoService")
@Table(name = "user_info")
public class UserInfo extends AbstractEntity {

    @OneToOne
    private User user;

    private String info;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public UserList getOwners() {
        UserList userList = new UserList();
        userList.setUserList(null);
        return userList;
    }
}
