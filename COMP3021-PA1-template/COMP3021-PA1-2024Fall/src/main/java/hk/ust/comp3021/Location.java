package hk.ust.comp3021;

import java.util.List;

public class Location {
    Double latitude;
    Double altitude;

    public Location(List<Double> arr) {
        latitude = arr.get(0);
        altitude = arr.get(1);
    }

    Double distanceTo(Location l){

        return 0.0;
    }

}
