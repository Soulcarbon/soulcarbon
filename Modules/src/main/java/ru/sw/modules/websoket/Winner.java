package ru.sw.modules.websoket;

import ru.sw.modules.game.player.Player;
import ru.sw.modules.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Winner {

    private Player player;
    private List<Weapon> list = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Weapon> getList() {
        return list;
    }

    public void setList(List<Weapon> list) {
        this.list = list;
    }
}
