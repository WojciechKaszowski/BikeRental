package model.customer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImplTest {

    private CustomerRepository underTest;

    @Before
    public void setUp(){
        underTest = new CustomerRepositoryImpl();
    }

    @Test
    public void readFromFile_shouldReturnTrueWhenFileExist() throws IOException {
        String fileName = "CustomerRepo.txt";
        Assert.assertTrue(underTest.readFromFile(fileName));
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_shouldThrowFileNotFoundException() throws NullPointerException, IOException {
        String fileName = "Dummy.txt";
        Assert.assertTrue(underTest.readFromFile(fileName));
    }

    @Test
    public void getCustomer_shouldReturnCustomerFromRepository() {
        Customer customer = new Customer("John",1000);
        Assert.assertEquals(customer,underTest.getCustomer(customer));
    }

    @Test
    public void getAllCustomers_shouldReturnListOfCustomers() throws FileNotFoundException {
        Assert.assertEquals(customersList(),underTest.getAllCustomers());
    }

    private List<Customer> customersList() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John",1000));
        customers.add(new Customer("Will",300));
        return customers;
    }


}