package model;

import model.bike.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class BikeRepositoryImplTest {

    private BikeRepository underTest;

    @Before
    public void setUp(){
        underTest = new BikeRepositoryImpl();
    }

    @Test
    public void readFromFile_shouldReturnTrueWhenFileExist() throws FileNotFoundException {
        String fileName = "BikeRepo.txt";
        Assert.assertEquals(true,underTest.readFromFile(fileName));
    }

    @Test(expected = FileNotFoundException.class)
    public void readFromFile_shouldThrowFileNotFoundException() throws FileNotFoundException {
        String fileName = "Dummy.txt";
        Assert.assertEquals(true,underTest.readFromFile(fileName));
    }

    @Test
    public void getBike_shouldReturnBikeFromRepository() {
        Bike bike = new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10,"City Bike","1");
        Assert.assertEquals(bike,underTest.getBike(bike));
    }

    @Test
    public void getAllBikes_shouldReturnListOfBikes() throws FileNotFoundException {
        List<Bike> listOfBikes = underTest.getAllBikes();
        Assert.assertEquals(listOfBikes,underTest.getAllBikes());
    }

}