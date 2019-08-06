package controller.rental;

import model.bike.Bike;
import model.bike.BikeColor;
import model.bike.Brand;
import model.bike.RentalStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

public class RentalCalculationControllerImplTest {


    private RentalCalculationController underTest;


    @Before
    public void setUp(){

        underTest = new RentalCalculationControllerImpl();
    }

    @Test
    public void calculateMoneyForReturn_shouldReturnRentalPriceForBike() {
        Bike bike = new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10.0,"City Bike","5");
        bike.setBookTime(LocalTime.now());
        Assert.assertEquals(10.0,underTest.calculateMoneyForReturn(bike),0);
    }

}