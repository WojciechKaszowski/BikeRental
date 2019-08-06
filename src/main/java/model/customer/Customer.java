package model.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {

    private String customerName;
    private double wallet;
    private List<String> bikes;

    public Customer() {
    }

    public Customer(String customerName, double wallet) {
        this.customerName = customerName;
        this.wallet = wallet;
        this.bikes = new ArrayList<>();
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public List<String> getBikes() {
        return bikes;
    }

    public void setBikes(List<String> bikes) {
        this.bikes = bikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Double.compare(customer.wallet, wallet) == 0 &&
                Objects.equals(customerName, customer.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, wallet);
    }
}
