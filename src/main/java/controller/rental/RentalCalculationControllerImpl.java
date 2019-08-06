package controller.rental;

import model.bike.Bike;

import java.time.LocalTime;

public class RentalCalculationControllerImpl implements RentalCalculationController{


    @Override
    public double calculateMoneyForReturn(Bike bike) {

        return calculateRentalPrice(bike);
    }


    private double calculateRentalPrice(Bike bike) {
        LocalTime returnTime = LocalTime.now();
        int rentTimeInSeconds = calculateTimeInSeconds(bike.getBookTime());
        int returnTimeInSeconds = calculateTimeInSeconds(returnTime);

        double pricePerHour = bike.getPricePerHour();
        final int FIRST_HOUR = 10;

        int rentTime = ((returnTimeInSeconds - rentTimeInSeconds) + FIRST_HOUR) / 10;
        return rentTime * pricePerHour;

    }

    private int calculateTimeInSeconds(LocalTime time) {

        int hours = time.getHour();
        int minutes = time.getMinute();
        int seconds = time.getSecond();

        return hours * 3600 + minutes * 60 + seconds;
    }


}