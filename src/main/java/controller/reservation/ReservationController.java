package controller.reservation;

import model.bike.Bike;
import model.customer.Customer;

import java.util.List;

public interface ReservationController {

    List<Bike> getListOfAllReservedBikes();
    boolean bookBike(int bikeNr, List<Bike> actualListOfBikes);
    Bike returnBike(int bikeToReturn);
    List<Bike> setReservedBikesList(Customer customer);

}
