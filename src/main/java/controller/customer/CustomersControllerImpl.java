package controller.customer;

import model.customer.Customer;
import model.customer.CustomerRepository;

import java.util.List;

public class CustomersControllerImpl  implements CustomersController{

    private Customer customer;

    private CustomerRepository customerRepository;

    public CustomersControllerImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean validateCustomerByName(String name) {
        List<Customer> customers = customerRepository.getAllCustomers();
        for(Customer customer : customers) {
            if(customer.getCustomerName().equals(name)) {
                this.customer = customer;
                return true;
            }
        }
        return false;
    }

    @Override
    public void addCustomer(String name, double money) {
        customer = new Customer(name,money);

    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean addMoneyToWallet(double addMoney) {
        if (addMoney <= 0) {
            System.out.println("Wrong data. Try again");
            return false;
        }
        double moneyToAdd = getWallet() + addMoney;
        setWallet(moneyToAdd);
        return true;
    }

    @Override
    public double takeRentalPriceFromWallet(double price) {
        return getWallet() - price;
    }

    @Override
    public boolean isWalletStatusOk() {
        if(getWallet() >= 0) {
            return true;
        }
        System.out.println("Your wallet is to low, you need to add some money");
        return false;
    }

    @Override
    public double getWallet() {
        return customer.getWallet();
    }

    @Override
    public void setWallet(double money) {
        customer.setWallet(money);
    }
}
