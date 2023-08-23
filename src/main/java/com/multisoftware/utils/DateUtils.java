package com.multisoftware.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final Logger logger = LogManager.getLogger(DateUtils.class);

    public static Date formatDate(String inputDate) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
        } catch (ParseException e) {
           logger.error(e.getMessage(), e);
        }
        return new Date();
    }

    public static String formatDate(Date inputDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(inputDate);
    }

    public static String formatSqlDate(java.sql.Date inputDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(inputDate);
    }
}
