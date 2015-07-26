package tests;


import org.junit.Test;
import ru.sw.modules.game.player.Player;
import ru.sw.modules.steam.utils.SteamUtil;
import ru.sw.modules.weapon.Weapon;

public class SteamTest {


    @Test
    public void someTest(){
        Weapon weapon =SteamUtil.getWeaponByClassId("310776543", "730");
        System.err.println(weapon.getClassId());
        System.err.println(weapon.getWeaponName());
        System.err.println(weapon.getDollarPrice());
        System.err.println(weapon.getRublePrice());

        Player player = SteamUtil.getPlayerBySteamId("76561198033391771");
        System.err.println(player.getNickName());
    }
}
