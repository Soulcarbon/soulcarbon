package ru.sw.modules.statistics;

import org.springframework.stereotype.Service;
import ru.sw.modules.bootstrapService.InitialServiceInfo;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.services.AbstractService;

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

        entityManager.createNativeQuery("select ");

        return null;
    }
}
