package controller.customer;

import model.customer.Customer;

public interface CustomersController {

    boolean addMoneyToWallet(double addMoney);
    double takeRentalPriceFromWallet(double price);
    boolean isWalletStatusOk();
    boolean validateCustomerByName(String name);
    void addCustomer(String name, double money);
    double getWallet();
    void setWallet(double money);
    Customer getCustomer();


}
