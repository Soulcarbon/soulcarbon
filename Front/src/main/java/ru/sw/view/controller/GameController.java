package ru.sw.view.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sw.modules.game.player.Player;
import ru.sw.modules.steam.utils.Price;
import ru.sw.modules.steam.utils.SteamUtil;
import ru.sw.modules.weapon.Weapon;
import ru.sw.modules.weapon.WeaponRepository;
import ru.sw.modules.websoket.GameWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/game")
public class GameController {


    @Autowired
    @Qualifier("echo")
    private GameWebSocketHandler gameWebSocketHandler;

    @Autowired
    private WeaponRepository weaponRepository;

    @RequestMapping(value = "/addPlayer" , method = RequestMethod.POST)
    public @ResponseBody String addPlayer(@RequestParam("data") String jsonData) throws IOException {
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.readValue(jsonData, typeRef);

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


            synchronized (gameWebSocketHandler.monitor) {
                gameWebSocketHandler.activeGame.getTotal().addPrice(player.getTotal());
                Integer tempCountWeapon = gameWebSocketHandler.activeGame.getCountWeapons();
                gameWebSocketHandler.activeGame.setCountWeapons(tempCountWeapon + weapon_list.size());
                if (gameWebSocketHandler.activeGame.getPlayers() != null && !gameWebSocketHandler.activeGame.getPlayers().isEmpty()) {

                    //Проверяем не ставил ли такой игрок
                    for(Player element : gameWebSocketHandler.activeGame.getPlayers()) {
                        if (element.getSteamId().equals(player.getSteamId())) {
                            element.getWeaponList().addAll(weapon_list);
                            element.getTotal().addPrice(totalCost);
                            for (Player player1 : gameWebSocketHandler.activeGame.getPlayers()) {
                                player1.setProbability(player1.getTotal().getUsd() / gameWebSocketHandler.activeGame.getTotal().getUsd() * 100);
                            }
                            gameWebSocketHandler.updateSession();
                            return "ok";
                        }
                    }
                    //Игрок ставит первый раз
                    player.setWeaponList(weapon_list);
                    gameWebSocketHandler.activeGame.getPlayers().add(player);
                    for(Player element : gameWebSocketHandler.activeGame.getPlayers()) {
                        element.setProbability(element.getTotal().getUsd() / gameWebSocketHandler.activeGame.getTotal().getUsd() * 100);
                    }
                    gameWebSocketHandler.updateSession();
                    if(gameWebSocketHandler.activeGame.getPlayers().size() == 3) {
                        gameWebSocketHandler.startGame();
                    }

                    return "ok";
                }

                player.setWeaponList(weapon_list);
                player.setProbability(100);
                gameWebSocketHandler.activeGame.setPlayers(new LinkedList<Player>(Arrays.asList(player)));
                gameWebSocketHandler.updateSession();
                return "ok";
            }
        }

        return "wrong key";
    }
}
