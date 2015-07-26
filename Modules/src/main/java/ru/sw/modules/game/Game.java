package ru.sw.modules.game;

import ru.sw.modules.game.player.Player;
import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
@ModuleInfo(repositoryName = "GameRepository" , serviceName = "GameService")
public class Game extends AbstractEntity {

    @Transient
    private List<Player> players = new ArrayList<>();

    @Column(name = "count_visitors")
    private Integer countVisitors = 0;

    @Column(name = "total_cash")
    private Integer totalCash = 0;

    @Column(name = "count_weapons")
    private Integer countWeapons = 0;

    @Column(name = "seconds_before_round_over")
    private Integer secondsBeforeRoundOver = 300;


    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Integer getCountVisitors() {
        return countVisitors;
    }

    public void setCountVisitors(Integer countVisitors) {
        this.countVisitors = countVisitors;
    }

    public Integer getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Integer totalCash) {
        this.totalCash = totalCash;
    }

    public Integer getCountWeapons() {
        return countWeapons;
    }

    public void setCountWeapons(Integer countWeapons) {
        this.countWeapons = countWeapons;
    }

    public Integer getSecondsBeforeRoundOver() {
        return secondsBeforeRoundOver;
    }

    public void setSecondsBeforeRoundOver(Integer secondsBeforeRoundOver) {
        this.secondsBeforeRoundOver = secondsBeforeRoundOver;
    }

    @Override
    public UserList getOwners() {
        return null;
    }
}
