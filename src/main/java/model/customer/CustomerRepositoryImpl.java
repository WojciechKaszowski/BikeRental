package model.customer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository{

    private List<Customer> customers = new ArrayList<>();

    public CustomerRepositoryImpl() {
        initRepository();
    }

    @Override
    public boolean readFromFile(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());

        Customer[] customerArray  = mapper.readValue(file, Customer[].class);

        customers.addAll(Arrays.asList(customerArray));

        return true;
    }

    @Override
    public Customer getCustomer(Customer customer) {
            int customerIndex = customers.indexOf(customer);
            return customers.get(customerIndex);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    private void initRepository() {
        try {
            readFromFile("CustomerRepo.txt");
        } catch (IOException e) {
            System.out.println("Cannot initialize repository");
            e.printStackTrace();
        }
    }
}


