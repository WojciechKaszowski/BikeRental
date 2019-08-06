package model.bike;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BikeRepositoryImpl implements BikeRepository {

    private List<Bike> bikes;

    public BikeRepositoryImpl() {
        initRepository();
    }

    @Override
    public boolean readFromFile(String fileName) throws FileNotFoundException {
        bikes = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] str = line.split("/");
                Bike bike = new Bike(Brand.valueOf(str[0]), BikeColor.valueOf(str[1]), RentalStatus.valueOf(str[2]),Integer.parseInt(str[3]),str[4],str[5]);
                bikes.add(bike);
            }
            return true;
        } catch (Exception e) {
            throw new FileNotFoundException("Cannot find file ");
        }
    }

    @Override
    public Bike getBike(Bike bike) {
            int bikeIndex = bikes.indexOf(bike);
            return bikes.get(bikeIndex);
    }

    @Override
    public List<Bike> getAllBikes() {
        return bikes;
    }

    private void initRepository() {
        try {
            readFromFile("BikeRepo.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
