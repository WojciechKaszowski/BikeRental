package controller.customer;

import model.customer.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomersControllerImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomersController underTest;


    @Before
    public void setUp(){
        underTest = new CustomersControllerImpl(customerRepository);
        underTest.addCustomer("Adam",100);
    }

    @Test
    public void addCustomer() {

        Assert.assertEquals("Adam",underTest.getCustomer().getCustomerName());
        Assert.assertEquals(100,underTest.getCustomer().getWallet(),0);
    }


    @Test
    public void addMoneyToWallet_shouldReturnFalseWhenNegativeNumber() {

        double moneyToAdd = -10.0;

        Assert.assertFalse(underTest.addMoneyToWallet(moneyToAdd));
    }

    @Test
    public void addMoneyToWallet_shouldReturnTrueWhenNumberIsPositive() {


        double moneyToAdd = 10.0;

        Assert.assertTrue(underTest.addMoneyToWallet(moneyToAdd));
    }

    @Test
    public void takeRentalPriceFromWallet_shouldReturnWalletMinusValue() {

        underTest.addCustomer("Adam",100);
        double walletStatus = underTest.getWallet();
        double value = 10.0;

        Assert.assertEquals(walletStatus-value,underTest.takeRentalPriceFromWallet(value),0);
    }

    @Test
    public void isWalletStatusOk_shouldReturnTrueWhenWalletValueIsMoreOrEqual0() {
        underTest.addMoneyToWallet(100.0);
        Assert.assertTrue(underTest.isWalletStatusOk());
    }

}