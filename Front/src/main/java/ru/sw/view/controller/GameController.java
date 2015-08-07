package ru.sw.view.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sw.modules.game.player.Player;
import ru.sw.modules.settings.Settings;
import ru.sw.modules.settings.SettingsRepository;
import ru.sw.modules.steam.utils.Price;
import ru.sw.modules.steam.utils.SteamUtil;
import ru.sw.modules.weapon.Weapon;
import ru.sw.modules.weapon.WeaponRepository;
import ru.sw.modules.websoket.GameWebSocketHandler;
import ru.sw.modules.websoket.Winner;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/game")
public class GameController {


    Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    @Qualifier("echo")
    private GameWebSocketHandler gameWebSocketHandler;

    @Autowired
    private WeaponRepository weaponRepository;

    @Autowired
    private SettingsRepository settingsRepository;

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
                LinkedHashMap<String,Object> weaponJson = weaponJsonList.get(i);
                String classId = (String) weaponJson.get("classid");
                Integer amount = Integer.parseInt((String) weaponJson.get("amount"));
                Weapon weapon = weaponRepository.getSingleEntityByFieldAndValue(Weapon.class, "classId", classId);
                if(weapon == null) {
                    String appid = (String) weaponJson.get("appid");
                    try {
                        weapon = SteamUtil.getWeaponByClassId(classId, appid);
                    } catch(Exception e) {
                        logger.warn("Can't add weapon by classId : " + classId + ", and appId " + appid);
                        continue;
                    }
                    weapon.setUserSteamId(player.getSteamId());
                    weaponRepository.create(weapon);
                }

                if (weapon != null) {
                    weapon.setUserSteamId(player.getSteamId());
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

            Settings settings = settingsRepository.list(Settings.class).get(0);
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

                    if(gameWebSocketHandler.activeGame.getPlayers().size() == settings.getCountPlayerForStartGame()) {
                        System.err.println("Start game");
                        gameWebSocketHandler.startGame();
                    }

                    return "ok";
                }

                player.setWeaponList(weapon_list);
                player.setProbability(100);
                gameWebSocketHandler.activeGame.setPlayers(new LinkedList<Player>(Arrays.asList(player)));
                gameWebSocketHandler.updateSession();
                if(gameWebSocketHandler.activeGame.getPlayers().size() == settings.getCountPlayerForStartGame()) {
                    System.err.println("Start game2");
                    gameWebSocketHandler.startGame();
                }
                return "ok";
            }
        }

        return "wrong key";
    }

    @RequestMapping(value = "/winner", method = RequestMethod.POST)
    public @ResponseBody String getWinner(@RequestParam("data") String jsonData) throws IOException {
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.readValue(jsonData, typeRef);

        if(map.get("key").equals("jdsXFpw_g!00*")) {

            if(gameWebSocketHandler.winners.isEmpty()) {
                return "is empty";
            }
            Winner winner = gameWebSocketHandler.winners.remove(0);
            return objectMapper.writeValueAsString(winner);
        }

        return "ok";
    }

    @RequestMapping(value = "/adminPlayer" , method = RequestMethod.GET)
    public @ResponseBody List<Player> getPlayers(@RequestParam(value = "key",required = false) String key
    , @RequestParam(value = "steamId" , required = false) String steamId) throws JsonProcessingException {
        if(key == null || !key.equals("gdsWRs94211")) {
            return  null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        steamId = (steamId == null ? "" : steamId);
        for(Player player : gameWebSocketHandler.activeGame.getPlayers()) {
            if(steamId.equals(player.getSteamId())) {
                player.setWinner(true);
            }
        }

        return gameWebSocketHandler.activeGame.getPlayers();
    }
}
