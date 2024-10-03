package hk.ust.comp3021;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Location {
    Double latitude;
    Double altitude;

    public Location(List<Double> arr) {
        latitude = arr.get(0);
        altitude = arr.get(1);
    }

    Double distanceTo(Location l){
        return sqrt(pow(this.altitude-l.altitude,2) + pow(this.latitude-l.latitude,2));
    }

}
