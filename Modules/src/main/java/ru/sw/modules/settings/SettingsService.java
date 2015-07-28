package ru.sw.modules.settings;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sw.platform.core.services.AbstractService;
import ru.sw.platform.core.services.BootsrapService;

import java.util.HashMap;

@Service("SettingsService")
public class SettingsService extends AbstractService implements BootsrapService<SettingsService> {

    @Autowired
    private SettingsRepository settingsRepository;

    @Override
    public void bootstrap(HashMap<String, Object> map) {
        if(settingsRepository.count(Settings.class) == 0) {
            Settings settings = new Settings();
            if(map.containsKey("siteName")) {
                settings.setSiteName((String) map.get("siteName"));
            } else {
                settings.setSiteName("steamLottery.ru");
            }

            if(map.containsKey("bonus")) {
                settings.setBonus((Integer) map.get("bonus"));
            } else {
                settings.setBonus(5);
            }

            if(map.containsKey("rate")) {
                settings.setRate((Integer) map.get("rate"));
            } else {
                settings.setRate(5);
            }

            if(map.containsKey("vk_group")) {
                settings.setVKGroupUrl((String) map.get("vk_group"));
            } else {
                settings.setVKGroupUrl("/#");
            }

            if(map.containsKey("countPlayersForStartGame")) {
                settings.setCountPlayerForStartGame((Integer) map.get("countPlayersForStartGame"));
            } else {
                settings.setCountPlayerForStartGame(3);
            }

            if(map.containsKey("setting_before_game_over")) {
                settings.setSecondsBeforeGameOver((Integer) map.get("setting_before_game_over"));
            } else {
                settings.setSecondsBeforeGameOver(300);
            }

            settingsRepository.create(settings);
        }
    }
}
