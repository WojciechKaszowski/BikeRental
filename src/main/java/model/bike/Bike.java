package model.bike;


import java.time.LocalTime;
import java.util.Objects;

public class Bike {

    private final String ID;
    private Brand brand;
    private BikeColor color;
    private RentalStatus rentalStatus;
    private double pricePerHour;
    private String description;
    private LocalTime bookTime;

    public Bike(Brand brand, BikeColor color, RentalStatus rentalStatus, double pricePerHour, String description, String ID) {
        this.brand = brand;
        this.color = color;
        this.rentalStatus = rentalStatus;
        this.pricePerHour = pricePerHour;
        this.description = description;
        this.ID = ID;
        setUpTime();
    }

    public Brand getBrand() {
        return brand;
    }

    public BikeColor getColor() {
        return color;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public LocalTime getBookTime() {
        return bookTime;
    }

    public String getID() {
        return ID;
    }

    public void setBookTime(LocalTime bookTime) {
        this.bookTime = bookTime;
    }

    private void setUpTime() {
        if(rentalStatus.equals(RentalStatus.OCCUPIED)) {
            bookTime = LocalTime.now();
        }
    }

    @Override
    public String toString() {
        return "Bike -> " +
                " Brand: " + brand +
                " Color: " + color +
                " Status: " + rentalStatus +
                " Rental price per hour: " + pricePerHour +
                " Description: " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Double.compare(bike.pricePerHour, pricePerHour) == 0 &&
                Objects.equals(ID, bike.ID) &&
                brand == bike.brand &&
                color == bike.color &&
                rentalStatus == bike.rentalStatus &&
                Objects.equals(description, bike.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, brand, color, rentalStatus, pricePerHour, description);
    }
}
