package model.bike;

import java.io.FileNotFoundException;
import java.util.List;

public interface BikeRepository {

    Bike getBike(Bike bike);
    List<Bike> getAllBikes();
    boolean readFromFile(String fileName) throws FileNotFoundException;

}
