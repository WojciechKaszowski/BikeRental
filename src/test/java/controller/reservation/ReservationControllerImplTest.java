package controller.reservation;

import model.bike.*;
import model.customer.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerImplTest {

    @Mock
    private BikeRepository bikeRepository;

    private ReservationController underTest;

    @Before
    public void setUp(){
        underTest = new ReservationControllerImpl(bikeRepository);

    }

    @Test
    public void bookBike_shouldReturnFalseWhenBikeNrIsOutOfRange() {
        Assert.assertFalse(underTest.bookBike(-1,prepareMockData()));
        Assert.assertFalse(underTest.bookBike(8,prepareMockData()));
    }

    @Test
    public void bookBike_shouldReturnTrueAndBookedBikeWhenRentalStatusFree() {
        Bike bike = new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10,"City Bike","5");

        Mockito.when(bikeRepository.getBike(bike)).thenReturn(bike);

        Assert.assertTrue(underTest.bookBike(1,prepareMockData()));
        Assert.assertEquals(1,underTest.getListOfAllReservedBikes().size());

    }

    @Test
    public void bookBike_shouldReturnFalseWhenBikeIsOccupied() {

        Assert.assertFalse(underTest.bookBike(-1,prepareMockData()));
        Assert.assertFalse(underTest.bookBike(8,prepareMockData()));
    }

    @Test
    public void bookBike_shouldReturnFalseWhenBikeNrIsWrong() {
        Bike bike = new Bike(Brand.GIANT,BikeColor.YELLOW,RentalStatus.OCCUPIED,6,"MTB Bike","7");

        Mockito.when(bikeRepository.getBike(bike)).thenReturn(bike);

        Assert.assertFalse(underTest.bookBike(4,prepareMockData()));
    }

    @Test
    public void returnBike() {
        Bike bike = new Bike(Brand.GIANT,BikeColor.YELLOW,RentalStatus.OCCUPIED,6,"MTB Bike","7");
        underTest.getListOfAllReservedBikes().add(bike);

        Mockito.when(bikeRepository.getBike(bike)).thenReturn(bike);

        Assert.assertEquals(bike,underTest.returnBike(0));

    }

    @Test
    public void getListOfAllReservedBikes() {
        underTest.getListOfAllReservedBikes().addAll(prepareMockData());

        Assert.assertEquals(5,underTest.getListOfAllReservedBikes().size());
    }

    @Test
    public void setReservedBikesList_shouldReturnListOfBikesAlreadyReserved() {
        Customer customer = new Customer("Adam",100);
        List<String> customerBikes = new ArrayList<>(Arrays.asList("5", "7"));
        customer.setBikes(customerBikes);
        Mockito.when(bikeRepository.getAllBikes()).thenReturn(prepareMockData());
        Assert.assertEquals(customerAlreadyReservedBikes(),underTest.setReservedBikesList(customer));

    }

    @Test
    public void setReservedBikesList_shouldReturnEmptyList() {
        Customer customer = new Customer("Adam",100);
        Mockito.when(bikeRepository.getAllBikes()).thenReturn(prepareMockData());
        Assert.assertEquals(0,underTest.setReservedBikesList(customer).size());
    }


    private List<Bike> prepareMockData() {
        List<Bike> bikes = new ArrayList<>();

        bikes.add(new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10,"City Bike","5"));
        bikes.add(new Bike(Brand.CANNONDALE,BikeColor.WHITE,RentalStatus.FREE,8,"Road Bike","4"));
        bikes.add(new Bike(Brand.SCOTT,BikeColor.BLACK,RentalStatus.FREE,12,"Road Bike","2"));
        bikes.add(new Bike(Brand.GIANT,BikeColor.YELLOW,RentalStatus.OCCUPIED,6,"MTB Bike","7"));
        bikes.add(new Bike(Brand.TREK,BikeColor.BLACK,RentalStatus.OCCUPIED,16,"Road Bike","9"));

        return bikes;
    }

    private List<Bike> customerAlreadyReservedBikes() {
        List<Bike> bikes = new ArrayList<>();
        bikes.add(new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10,"City Bike","5"));
        bikes.add(new Bike(Brand.GIANT,BikeColor.YELLOW,RentalStatus.OCCUPIED,6,"MTB Bike","7"));
        return bikes;
    }

}