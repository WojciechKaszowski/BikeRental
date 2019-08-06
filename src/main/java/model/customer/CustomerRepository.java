package model.customer;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CustomerRepository {

    Customer getCustomer(Customer customer);
    List<Customer> getAllCustomers();
    boolean readFromFile(String fileName) throws IOException;

}
