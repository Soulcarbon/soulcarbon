package ru.sw.modules.steam.utils;

import org.apache.commons.httpclient.util.URIUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.sw.modules.game.player.Player;
import ru.sw.modules.weapon.Weapon;
import ru.sw.platform.core.exceptions.PlatofrmExecption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SteamUtil {

    public static String apiKey = "69B92BBD26395DFFD6301BE302590FFD";

    private static Logger logger = LoggerFactory.getLogger(SteamUtil.class);

    public static Weapon getWeaponByClassId(String classId, String appid) {
        JSONObject jsonObject = null;
        try {
            jsonObject = Utils.readJsonFromUrl("https://api.steampowered.com/ISteamEconomy/GetAssetClassInfo/v1/?key=" + apiKey + "&format=json&appid=" + appid + "&class_count=1&classid0=" + classId);
            JSONObject result = jsonObject.getJSONObject("result");
            if (result.getBoolean("success")) {
                Weapon weapon = new Weapon();
                weapon.setClassId(classId);
                String name = result.getJSONObject(classId).getString("market_name");
                String imageUrl = "http://cdn.steamcommunity.com/economy/image/" + result.getJSONObject(classId).getString("icon_url");

                String urlName = URIUtil.encodeQuery(name);
                jsonObject = Utils.readJsonFromUrl("http://steamcommunity.com/market/priceoverview/?currency=1&appid=" + appid + "&market_hash_name=" + urlName);
                boolean success = jsonObject.getBoolean("success");
                if (success) {
                    weapon.setWeaponName(name);
                    weapon.setImageUrl(imageUrl);
                    String price = jsonObject.get("lowest_price").toString();
                    //Dollar(USD)
                    if (price.startsWith("$")) {
                        price = price.replace("$", "");
                        Price valuteEntry = Utils.convertDollarToRuble(price);
                        weapon.setPrice(valuteEntry);
                    }

                    return weapon;
                }
            }
        } catch (Exception e) {
            logger.warn("Could not parse classId to weapon", e);
            throw new PlatofrmExecption("Could not parse classId to weapon", PlatofrmExecption.Type.ActionError);
        }
        return null;
    }

    public static Player getPlayerBySteamId(String steamId) {

        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse("http://steamcommunity.com/profiles/" + steamId + "/?xml=1");

            NodeList nodes = doc.getElementsByTagName("profile");
            Player user = new Player();
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                NodeList title = element.getElementsByTagName("steamID");
                Element line = (Element) title.item(0);
                user.setNickName(Utils.getCharacterDataFromElement(line));
            }

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                NodeList title = element.getElementsByTagName("avatarFull");
                Element line = (Element) title.item(0);
                user.setImageUrl(Utils.getCharacterDataFromElement(line));
            }

            return user;
        } catch (Exception e) {
            logger.warn("Could not get user by steamId" , e);
            throw new PlatofrmExecption("Could not get user by steamId" , PlatofrmExecption.Type.ActionError);
        }
    }
}
