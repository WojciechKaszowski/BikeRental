package controller.filter.comparators;

import model.bike.Bike;
import model.bike.RentalStatus;

import java.util.Comparator;

public class StatusComparator implements Comparator<Bike> {

    private RentalStatus rentalStatus;

    public StatusComparator(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    @Override
    public int compare(Bike o1, Bike o2) {
        if(o2.getRentalStatus().equals(rentalStatus)) {
            if(o1.getRentalStatus().equals(rentalStatus)) {
                return 0;
            }
            return 1;
        }
        return -1;
    }
}
