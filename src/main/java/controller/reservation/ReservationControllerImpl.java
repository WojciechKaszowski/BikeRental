package controller.reservation;

import model.bike.Bike;
import model.bike.BikeRepository;
import model.bike.RentalStatus;
import model.customer.Customer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationControllerImpl implements ReservationController {

    private BikeRepository bikeRepository;
    private List<Bike> listOfReservedBikes;

    public ReservationControllerImpl(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
        listOfReservedBikes = new ArrayList<>();
    }

    @Override
    public List<Bike> getListOfAllReservedBikes() {
        return listOfReservedBikes;
    }

    @Override
    public boolean bookBike(int bikeNr, List<Bike> actualListOfBikes) {
        if(bikeNr < 1 || bikeNr > actualListOfBikes.size()) {
            System.out.println("Wrong number. Try again");
            return false;
        }
        Bike bikeToBook = actualListOfBikes.get(bikeNr-1);
        if(makeReservation(bikeToBook))  {
            listOfReservedBikes.add(bikeToBook);
            System.out.println("Bike: " + bikeToBook + " was added to reserved list");
        } else {
            System.out.println("Cannot add bike to the list of reserved bikes");
            return false;
        }
        bikeToBook.setBookTime(LocalTime.now());
        return true;
    }

    @Override
    public Bike returnBike(int bikeToReturn) {
        Bike bike = listOfReservedBikes.get(bikeToReturn);
        bikeRepository.getBike(bike).setRentalStatus(RentalStatus.FREE);
        listOfReservedBikes.remove(bike);
        return bike;
    }

    @Override
    public List<Bike> setReservedBikesList(Customer customer) {
        List<Bike> bikes = bikeRepository.getAllBikes();
        List<Bike> customerBikes = new ArrayList<>();
        for(Bike bike : bikes) {
            if(customer.getBikes().contains(bike.getID())) {
                customerBikes.add(bike);
            }
        }
        if(customerBikes.size() == 0) {
            listOfReservedBikes = new ArrayList<>();
        }
        listOfReservedBikes = customerBikes;
        return customerBikes;
    }

    private boolean makeReservation(Bike bike) {
        if(isReservationPossible(bike)) {
            System.out.println("You booked: " + bike);
            bikeRepository.getBike(bike).setRentalStatus(RentalStatus.OCCUPIED);
            return true;
        }
        return false;
    }

    private boolean isReservationPossible(Bike bike) {
        RentalStatus rentalStatus = bikeRepository.getBike(bike).getRentalStatus();
        if(rentalStatus.equals(RentalStatus.OCCUPIED)) {
            System.out.println("Bike is already occupied. Reservation is not possible");
            return false;
        }
        return true;
    }


}
