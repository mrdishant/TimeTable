package com.nearur.timetable;

/**
 * Created by mrdis on 8/7/2017.
 */

public class Lecture {

    String name,tname,room,type;

    public Lecture(String name, String tname, String room, String type) {
        this.name = name;
        this.tname = tname;
        this.room = room;
        this.type = type;
    }

    @Override
    public String toString() {
        return "\nName : "+ name +
                "\nTeacher : " + tname +
                "\nRoom : " + room +
                "\n" + type ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
