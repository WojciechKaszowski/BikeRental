package controller.scanner;

import java.util.Scanner;

public class ScannerControllerImpl implements ScannerController {

    @Override
    public String next() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }
}
