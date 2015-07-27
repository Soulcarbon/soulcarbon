package ru.sw.modules.game;

import ru.sw.modules.game.player.Player;
import ru.sw.modules.steam.utils.Price;
import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
@ModuleInfo(repositoryName = "GameRepository" , serviceName = "GameService")
public class Game extends AbstractEntity {

    public enum GameState {
        Wait,
        Active,
        Finished
    }

    @Enumerated(EnumType.STRING)
    private GameState state = GameState.Wait;

    @Transient
    private List<Player> players = new ArrayList<>();

    @Column(name = "count_visitors")
    private Integer countVisitors = 0;

    @Embedded
    private Price total = new Price();

    @Column(name = "count_weapons")
    private Integer countWeapons = 0;

    @Column(name = "seconds_before_round_over")
    private Integer secondsBeforeRoundOver = 300;

    private String lastWinnerNickName;

    private String lastWinnerImageUrl;

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

    public Price getTotal() {
        return total;
    }

    public void setTotal(Price total) {
        this.total = total;
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

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public String getLastWinnerNickName() {
        return lastWinnerNickName;
    }

    public void setLastWinnerNickName(String lastWinnerNickName) {
        this.lastWinnerNickName = lastWinnerNickName;
    }

    public String getLastWinnerImageUrl() {
        return lastWinnerImageUrl;
    }

    public void setLastWinnerImageUrl(String lastWinnerImageUrl) {
        this.lastWinnerImageUrl = lastWinnerImageUrl;
    }

    @Override
    public UserList getOwners() {
        return null;
    }
}
