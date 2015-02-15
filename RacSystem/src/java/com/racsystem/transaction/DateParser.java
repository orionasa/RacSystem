/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.racsystem.transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author asa
 */
public class DateParser {

    public int parseDate(String date1, String date2) throws ParseException {
        Date d1 = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.ENGLISH).parse(date1);
        Date d2 = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.ENGLISH).parse(date2);
        if (d2.getTime() < d1.getTime()) {
            return -1;
        } else {
            return (int) ((d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000));
        }
    }

    public boolean compareDate(String date1, String date2) throws ParseException {
        Date d0 = new Date();
        Date d1 = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.ENGLISH).parse(date1);
        Date d2 = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.ENGLISH).parse(date2);
        return ((d1.getTime() <= d0.getTime()) && (d0.getTime() <= d2.getTime()));
    }

    public boolean receiveCar(String date1) throws ParseException {
        Date d0 = new Date();
        Date d1 = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.ENGLISH).parse(date1);
        return d0.getTime() > d1.getTime();
    }

    public boolean nonReceiveCar(String date1) throws ParseException {
        Date d0 = new Date();
        Date d1 = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.ENGLISH).parse(date1);
        return d0.getTime() < d1.getTime();
    }

    public static String formatDate(String date) throws ParseException {
        Calendar mydate = new GregorianCalendar();
        Date d1 = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.ENGLISH).parse(date);
        mydate.setTime(d1);
        return mydate.get(Calendar.MONTH) + "-" + mydate.get(Calendar.DAY_OF_MONTH) + "-" + mydate.get(Calendar.YEAR);
    }

}
