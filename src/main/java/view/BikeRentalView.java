package view;


import controller.BikeRentalController;
import controller.BikeRentalControllerImpl;
import controller.filter.FilterType;
import controller.scanner.ScannerController;
import controller.scanner.ScannerControllerImpl;
import model.bike.Bike;

import java.util.List;


public class BikeRentalView {

    private BikeRentalController bikeRental;
    private ScannerController scannerController;
    private final String WRONG_INPUT = "\nWrong data. Please try again\n";

    public BikeRentalView() {
        this.bikeRental = new BikeRentalControllerImpl();
        this.scannerController = new ScannerControllerImpl();
    }

    public void start() {
        printMainMenu();
    }

    private void printMainMenu() {
        getCustomer();
        String option = "0";

        printMainMenuHeading();

        while (!option.equals("4")) {

            option = scannerController.next();
            switch (option) {
                case "1": {
                    bikesMenu();
                    printMainMenuHeading();
                    break;
                }
                case "2": {
                    returnBikeMenu();
                    printMainMenuHeading();
                    break;
                }
                case "3": {
                    walletMenu();
                    printMainMenuHeading();
                    break;
                }
                case "4": {
                    if (!bikeRental.isExitPossible()) {
                        option = "0";
                        printMainMenuHeading();
                    }
                    break;
                }
                default: {
                    System.out.println(WRONG_INPUT);
                }
            }
        }
    }

    private void printMainMenuHeading() {
        System.out.println("\n Welcome in our Bike Rental");
        System.out.println("Choose option by selecting number: ");
        System.out.println("1. Our bikes list\n" +
                "2. Bikes reserved by you\n" +
                "3. Your wallet\n" +
                "4. Exit\n");
    }

    private void getCustomer() {
        String name = getCustomerName();
        if(!bikeRental.checkIfCustomerExist(name)) {
            double money = getCustomerWallet();
            bikeRental.addNewCustomer(name,money);
        }
        bikeRental.setUpListOfReservedBikes();
    }

    private String getCustomerName() {
        String userName;
        while(true) {
            System.out.println("Please enter your UserName: ");
            userName = scannerController.next();
            if(userName.length() > 0) {
                return userName;
            }
            System.out.println(WRONG_INPUT);
        }
    }

    private double getCustomerWallet() {
        while (true) {
            System.out.println("Please enter the value of your wallet");
            double value;
            try {
                value = Double.parseDouble(scannerController.next());
                if (value > 0) {
                    return value;
                }
                System.out.println(WRONG_INPUT);
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private void bikesMenu() {
        String option = "0";
        while (!option.equals("3")) {

            System.out.println();
            if (!printList(bikeRental.getListOfBikesAvailableForCustomer())) {
                System.out.println("Add money to wallet to see list of bikes");
                break;
            }

            System.out.println("\nChoose option by selecting number: ");
            System.out.println("1. Book a bike\n" +
                    "2. Filter bikes list\n" +
                    "3. Return\n");

            option = scannerController.next();
            switch (option) {
                case "1": {
                    printBookBikeMenu();
                    option = "3";
                    break;
                }
                case "2": {
                    filterBikesMenu();
                    break;
                }
                case "3": {
                    break;
                }
                default: {
                    System.out.println(WRONG_INPUT);
                    option = "3";
                }
            }
        }
    }

    private void filterBikesMenu() {

        String option = "0";
        while (option.equals("0")) {
            System.out.println("\nChoose a filter: ");
            System.out.println("1. Filter by color\n" +
                    "2. Filter by brand\n" +
                    "3. Filter by status\n" +
                    "4. Filter by price\n" +
                    "5. Return\n");
            option = scannerController.next();
            switch (option) {
                case "1": {
                    if(!passValueToFilter(FilterType.COLOR)) option = "0";
                    break;
                }
                case "2": {
                    if(!passValueToFilter(FilterType.BRAND)) option = "0";
                    break;
                }
                case "3": {
                    if(!passValueToFilter(FilterType.RENTAL_STATUS)) option = "0";
                    break;
                }
                case "4": {
                    if(!passValueToFilter(FilterType.PRICE)) option = "0";
                    break;
                }
                case "5": {
                    return;
                }
                default: {
                    System.out.println(WRONG_INPUT);
                    option = "0";
                    break;
                }
            }
        }
    }

    private boolean passValueToFilter(FilterType filterType) {
        System.out.println("Hint! You can filter only on values currently available on the list.");
        System.out.println("Input value: ");
        String value = scannerController.next();
        if(bikeRental.isFilterPossible(filterType,value)) {
            if(!bikeRental.filter(filterType, value)) {
                System.out.println(WRONG_INPUT);
                return false;
            }
            return true;
        }
        System.out.println(WRONG_INPUT);
        return false;
    }

    private void returnBikeMenu() {
        String option = "0";

        while (!option.equals("2")) {

            if(!printList(bikeRental.getListOfReservedBikes())) {
                System.out.println("You currently do not have reserved bikes");
                break;
            }

            System.out.println("\nChoose option by selecting number: ");
            System.out.println("1. Return bike\n" +
                    "2. Return\n");
            option = scannerController.next();
            switch (option) {
                case "1": {
                    if(!bikeRental.returnBike(bikeToReturn())) {
                        System.out.println(WRONG_INPUT);
                    }
                    option = "2";
                    break;
                }
                case "2": {
                    break;
                }
                default: {
                    System.out.println(WRONG_INPUT);
                    option = "2";
                }
            }
        }
    }

    private void walletMenu() {
        String option = "0";
        while (!option.equals("2")) {
            System.out.println("\nActual wallet status: " + bikeRental.getWallet());
            System.out.println("Choose option by selecting number: ");
            System.out.println("1. Add money to wallet\n" +
                    "2. Return\n");

            option = scannerController.next();
            switch (option) {
                case "1": {
                    bikeRental.addMoneyToWallet(addMoney());
                    break;
                }
                case "2": {
                    break;
                }
                default: {
                    System.out.println(WRONG_INPUT);
                }
            }
        }
    }

    private boolean printList(List<Bike> list) {
        if (list.size() == 0) {
            System.out.println("List is empty");
            return false;
        }
        System.out.println("List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i));
        }
        return true;
    }

    private boolean printBookBikeMenu() {
        System.out.println("Input bike number from list to reserve: ");
        try {
            int bikeNumber = Integer.parseInt(scannerController.next());
            return bikeRental.bookABike(bikeNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(WRONG_INPUT);
            return false;
        }
    }

    private int bikeToReturn() {
        System.out.println("Input number of bike you want to return");
        try {
            return Integer.parseInt(scannerController.next());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double addMoney() {
        System.out.println("Input value of money you want to add to the wallet");
        try {
            return Double.parseDouble(scannerController.next());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
