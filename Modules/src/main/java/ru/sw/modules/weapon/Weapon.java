package ru.sw.modules.weapon;

import ru.sw.modules.steam.utils.Price;
import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;

import javax.persistence.*;

@Entity
@Table(name = "weapon")
@ModuleInfo(repositoryName = "WeaponRepository" , serviceName = "WeaponService")
public class Weapon extends AbstractEntity {

    @Column(length = 500 , name="weapon_name")
    private String weaponName;


    @Column(length = 2000 , name = "image_url")
    private String imageUrl;

    @Embedded
    private Price price;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Column(unique = true, name="class_id")
    private String classId;

    private String assetId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    @Transient
    private String userSteamId;

    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserSteamId() {
        return userSteamId;
    }

    public void setUserSteamId(String userSteamId) {
        this.userSteamId = userSteamId;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public UserList getOwners() {
        return null;
    }
}
