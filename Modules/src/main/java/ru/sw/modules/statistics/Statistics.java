package ru.sw.modules.statistics;

import ru.sw.modules.game.player.Player;
import ru.sw.modules.steam.utils.Price;
import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ModuleInfo(serviceName = "StatisticsService")
public class Statistics extends AbstractEntity{

    private Integer countGames = 0;

    private Integer countPlayers = 0;

    private Integer countWeapons = 0;

    private Price maxCash;

    private List<Player> previousWinner = new ArrayList<>();

    private List<Player> topPlayer = new ArrayList<>();

    public List<Player> getPreviousWinner() {
        return previousWinner;
    }

    public void setPreviousWinner(List<Player> previousWinner) {
        this.previousWinner = previousWinner;
    }

    public List<Player> getTopPlayer() {
        return topPlayer;
    }

    public void setTopPlayer(List<Player> topPlayer) {
        this.topPlayer = topPlayer;
    }

    public Price getMaxCash() {
        return maxCash;
    }

    public void setMaxCash(Price maxCash) {
        this.maxCash = maxCash;
    }

    public Integer getCountGames() {
        return countGames;
    }

    public void setCountGames(Integer countGames) {
        this.countGames = countGames;
    }

    public Integer getCountPlayers() {
        return countPlayers;
    }

    public void setCountPlayers(Integer countPlayers) {
        this.countPlayers = countPlayers;
    }

    public Integer getCountWeapons() {
        return countWeapons;
    }

    public void setCountWeapons(Integer countWeapons) {
        this.countWeapons = countWeapons;
    }

    @Override
    public UserList getOwners() {
        return null;
    }
}
