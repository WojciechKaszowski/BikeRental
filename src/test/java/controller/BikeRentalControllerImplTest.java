package controller;

import controller.customer.CustomersController;
import controller.filter.FilterController;
import controller.filter.FilterType;
import controller.rental.RentalCalculationController;
import controller.reservation.ReservationController;
import model.bike.Bike;
import model.bike.BikeColor;
import model.bike.Brand;
import model.bike.RentalStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BikeRentalControllerImplTest {

    @Mock
    private FilterController filterController;

    @Mock
    private ReservationController reservationController;

    @Mock
    private RentalCalculationController rentalCalculationController;

    @Mock
    private CustomersController customersController;


    private BikeRentalController underTest;

    @Before
    public void setUp(){
        underTest = new BikeRentalControllerImpl(filterController,reservationController,rentalCalculationController,customersController);
    }

    @Test
    public void returnBike_shouldReturnFalseWhenBikeNrIsLessOrEqual0() {
        Assert.assertFalse(underTest.returnBike(-2));
    }

    @Test
    public void returnBike_shouldReturnFalseWhenBikeNrIsMoreThenListSize() {
        Mockito.when(reservationController.getListOfAllReservedBikes()).thenReturn(listOfReservedBikes());
        Assert.assertFalse(underTest.returnBike(8));
    }

    @Test
    public void returnBike_shouldReturnTrue() {
        Mockito.when(reservationController.getListOfAllReservedBikes()).thenReturn(listOfReservedBikes());
        Assert.assertTrue(underTest.returnBike(3));
    }

    @Test
    public void addMoneyToWallet_shouldReturnTrue() {
        Mockito.when(customersController.addMoneyToWallet(100)).thenReturn(true);
        Assert.assertTrue(underTest.addMoneyToWallet(100));
    }

    @Test
    public void addMoneyToWallet_shouldReturnFalse() {
        Mockito.when(customersController.addMoneyToWallet(-50)).thenReturn(false);
        Assert.assertFalse(underTest.addMoneyToWallet(-50));
    }

    @Test
    public void checkIfCustomerExist_shouldReturnTrue() {
        Mockito.when(customersController.validateCustomerByName("Adam")).thenReturn(true);
        Assert.assertTrue(underTest.checkIfCustomerExist("Adam"));
    }

    @Test
    public void checkIfCustomerExist_shouldReturnFalse() {
        Mockito.when(customersController.validateCustomerByName("Adam")).thenReturn(false);
        Assert.assertFalse(underTest.checkIfCustomerExist("Adam"));
    }

    @Test
    public void isExitPossible_shouldReturnTrueWhen_isWalletStatusOkIsTrue() {
        Mockito.when(customersController.isWalletStatusOk()).thenReturn(true);

        boolean walletStatus = customersController.isWalletStatusOk();

        Assert.assertEquals(walletStatus,underTest.isExitPossible());

    }

    @Test
    public void isExitPossible_shouldReturnFalseWhen_isWalletStatusOkIsFalse() {
        Mockito.when(customersController.isWalletStatusOk()).thenReturn(false);

        boolean walletStatus = customersController.isWalletStatusOk();


        Assert.assertEquals(walletStatus,underTest.isExitPossible());

    }

    @Test
    public void getListOfBikesAvailableForCustomer() {

        Mockito.when(customersController.getWallet()).thenReturn(10.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfActualBikes());

        Assert.assertEquals(listOfBikesAvailableForCustomer(),underTest.getListOfBikesAvailableForCustomer());
    }

    @Test
    public void getListOfReservedBikes() {
        Mockito.when(reservationController.getListOfAllReservedBikes()).thenReturn(listOfReservedBikes());
        Assert.assertEquals(listOfReservedBikes(),underTest.getListOfReservedBikes());
        Mockito.verify(reservationController).getListOfAllReservedBikes();
    }

    @Test
    public void getWallet() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Assert.assertEquals(100.0,customersController.getWallet(),0);
        Mockito.verify(customersController).getWallet();
    }

    @Test
    public void isFilterPossible_shouldReturnTrueWhenColorOnList() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfActualBikes());

        Assert.assertTrue(underTest.isFilterPossible(FilterType.COLOR,"blue"));

    }

    @Test
    public void isFilterPossible_shouldReturnFalseWhenColorIsNotOnList() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfActualBikes());

        Assert.assertFalse(underTest.isFilterPossible(FilterType.COLOR,"brown"));

    }

    @Test
    public void isFilterPossible_shouldReturnTrueWhenBrandOnList() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfActualBikes());

        Assert.assertTrue(underTest.isFilterPossible(FilterType.BRAND,"kross"));

    }

    @Test
    public void isFilterPossible_shouldReturnFalseWhenBrandIsNotOnList() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfActualBikes());

        Assert.assertFalse(underTest.isFilterPossible(FilterType.BRAND,"suzuki"));

    }

    @Test
    public void isFilterPossible_shouldReturnTrueWhenStatusOnList() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfActualBikes());

        Assert.assertTrue(underTest.isFilterPossible(FilterType.RENTAL_STATUS,"free"));

    }

    @Test
    public void isFilterPossible_shouldReturnFalseWhenStatusIsNotOnList() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfReservedBikes());

        Assert.assertFalse(underTest.isFilterPossible(FilterType.RENTAL_STATUS,"occupied"));

    }

    @Test
    public void isFilterPossible_shouldReturnTrueWhenPricerOnList() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfActualBikes());

        Assert.assertTrue(underTest.isFilterPossible(FilterType.PRICE,"10"));

    }

    @Test
    public void isFilterPossible_shouldReturnFalseWhenPriceIsNotOnList() {
        Mockito.when(customersController.getWallet()).thenReturn(100.0);
        Mockito.when(filterController.getActualList()).thenReturn(listOfActualBikes());

        Assert.assertFalse(underTest.isFilterPossible(FilterType.PRICE,"100"));

    }




    private List<Bike> listOfReservedBikes() {
        List<Bike> bikes = new ArrayList<>();

        bikes.add(new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10,"City Bike","1"));
        bikes.add(new Bike(Brand.CANNONDALE,BikeColor.WHITE,RentalStatus.FREE,8,"Road Bike","2"));
        bikes.add(new Bike(Brand.SCOTT,BikeColor.BLACK,RentalStatus.FREE,12,"Road Bike","3"));

        return bikes;
    }


    private List<Bike> listOfActualBikes() {
        List<Bike> bikes = new ArrayList<>();

        bikes.add(new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10,"City Bike","1"));
        bikes.add(new Bike(Brand.CANNONDALE,BikeColor.WHITE,RentalStatus.FREE,8,"Road Bike","2"));
        bikes.add(new Bike(Brand.SCOTT,BikeColor.BLACK,RentalStatus.FREE,12,"Road Bike","3"));
        bikes.add(new Bike(Brand.GIANT,BikeColor.YELLOW,RentalStatus.OCCUPIED,11,"MTB Bike","4"));
        bikes.add(new Bike(Brand.TREK,BikeColor.BLACK,RentalStatus.OCCUPIED,16,"Road Bike","5"));

        return bikes;
    }

    private List<Bike> listOfBikesAvailableForCustomer() {
        List<Bike> bikes = new ArrayList<>();

        bikes.add(new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10,"City Bike","1"));
        bikes.add(new Bike(Brand.CANNONDALE,BikeColor.WHITE,RentalStatus.FREE,8,"Road Bike","2"));

        return bikes;
    }

}