package com.multisoftware.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.model.SelectItem;

import com.multisoftware.dto.RouteDTO;
import com.multisoftware.enums.MonthsEnum;
import com.multisoftware.enums.DateFilterEnum;
import com.multisoftware.messages.FacesMessages;
import com.multisoftware.model.Driver;
import com.multisoftware.model.Geopoint;
import com.multisoftware.service.RouteService;
import com.multisoftware.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;

import org.springframework.beans.factory.annotation.Autowired;

import com.multisoftware.enums.TypeFilterEnum;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class RoutesList extends FacesMessages implements Serializable {

    private static final long serialVersionUID = -7297484590290692485L;

    private static final Logger logger = LogManager.getLogger(RoutesList.class);
    
    @Autowired
    private RouteService routesBean;

    private List<RouteDTO> routesList;
    private Map<Driver, RouteDTO> routesListMap;
    private Date date;
    private Date dateFrom;
    private Date dateTo;
    private String searchDate;
    private String dateOption;
    private String typeOption;
    private String background = "streets";
    private double latitude;
    private double longitude;
    private double routeLatitude = 0.0;
    private double routeLongitude = 0.0;
    private int zoom = 4;
    private double opacity = 1.0;
    private String where = "MAGNITUDE >= 2";
    private boolean detail;
    private Long selectedOrderId;
    private List<Geopoint> geopointList;
    private List<Geopoint> selectedGeopoint;
    private MonthsEnum selectedMonth;
    private String selectedYear;
    private List<SelectItem> monthsList = new ArrayList<SelectItem>();
    private List<String> yearsList = new ArrayList<String>();
    private boolean skip;

    @Autowired
    private ChartView chartView;

    private String selectedIdRoute;

    @PostConstruct
    public void init() {
        // date = new java.util.Date();
        date = DateUtils.formatDate("23/05/2013");
        dateOption = DateFilterEnum.DATE.name();
        latitude = 53.972753;
        longitude = 17.281934;
        monthsList = getMonths();
        yearsList = getYears();
        setSelectedOrderId(2L);
        routesList = routesBean.getRouteByDay(date);
    }

    private Date formatDate(String inputDate) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return new Date();
    }

    public void searchAction() {
        DateFilterEnum dateFilterEnum = DateFilterEnum.valueOf(dateOption);
        TypeFilterEnum typeFilterEnum = TypeFilterEnum.valueOf(typeOption);

        logger.debug("Akcja wyszukaj");
        if (typeFilterEnum.equals(TypeFilterEnum.DRIVER) && !dateFilterEnum.equals(DateFilterEnum.MONTH)) {
            logger.debug("Szukam nazwiska:" + searchDate + " dnia:" + date.toString());
            routesList = routesBean.getRouteByDayAndDriver(date, searchDate);
        } else if (typeFilterEnum.equals(TypeFilterEnum.VEHICLE) && dateFilterEnum.equals(DateFilterEnum.DATERANGE)) {
            logger.debug("Szukam pojazdu:" + searchDate + " dnia:" + date.toString());
            routesList = routesBean.getRouteByDayRangeAndRegNum(dateFrom, dateTo, searchDate);
        } else if (typeFilterEnum.equals(TypeFilterEnum.VEHICLE) && !dateFilterEnum.equals(DateFilterEnum.MONTH)) {
            logger.debug("Szukam pojazdu:" + searchDate + " dnia:" + date.toString());
            routesList = routesBean.getRouteByDayAndRegNum(date, searchDate);
        } else if (dateFilterEnum.equals(DateFilterEnum.DATE)) {
            // routesList = routesBean.getRouteByDayDetails(date);
            routesList = routesBean.getRouteByDay(date);
        } else if (dateFilterEnum.equals(DateFilterEnum.DATERANGE)) {
            routesList = routesBean.getRouteByDateRange(dateFrom, dateTo);
        } else if (dateFilterEnum.equals(DateFilterEnum.MONTH)) {
            DateFormat sd = new SimpleDateFormat("MM-yyyy");
            int month = MonthsEnum.get(selectedMonth);
            date = new Date();
            try {
                date = sd.parse(month + "-" + selectedYear);
                if (typeFilterEnum.equals(TypeFilterEnum.VEHICLE)) {
                    routesList = routesBean.getRouteByMonthAndYearAndRegNum(date, searchDate);
                } else if (typeFilterEnum.equals(TypeFilterEnum.DRIVER)) {
                    routesList = routesBean.getRouteByMonthAndDriver(date, searchDate);
                } else {
                    routesList = routesBean.getRouteByMonthAndYear(date);
                }
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        showNoDataToDisplayIfEmptyRoutesList();
    }

    private void showNoDataToDisplayIfEmptyRoutesList() {
        showMessage("Brak danych do wyświetlenia");
    }

    public void makeGeopointsInsert() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\geopoints.txt"))) {
            // PrintWriter pw = new PrintWriter(new FileWriter("d:\\out.txt"));
            String line;
            int idGeop = 168;
            while ((line = br.readLine()) != null) {
                String[] strArr = line.split(",");
                double lon = Double.valueOf(strArr[0]);
                double lat = Double.valueOf(strArr[1]);
                // logger.debug("INSERT INTO public.geopoints(idgeopoints, idsystem,
                // iddriver, idroutes, lat, lon, gas, maxspeed, datetime, idvehicle,
                // distance)VALUES ("+idGeop+", 2, 10, 15, "+lat+", "+lon+", 35, 54,
                // '2016-04-25', 5, 30);");
                String insert = "INSERT INTO public.geopoints(idgeopoints, idsystem, iddriver, idroutes, lat, lon, gas, maxspeed, datetime, idvehicle, distance)VALUES ("
                        + idGeop + ", 2, 10, 15, " + lat + ", " + lon + ", 35, 54, '2016-04-25', 5, 30);";
                // pw.write(insert);
                idGeop++;
            }
            // pw.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public List<String> getYears() {
        int yearStart = 2013;
        int yearEnd = DateTime.now().year().get();
        List<String> yearList = new ArrayList<String>();
        for (int i = yearStart; yearStart < yearEnd; i++) {
            yearList.add(String.valueOf(i));
            yearStart++;
        }
        return yearList;
    }

    public static List<SelectItem> getMonths() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (MonthsEnum month : MonthsEnum.values()) {
            items.add(new SelectItem(month, MonthsEnum.getText(month)));
        }
        return items;
    }

    public void calculateTotal(Object valueOfThisSorting) {
        Double total = new Double(0);
        Integer totalHour = new Integer(0);
        Integer totalMinute = new Integer(0);
        Integer totalDay = new Integer(0);
        DateTime out = new DateTime();
        for (RouteDTO r : getRoutesList()) {
            if (r.getDriver().getLName().equals(valueOfThisSorting)) {
                total += ((RouteDTO) r).getRoute().getDistanceSum();
                r.setDistanceSum(total);
                DateTime startTime = new DateTime(((RouteDTO) r).getRoute().getDateStart());
                DateTime endTime = new DateTime(((RouteDTO) r).getRoute().getDateEnd());
                Period p = new Period(startTime, endTime);
                totalDay += p.getDays();
                r.setRouteDays(totalDay);
                totalHour += p.getHours();
                r.setRouteHours(totalHour);
                totalMinute += p.getMinutes();
                r.setRouteMinutes(totalMinute);
                calculateHours(r.getRouteMinutes());

                // dodane na szybko - sprawdzic
                out.plusHours(totalHour);
                out.plusMinutes(totalMinute);
            }
        }
    }

    public String redirectToRouteMap() {
//		return "routeMap.xhtml?faces-redirect=true&selectedIdRoute=".concat(String.valueOf(selectedRoute.getIDRoutes()));
        return "routeMap.xhtml?faces-redirect=true&includeViewParams=true";
    }

    public String action(String idRoute) {

        String outcome = "routeMap.xhtml?faces-redirect=true";

        try {
            outcome += String.format("&amp;selectedIdRoute=%s", URLEncoder.encode(idRoute, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }

        return outcome;
    }

    public String convertTimeWithTimeZone(Timestamp time) {
        long longTime = time.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(longTime);
        return (cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
    }

    private String calculateHours(Integer minutes) {
        String hours = String.format("%d Minutes: %d:%02d Hours", minutes, (minutes / 60), (minutes % 60));
        logger.debug(String.format("%d Minutes: %d:%02d Hours", minutes, (minutes / 60), (minutes % 60)));
        return "";
    }

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public ChartView getChartView() {
        return chartView;
    }

    public void setChartView(ChartView chartView) {
        this.chartView = chartView;
    }

    public String getDateOption() {
        return dateOption;
    }

    public void setDateOption(String option) {
        this.dateOption = option;
    }

    public String getTypeOption() {
        return typeOption;
    }

    public void setTypeOption(String typeOption) {
        this.typeOption = typeOption;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchData) {
        this.searchDate = searchData;
    }

    public List<RouteDTO> getRoutesList() {
        return routesList;
    }

    public void setRoutesList(List<RouteDTO> routesList) {
        this.routesList = routesList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date data) {
        this.date = data;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public Map<Driver, RouteDTO> getRoutesListMap() {
        return routesListMap;
    }

    public void setRoutesListMap(Map<Driver, RouteDTO> routesListMap) {
        this.routesListMap = routesListMap;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    /*
     * Depracated by getselectedRoute
     */
    public Long getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(Long selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

    public List<Geopoint> getGeopointList() {
        return geopointList;
    }

    public void setGeopointList(List<Geopoint> geopointList) {
        this.geopointList = geopointList;
    }

    public List<Geopoint> getSelectedGeopoint() {
        return selectedGeopoint;
    }

    public void setSelectedGeopoint(List<Geopoint> selectedGeopoint) {
        this.selectedGeopoint = selectedGeopoint;
    }

    public MonthsEnum getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(MonthsEnum selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public List<SelectItem> getMonthsList() {
        return monthsList;
    }

    public void setMonthsList(List<SelectItem> monthsList) {
        this.monthsList = monthsList;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

    public List<String> getYearsList() {
        return yearsList;
    }

    public void setYearsList(List<String> yearsList) {
        this.yearsList = yearsList;
    }

    public double getRouteLatitude() {
        return routeLatitude;
    }

    public void setRouteLatitude(double routeLatitude) {
        this.routeLatitude = routeLatitude;
    }

    public double getRouteLongitude() {
        return routeLongitude;
    }

    public void setRouteLongitude(double routeLongitude) {
        this.routeLongitude = routeLongitude;
    }

    public String getSelectedIdRoute() {
        return selectedIdRoute;
    }

    public void setSelectedIdRoute(String selectedIdRoute) {
        this.selectedIdRoute = selectedIdRoute;
    }
}
