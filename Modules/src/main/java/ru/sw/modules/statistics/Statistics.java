package ru.sw.modules.statistics;

import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;

@Entity
@Table(name = "statistics")
@ModuleInfo(repositoryName = "StatisticsRepository" , serviceName = "StatisticsService")
public class Statistics extends AbstractEntity{

    private Calendar date;

    private Integer countGames = 0;

    private Integer countPlayers = 0;

    private Integer countWeapons = 0;

    private Integer maxCashRub = 0;

    private Integer maxCashUsd = 0;


    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
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

    public Integer getMaxCashRub() {
        return maxCashRub;
    }

    public void setMaxCashRub(Integer maxCashRub) {
        this.maxCashRub = maxCashRub;
    }

    public Integer getMaxCashUsd() {
        return maxCashUsd;
    }

    public void setMaxCashUsd(Integer maxCashUsd) {
        this.maxCashUsd = maxCashUsd;
    }

    @Override
    public UserList getOwners() {
        return null;
    }
}
