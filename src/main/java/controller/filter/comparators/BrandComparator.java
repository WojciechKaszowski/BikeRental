package controller.filter.comparators;

import model.bike.Bike;
import model.bike.Brand;

import java.util.Comparator;

public class BrandComparator implements Comparator<Bike> {

    private Brand brand;

    public BrandComparator(Brand brand) {
        this.brand = brand;
    }

    @Override
    public int compare(Bike o1, Bike o2) {
        if(o2.getBrand().equals(brand)) {
            if(o1.getBrand().equals(brand)) {
                return 0;
            }
            return 1;
        }
        return -1;
    }
}
