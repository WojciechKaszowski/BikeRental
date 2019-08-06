package controller.filter;

import controller.filter.comparators.BrandComparator;
import controller.filter.comparators.ColorComparator;
import controller.filter.comparators.PriceComparator;
import controller.filter.comparators.StatusComparator;
import model.bike.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilterControllerImpl implements FilterController {

    /**
     * FilterController is a class allowing to filter list of bikes by value from the user,
     * it uses 4 comparators located in comparators package to do that.
     */

    private List<Bike> listOfBikesAfterFilter;

    public FilterControllerImpl(BikeRepository bikeRepository) {
        listOfBikesAfterFilter = bikeRepository.getAllBikes();
    }

    @Override
    public boolean filterByColor(String color) {
        try {
            BikeColor bikeColor = BikeColor.valueOf(color.toUpperCase());
            listOfBikesAfterFilter =  filterList(new ColorComparator(bikeColor));
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean filterByBrand(String brand) {
        try {
            Brand bikeBrand = Brand.valueOf(brand.toUpperCase());
            listOfBikesAfterFilter =  filterList(new BrandComparator(bikeBrand));
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean filterByStatus(String status) {
        try {
            RentalStatus bikeStatus = RentalStatus.valueOf(status.toUpperCase());
            listOfBikesAfterFilter =  filterList(new StatusComparator(bikeStatus));
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean filterByPrice(String price) {
        try {
            double bikePrice = Double.parseDouble(price);
            if(bikePrice <= 0) {
                return false;
            }
            listOfBikesAfterFilter =  filterList(new PriceComparator(bikePrice));
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public List<Bike> getActualList() {
        return listOfBikesAfterFilter;
    }

    private List<Bike> filterList(Comparator<Bike> comparator) {
        return listOfBikesAfterFilter.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

}
