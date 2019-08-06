package controller.filter;

import model.bike.Bike;

import java.util.List;

public interface FilterController {

    boolean filterByColor(String color);
    boolean filterByBrand(String brand);
    boolean filterByStatus(String status);
    boolean filterByPrice(String pricePerHour);
    List<Bike> getActualList();
}
