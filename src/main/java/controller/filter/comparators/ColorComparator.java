package controller.filter.comparators;

import model.bike.Bike;
import model.bike.BikeColor;

import java.util.Comparator;

public class ColorComparator implements Comparator<Bike> {

   private BikeColor color;

    public ColorComparator(BikeColor color) {
        this.color = color;
    }

    @Override
    public int compare(Bike o1, Bike o2) {
        if(o2.getColor().equals(color)) {
            if(o1.getColor().equals(color)) {
                return 0;
            }
            return 1;
        }
        return -1;
    }
}
