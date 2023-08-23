package com.multisoftware.service;

import com.multisoftware.model.Geopoint;
import com.multisoftware.repository.GeopointsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CombustionChartService {

    private static final Logger logger = LogManager.getLogger(CombustionChartService.class);

    private List<Geopoint> listaDanych;

    @Autowired
    private GeopointsRepository geopointsRepository;

    @SuppressWarnings("unchecked")
    public List<Geopoint> getCombustionData(Long idSystem, Long idRoute){
        listaDanych = geopointsRepository.findByIDRoute(idSystem, idRoute);
        return listaDanych;
    }

    @SuppressWarnings("unchecked")
    public List<Geopoint> getCombustionDataByRouteBetweenTime(Long idSystem, Long idRoute, String timeFrom, String timeTo){
        int minutesFrom = 0;
        int minutesTo = 0;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Calendar calFrom = Calendar.getInstance();
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = df.parse(timeFrom);
            dateTo = df.parse(timeTo);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        calFrom.setTime(dateFrom);
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(dateTo);
        minutesFrom = calFrom.get(Calendar.HOUR_OF_DAY) * 60 + calFrom.get(Calendar.MINUTE);
        minutesTo = calTo.get(Calendar.HOUR_OF_DAY) * 60 + calTo.get(Calendar.MINUTE);

        listaDanych = geopointsRepository.findByIDRouteAndTime(idSystem, idRoute, minutesFrom, minutesTo);
        return listaDanych;
    }
}
