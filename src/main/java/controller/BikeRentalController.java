package controller;

import model.bike.Bike;
import controller.filter.FilterType;

import java.util.List;

public interface BikeRentalController {

    boolean isExitPossible();
    boolean filter(FilterType filterType, String value);
    boolean returnBike(int bikeToReturn);
    boolean addMoneyToWallet(double addMoney);
    boolean bookABike(int bikeNumber);
    List<Bike> getListOfBikesAvailableForCustomer();
    List<Bike> getListOfReservedBikes();
    double getWallet();
    boolean isFilterPossible(FilterType filterType, String value);
    boolean checkIfCustomerExist(String customerName);
    void addNewCustomer(String name, double money);
    void setUpListOfReservedBikes();
}
