package ru.sw.modules.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sw.modules.bootstrapService.InitialServiceInfo;
import ru.sw.modules.game.Game;
import ru.sw.modules.game.GameRepository;
import ru.sw.modules.steam.utils.Price;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.services.AbstractService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service("StatisticsService")
public class StatisticsService extends AbstractService {

    @Autowired
    private GameRepository gameRepository;

    @Action(name = "statistics")
    public Statistics statisticses(HashMap<String, Object> map) {
        Integer count = 50;
        if(map.containsKey("count")) {
            count = (Integer) map.get("count");
        }

        Statistics statistics = new Statistics();

        //select * from game WHERE date_of_start_game >= '2015-07-28'
        //AND date_of_start_game < ('2015-07-28'::date + '1 day'::interval);

        Calendar toDay = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(toDay.getTime());
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(Calendar.DATE, 1);
        String formatted2 = format1.format(nextDay.getTime());
        List l = entityManager.createNativeQuery("select count_players , count_weapons, rub, usd  from game WHERE date_of_start_game >= '" + formatted + "'" +
                "AND date_of_start_game < '"+formatted2+"'").getResultList();



        statistics.setCountGames(l.size());

        Integer sumPlayer = 0;
        Integer sumCountWeapon = 0;
        Double maxRub = 0.0;
        Double maxUsd = 0.0;

        for(Object object : l) {
            Object [] objects = (Object[]) object;
            Integer countPlayer = (Integer) objects[0];
            Integer countWeapon = (Integer) objects[1];
            Double rub = (Double) objects[2];
            Double usd = (Double) objects[3];

            sumPlayer += countPlayer;
            sumCountWeapon += countWeapon;


            if(maxRub < rub) {
                maxRub = rub;
                maxUsd = usd;
            }
        }
        statistics.setCountPlayers(sumPlayer);
        statistics.setCountWeapons(sumCountWeapon);
        statistics.setMaxCash(new Price(maxRub,maxUsd));

        List<Game> lastGames = entityManager.createQuery("select g from Game g order by g.id desc").setMaxResults(30).getResultList();
        statistics.setPreviousWinner(lastGames);

        List<Game> topGames = entityManager.createQuery("select g from Game g order by g.total.rub desc").setMaxResults(30).getResultList();
        statistics.setTopPlayer(topGames);

        return statistics;
    }
}
