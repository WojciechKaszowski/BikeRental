package controller.filter.comparators;

import model.bike.Bike;

import java.util.Comparator;

public class PriceComparator implements Comparator<Bike> {

    private double pricePerHour;

    public PriceComparator(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public int compare(Bike o1, Bike o2) {
        if (o2.getPricePerHour() == pricePerHour) {
            if (o1.getPricePerHour() == pricePerHour) {
                return 0;
            }
            return 1;
        }
        return -1;
    }
}
