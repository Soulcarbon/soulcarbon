package ru.sw.modules.settings;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;
import ru.sw.platform.modules.role.Role;
import ru.sw.platform.modules.role.RoleRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;

@ModuleInfo(repositoryName = "SettingsRepository" , serviceName = "SettingsService")
@Entity
@Table(name = "settings")
public class Settings extends AbstractEntity {

    @Column(name = "site_name")
    private String siteName;

    private Integer bonus;

    private Integer rate;

    @Column(name = "vk_group_url")
    private String VKGroupUrl;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getVKGroupUrl() {
        return VKGroupUrl;
    }

    public void setVKGroupUrl(String VKGroupUrl) {
        this.VKGroupUrl = VKGroupUrl;
    }

    @Override
    public UserList getOwners() {
        UserList userList = new UserList();

        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");
        userList.setRoles(Arrays.asList(role));

        return userList;
    }
}
