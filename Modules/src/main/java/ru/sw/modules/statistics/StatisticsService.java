package ru.sw.modules.statistics;

import org.springframework.stereotype.Service;
import ru.sw.modules.bootstrapService.InitialServiceInfo;
import ru.sw.modules.steam.utils.Price;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.services.AbstractService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service("StatisticsService")
public class StatisticsService extends AbstractService {



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
//        List l = entityManager.createNativeQuery("select count_players , count_weapons,  from game WHERE date_of_start_game >= '" + formatted + "'" +
//                "AND date_of_start_game < '"+formatted2+"'").getResultList();
//
//
//
//        statistics.setCountGames(l.size());
//
//        for(Object object : l) {
//            Object [] objects = (Object[]) object;
//
//        }
        statistics.setCountPlayers(43);
        statistics.setCountWeapons(245);
        statistics.setMaxCash(new Price(12,5));


        return statistics;
    }
}
