package controller.filter.comparators;

import model.bike.Bike;
import model.bike.BikeColor;
import model.bike.Brand;
import model.bike.RentalStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PriceComparatorTest {

    private PriceComparator underTest;

    @Before
    public void setUp(){
        underTest = new PriceComparator(10);
    }

    @Test
    public void testEqual() {
        Bike bike1 = new Bike(Brand.KROSS, BikeColor.WHITE, RentalStatus.FREE,10,"Road Bike","5");
        Bike bike2 = new Bike(Brand.CANNONDALE,BikeColor.BLACK,RentalStatus.FREE,10,"Road Bike","7");

        int result = underTest.compare(bike1, bike2);
        Assert.assertEquals(0,result);
    }

    @Test
    public void testGreaterThen() {
        Bike bike1 = new Bike(Brand.KROSS,BikeColor.BLACK,RentalStatus.FREE,12,"Road Bike","6");
        Bike bike2 = new Bike(Brand.CANNONDALE, BikeColor.WHITE, RentalStatus.FREE,10,"Road Bike","8");


        int result = underTest.compare(bike1, bike2);
        Assert.assertEquals(1,result);
    }

    @Test
    public void testLessThen() {
        Bike bike1 = new Bike(Brand.CANNONDALE,BikeColor.BLACK,RentalStatus.FREE,10,"Road Bike","8");
        Bike bike2 = new Bike(Brand.KROSS, BikeColor.WHITE, RentalStatus.FREE,8,"Road Bike","6");


        int result = underTest.compare(bike1, bike2);
        Assert.assertEquals(-1,result);
    }



}