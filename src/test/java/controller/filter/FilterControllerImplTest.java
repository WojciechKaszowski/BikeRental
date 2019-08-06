package controller.filter;

import controller.filter.comparators.ColorComparator;
import model.bike.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class FilterControllerImplTest {

    @Mock
    private BikeRepository bikeRepository;

    private FilterController underTest;

    @Before
    public void setUp(){
        Mockito.when(bikeRepository.getAllBikes()).thenReturn(preparedListOfBikes());
        underTest = new FilterControllerImpl(bikeRepository);
    }


    @Test
    public void filterByColor_shouldReturnTrueWhenThereIsAtLeastOneBikeWithThisColorOnTheList(){

        String color = "blue";
        Assert.assertTrue(underTest.filterByColor(color));
    }

    @Test
    public void filterByColor_shouldReturnFalseWhenThereAreNoBikesWithThisColorOnTheList(){

        String color = "purple";
        Assert.assertFalse(underTest.filterByColor(color));
    }

    @Test
    public void filterByBrand_shouldReturnTrueWhenThereIsAtLeastOneBikeWithThisBrandOnTheList(){

        String brand = "kross";
        Assert.assertTrue(underTest.filterByBrand(brand));
    }

    @Test
    public void filterByBrand_shouldReturnFalseWhenThereAreNoBikesWithThisBrandOnTheList(){

        String brand = "audi";
        Assert.assertFalse(underTest.filterByBrand(brand));
    }

    @Test
    public void filterByStatus_shouldReturnTrueWhenThereIsAtLeastOneBikeWithThisRentalStatusOnTheList(){

        String rentalStatus = "free";
        Assert.assertTrue(underTest.filterByStatus(rentalStatus));
    }

    @Test
    public void filterByStatus_shouldReturnFalseWhenThereAreNoBikesWithThisRentalStatusOnTheList(){

        String rentalStatus = "destroyed";
        Assert.assertFalse(underTest.filterByStatus(rentalStatus));
    }

    @Test
    public void filterByPrice_shouldReturnTrueWhenThereIsAtLeastOneBikeWithThisPriceOnTheList(){

        String price = "10";
        Assert.assertTrue(underTest.filterByPrice(price));
    }

    @Test
    public void filterByPrice_shouldReturnFalseWhenThereAreNoBikesWithThisPriceOnTheList(){

        String price = "-100";
        Assert.assertFalse(underTest.filterByPrice(price));
    }

    @Test
    public void getActualList_shouldReturnActualListOfBikes() {
        Assert.assertEquals(5,underTest.getActualList().size());
    }

    @Test
    public void filterList_shouldReturnListFilteredByGivenColour() {
        underTest.filterByColor("black");
        List<Bike> listOfBikesFilteredByBlack = underTest.getActualList();
        Assert.assertEquals(preparedListOfBikesFilteredByBlackColour(),listOfBikesFilteredByBlack);

    }

    private List<Bike> preparedListOfBikes() {
        List<Bike> bikes = new ArrayList<>();

        bikes.add(new Bike(Brand.KROSS, BikeColor.BLUE, RentalStatus.FREE,10,"City Bike","1"));
        bikes.add(new Bike(Brand.CANNONDALE,BikeColor.WHITE,RentalStatus.FREE,8,"Road Bike","2"));
        bikes.add(new Bike(Brand.SCOTT,BikeColor.BLACK,RentalStatus.FREE,12,"Road Bike","3"));
        bikes.add(new Bike(Brand.GIANT,BikeColor.YELLOW,RentalStatus.OCCUPIED,6,"MTB Bike","4"));
        bikes.add(new Bike(Brand.TREK,BikeColor.BLACK,RentalStatus.OCCUPIED,16,"Road Bike","5"));

        return bikes;
    }

    private List<Bike> preparedListOfBikesFilteredByBlackColour() {
        return preparedListOfBikes().stream()
                .sorted(new ColorComparator(BikeColor.BLACK))
                .collect(Collectors.toList());
    }
}