package ru.sw.modules.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

}
