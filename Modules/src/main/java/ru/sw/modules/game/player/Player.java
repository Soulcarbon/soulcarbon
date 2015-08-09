package ru.sw.modules.game.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sw.modules.steam.utils.Price;
import ru.sw.modules.weapon.Weapon;

import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private String nickName;

    private String imageUrl;

    private List<Weapon> weaponList = new ArrayList<>();

    private Price total;

    private double probability;

    private String steamId = "";

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonIgnore
    private boolean isWinner = false;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    public void setWeaponList(List<Weapon> weaponList) {
        this.weaponList = weaponList;
    }

    public Price getTotal() {
        return total;
    }

    public void setTotal(Price total) {
        this.total = total;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }
}
