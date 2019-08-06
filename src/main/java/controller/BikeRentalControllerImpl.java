package controller;

import controller.customer.CustomersController;
import controller.customer.CustomersControllerImpl;
import controller.filter.FilterController;
import controller.filter.FilterControllerImpl;
import controller.filter.FilterType;
import controller.rental.RentalCalculationController;
import controller.rental.RentalCalculationControllerImpl;
import controller.reservation.ReservationController;
import controller.reservation.ReservationControllerImpl;
import model.bike.Bike;
import model.bike.BikeRepository;
import model.bike.BikeRepositoryImpl;
import model.customer.CustomerRepository;
import model.customer.CustomerRepositoryImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class BikeRentalControllerImpl implements BikeRentalController {

    private RentalCalculationController rentalCalculationController;
    private FilterController filterController;
    private ReservationController reservationController;
    private CustomersController customersController;

    public BikeRentalControllerImpl() {
        BikeRepository bikeRepository = new BikeRepositoryImpl();
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        this.rentalCalculationController = new RentalCalculationControllerImpl();
        this.filterController = new FilterControllerImpl(bikeRepository);
        this.reservationController = new ReservationControllerImpl(bikeRepository);
        this.customersController = new CustomersControllerImpl(customerRepository);
    }

    public BikeRentalControllerImpl(FilterController filterController, ReservationController reservationController,
                                    RentalCalculationController rentalCalculationController, CustomersController customersController) {
        this.filterController = filterController;
        this.reservationController = reservationController;
        this.rentalCalculationController = rentalCalculationController;
        this.customersController = customersController;
    }

    @Override
    public boolean returnBike(int bikeToReturn) {
        if (bikeToReturn <= 0 || bikeToReturn > reservationController.getListOfAllReservedBikes().size()) {
            return false;
        }
        Bike bike = reservationController.returnBike(bikeToReturn-1);
        double rentalPrice = calculateMoneyForReturn(bike);
        double price = customersController.takeRentalPriceFromWallet(rentalPrice);
        customersController.setWallet(price);
        System.out.println("The amount " + rentalPrice + " for reservation was taken from the wallet");
        return true;
    }

    @Override
    public boolean addMoneyToWallet(double addMoney) {
        addMoney = roundMoney(addMoney);
        return customersController.addMoneyToWallet(addMoney);
    }

    @Override
    public boolean checkIfCustomerExist(String customerName) {
        if(customersController.validateCustomerByName(customerName)) {
            System.out.println("You succesfully login as: " + customerName);
            return true;
        }
        return false;
    }

    @Override
    public void addNewCustomer(String name, double money) {
        money = roundMoney(money);
        customersController.addCustomer(name,money);
    }

    private double roundMoney(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }

    @Override
    public boolean isExitPossible() {
        return isWalletStatusOk();
    }

    @Override
    public boolean filter(FilterType filterType, String value) {

        return filterType.executeFilter(value,filterController);
    }

    @Override
    public boolean bookABike(int bikeNumber) {
        return reservationController.bookBike(bikeNumber, getListOfBikesAvailableForCustomer());
    }

    @Override
    public List<Bike> getListOfBikesAvailableForCustomer() {
        double customerWallet = customersController.getWallet();
        return filterController.getActualList().stream()
                .filter(bike -> bike.getPricePerHour() <= customerWallet)
                .collect(Collectors.toList());
    }

    @Override
    public List<Bike> getListOfReservedBikes() {
        return reservationController.getListOfAllReservedBikes();
    }

    @Override
    public void setUpListOfReservedBikes() {
        reservationController.setReservedBikesList(customersController.getCustomer());
    }

    @Override
    public double getWallet() {
        return customersController.getWallet();
    }

    @Override
    public boolean isFilterPossible(FilterType filterType, String value) {
        switch(filterType) {
            case COLOR: return isColorAvailable(value);
            case BRAND: return isBrandAvailable(value);
            case RENTAL_STATUS: return isRentalStatusAvailable(value);
            case PRICE: return isPriceAvailable(value);
        }
        return false;
    }

    private boolean isWalletStatusOk() {
        return customersController.isWalletStatusOk();
    }

    private double calculateMoneyForReturn(Bike bike) {
        return rentalCalculationController.calculateMoneyForReturn(bike);
    }

    private boolean isColorAvailable(String value) {
        List<Bike> bikes = getListOfBikesAvailableForCustomer();
        long count = bikes.stream()
                .filter(b -> b.getColor().toString().equals(value.toUpperCase()))
                .count();
        return count > 0;
    }

    private boolean isBrandAvailable(String value) {
        List<Bike> bikes = getListOfBikesAvailableForCustomer();
        long count = bikes.stream()
                .filter(b -> b.getBrand().toString().equals(value.toUpperCase()))
                .count();
        return count > 0;
    }

    private boolean isRentalStatusAvailable(String value) {
        List<Bike> bikes = getListOfBikesAvailableForCustomer();
        long count = bikes.stream()
                .filter(b -> b.getRentalStatus().toString().equals(value.toUpperCase()))
                .count();
        return count > 0;
    }

    private boolean isPriceAvailable(String value) {
        List<Bike> bikes = getListOfBikesAvailableForCustomer();
        double price;
        try {
             price = Double.parseDouble(value.toUpperCase());
        }
        catch (NumberFormatException e) {
            return false;
        }
        long count = bikes.stream()
                .filter(b -> b.getPricePerHour() == price)
                .count();
        return count > 0;
    }

}
