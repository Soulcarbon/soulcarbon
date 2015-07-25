package ru.sw.platform.modules.role;

import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;
import ru.sw.platform.modules.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "sys_role")
@ModuleInfo(repositoryName = "RoleRepository", serviceName = "RoleService")
public class Role extends AbstractEntity {

    @Column(name = "role_name")
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /*
    * Изменять роли могут только администраторы
    * **/
    @Override
    public UserList getOwners() {
        UserList userList = new UserList();
        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");
        userList.setRoles(Arrays.asList(role));
        return userList;
    }
}
