package ru.sw.modules.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sw.modules.game.player.Player;
import ru.sw.modules.steam.utils.SteamUtil;
import ru.sw.modules.steam.utils.Price;
import ru.sw.modules.weapon.Weapon;
import ru.sw.modules.weapon.WeaponRepository;
import ru.sw.modules.websoket.GameWebSocketHandler;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.services.AbstractService;

import java.util.*;

@Service("GameService")
public class GameService extends AbstractService {

    @Autowired
    private WeaponRepository weaponRepository;


    @Action(name = "addPlayer")
    public String addPlayer(HashMap<String, Object> map) {

        if(map.get("key").equals("gzdpaSe_503_!_")) {
            String steamId = (String) map.get("steamid_other");
            List<LinkedHashMap<String,Object>> weaponJsonList = (List<LinkedHashMap<String,Object>>) map.get("weaponJsonList");
            Player player = SteamUtil.getPlayerBySteamId(steamId);
            List<Weapon> weapon_list = new ArrayList<>();
            Price totalCost = new Price();
            for (int i = 0; i < weaponJsonList.size(); i++) {
                LinkedHashMap<String,Object> weaponJson = weaponJsonList.get(0);
                String classId = (String) weaponJson.get("classid");
                Integer amount = Integer.parseInt((String) weaponJson.get("amount"));
                Weapon weapon = weaponRepository.getSingleEntityByFieldAndValue(Weapon.class, "classId", classId);
                if(weapon == null) {
                    String appid = (String) weaponJson.get("appid");
                    weapon = SteamUtil.getWeaponByClassId(classId, appid);
                    weaponRepository.create(weapon);
                }

                if (weapon != null) {
                    for (int j = 0; j < amount; j++) {
                        weapon_list.add(weapon);
                        totalCost.addPrice(weapon.getPrice());
                    }
                }
            }

            if(totalCost.getUsd() < 1e-7){
                return "cost failed";
            }

            player.setTotal(totalCost);
            player.setSteamId(steamId);


            synchronized (GameWebSocketHandler.monitor) {
                GameWebSocketHandler.activeGame.getTotal().addPrice(player.getTotal());
                Integer tempCountWeapon = GameWebSocketHandler.activeGame.getCountWeapons();
                GameWebSocketHandler.activeGame.setCountWeapons(tempCountWeapon + weapon_list.size());
                if (GameWebSocketHandler.activeGame.getPlayers() != null && !GameWebSocketHandler.activeGame.getPlayers().isEmpty()) {

                    //Проверяем не ставил ли такой игрок
                    for(Player element : GameWebSocketHandler.activeGame.getPlayers()) {
                        if (element.getSteamId().equals(player.getSteamId())) {
                            element.getWeaponList().addAll(weapon_list);
                            element.getTotal().addPrice(totalCost);
                            for (Player player1 : GameWebSocketHandler.activeGame.getPlayers()) {
                                player1.setProbability(player1.getTotal().getUsd() / GameWebSocketHandler.activeGame.getTotal().getUsd() * 100);
                            }
                            GameWebSocketHandler.updateSession();
                            return "ok";
                        }
                    }
                    //Игрок ставит первый раз
                    player.setWeaponList(weapon_list);
                    GameWebSocketHandler.activeGame.getPlayers().add(player);
                    for(Player element : GameWebSocketHandler.activeGame.getPlayers()) {
                        element.setProbability(element.getTotal().getUsd() / GameWebSocketHandler.activeGame.getTotal().getUsd() * 100);
                    }
                    GameWebSocketHandler.updateSession();
                    if(GameWebSocketHandler.activeGame.getPlayers().size() == 3) {
                        GameWebSocketHandler.startGame();
                    }

                    return "ok";
                }

                player.setWeaponList(weapon_list);
                player.setProbability(100);
                GameWebSocketHandler.activeGame.setPlayers(new LinkedList<Player>(Arrays.asList(player)));
                GameWebSocketHandler.updateSession();
                return "ok";
            }
        }

        return "wrong key";
    }
}
