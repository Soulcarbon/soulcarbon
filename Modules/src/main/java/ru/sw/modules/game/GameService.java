package ru.sw.modules.game;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.services.AbstractService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("GameService")
public class GameService extends AbstractService {

    @Action(name = "addPlayer")
    public String addPlayer(HashMap<String, Object> map) {

        for(Map.Entry<String,Object> entry : map.entrySet()) {
            System.err.println(entry.getKey() + "..." + entry.getValue());
        }


//        JSONObject jsonObject = new JSONObject(map.get());
//        String steamId = jsonObject.getString("steamid_other");
//        JSONArray jsonArray = jsonObject.getJSONArray("weaponJsonList");
//        List<Weapon> list = new ArrayList<Weapon>();
//        double cost = 0.0;
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject temp = jsonArray.getJSONObject(i);
//            String classId = temp.getString("classid");
//            Integer amount = temp.getInt("amount");
//            String appid = temp.getString("appid");
//            Weapon weapon = getWeaponByClassId(classId, appid);
//            if (weapon != null) {
//                for (int j = 0; j < amount; j++) {
//                    list.add(weapon);
//                    cost += weapon.getPrice();
//                }
//            }
//        }
//        User user = getUserBySteamId(steamId);
//        while (gameWebSocketHandler.activeGame == null) {
//            Thread.sleep(200);
//        }
//
//        if (cost < 1e-9) {
//            return "false";
//        }
//        user.setTotalSum(cost);
//        user.setSteamId(steamId);
//        gameWebSocketHandler.activeGame.setTotal(gameWebSocketHandler.activeGame.getTotal() + user.getTotalSum());
//        if (gameWebSocketHandler.activeGame.getUserList() != null) {
//            for (User element : gameWebSocketHandler.activeGame.getUserList()) {
//                if (element.getSteamId().equals(user.getSteamId())) {
//                    element.getWeaponList().addAll(list);
//                    element.setTotalSum(element.getTotalSum() + cost);
//                    for (User user1 : gameWebSocketHandler.activeGame.getUserList()) {
//                        user1.setProbability(user1.getTotalSum() / gameWebSocketHandler.activeGame.getTotal() * 100);
//                    }
//                    gameWebSocketHandler.updateSession();
//                    return "ok";
//                }
//            }
//        }
//        user.setWeaponList(list);
//        gameWebSocketHandler.activeGame.addUser(user);
//        gameWebSocketHandler.realPlayer++;
//        gameWebSocketHandler.realTotal += (int) cost + 1;
//        if (gameWebSocketHandler.realPlayer == 3) {
//            gameWebSocketHandler.activeGame.setState(Game.State.Initialize);
//        }
//        if (gameWebSocketHandler.activeGame.getUserList() != null) {
//            for (User user1 : gameWebSocketHandler.activeGame.getUserList()) {
//                user1.setProbability(user1.getTotalSum() / gameWebSocketHandler.activeGame.getTotal() * 100);
//            }
//        }
//        if(gameWebSocketHandler.activeGame.getUserList().size() == 3){
//            new Thread(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            gameWebSocketHandler.game();
//                        }
//                    }
//            ).start();
//        }
//        gameWebSocketHandler.updateSession();
//        return "ok";


        return "player added";
    }
}
