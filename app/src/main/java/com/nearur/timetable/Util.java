package com.nearur.timetable;

import android.net.Uri;

/**
 * Created by mrdis on 8/7/2017.
 */

public class Util {

    public static final int dbverdion=1;
    public static final String dbname="TimeTableStudent";

    public static final String tabname="Student";
    public static final String tabname2="Attendance";

    public static final String number="Number";
    public static final String monday="monday";
    public static final String tuesday="tuesday";
    public static final String wednesday="wednesday";
    public static final String thrusday="thursday";
    public static final String friday="friday";
    public static final String saturday="saturday";
    public static final String sunday="sunday";

    public static final String subid="Id";
    public static final String sname="Name";
    public static final String attended="Attended";
    public static final String total="Total";
    public static final String missed="Missed";


    public static final String query="create table Student(" +
            "Number integer primary key,"+
            "monday text," +
            "tuesday text," +
            "wednesday text," +
            "thursday text," +
            "friday text," +
            "saturday text," +
            "sunday text" +
            ")";


    public static final Uri u=Uri.parse("content://com.nearur.timetable/"+tabname);


    public static final String query2="create table Attendance(" +
            "Name text primary key,"+
            "Attended integer," +
            "Missed integer," +
            "Total integer" +
            ")";


    public static final Uri u1=Uri.parse("content://com.nearur.timetable/"+tabname2);



}
