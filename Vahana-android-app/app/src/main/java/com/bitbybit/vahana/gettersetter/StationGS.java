package com.bitbybit.vahana.gettersetter;

public class StationGS {

    private String flag, name, distance;

    public StationGS(String flag, String name, String distance) {
        this.flag = flag;
        this.name = name;
        this.distance = distance;
    }

    public String getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }
}
