package ui;

import model.Customer;
import model.exception.InvalidAmountException;
import model.exception.LessBalanceException;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new ShoppingApp();

    }

    static class ShoppingApp {
        // made a shoppingApp
        private static final String CUSTOMERS_FILE = "./data/customer.txt";

        private Customer lady;
        private Customer men;
        private Scanner input;

        //EFFECT: run the shopping app
        public ShoppingApp() {
            runShopping();
        }

        //MODIFIES:
        //EFFECT: construct the basic structure of the app
        private void runShopping() {
            boolean willContinue = true;
            String command = null;
            input = new Scanner(System.in);
            loadCustomers();

            while (willContinue) {
                displayBoard();
                command = input.next();
                command = command.toLowerCase();

                if (command.equals("s")) {
                    willContinue = false;

                } else {
                    processCommand(command);
                }
            }

            System.out.println("\nThank You For Your Shopping!");
        }

        // MODIFIES: this
        // EFFECTS: loads accounts from CUSTOMER_FILE, if that file exists;
        // otherwise initializes customers with default values
        private void loadCustomers() {
            try {
                List<Customer> customers = Reader.readCustomers(new File(CUSTOMERS_FILE));
                lady = customers.get(0);
                men = customers.get(1);

            } catch (IOException e) {
                init();
            }
        }

        // EFFECTS: saves state of customer's accounts to CUSTOMER_FILE
        private void saveCustomer() {
            try {
                Writer writer = new Writer(new File(CUSTOMERS_FILE));
                writer.write(lady);
                writer.write(men);
                writer.close();
                System.out.println("Customer's info has saved to file " + CUSTOMERS_FILE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to save accounts to " + CUSTOMERS_FILE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                // this is due to a programming error
            }
        }


        //MODIFIES: this
        //EFFECT: process user's command

        @SuppressWarnings("checkstyle:WhitespaceAround")
        private void processCommand(String command) {
            if (command.equals("add")) {
                addAmount();
            } else if (command.equals("w")) {
                doWithdrawal();
            } else if (command.equals("save")) {
                saveCustomer();
            } else if (command.equals("p")) {
                printBill();
            } else if (command.equals(" .")) {
                addCustomer();
            } else {
                System.out.println("Your Command Is Not Valid!");
            }
        }

        private void init() {
            lady = new Customer("Mary", 200);
            men = new Customer("Jack", 350);
        }


        //EFFECT: displays the options for customer
        private void displayBoard() {
            System.out.println("\nSelect from: ");
            System.out.println("\tadd -> add the amount to your balance ");
            System.out.println("\tw -> withdrawal the amount ");
            System.out.println("\tsave -> save customer info to the file");
            System.out.println("\tp -> print your bill");
            System.out.println("\ts -> cancel my request");
        }

        //MODIFIES: this
        //EFFECT: add the amount to the balance
        private void addAmount() {
            Customer shopper = selectShopper();
            System.out.println("Enter the amount you want to add:  $");
            double amount = input.nextDouble();
            try {
                shopper.deposit(amount);
            } catch (InvalidAmountException e) {
                System.out.println("Sorry, the amount you add is invalid");
            }
            printBalance(shopper);
        }


        //MODIFIES: this
        //EFFECT: withdrawal a certain amount;
        private void doWithdrawal() {
            Customer shopper = selectShopper();
            System.out.println("Enter the amount you want to withdrawal:  $");
            double amount = input.nextDouble();

            try {
                shopper.withdraw(amount);
            } catch (LessBalanceException e) {
                System.out.println("Sorry, the balance is not enough");
            }
            printBalance(shopper);
        }

        private void addCustomer() {
            List<Customer> customers = new ArrayList<>();
            Customer selectedShopper = selectShopper();
            customers.add(selectedShopper);
        }

        private Customer selectShopper() {
            String selection = "";  // force entry into loop

            selectSex();

            if (selection.equals("l")) {
                return lady;
            } else {
                return men;
            }

        }

        private void selectSex() {
            String selection = "";
            while (!(selection.equals("l") || selection.equals("m"))) {
                System.out.println("l for lady");
                System.out.println("m for men");
                selection = input.next();
                selection = selection.toLowerCase();
            }
        }


        //EFFECT: print the bill that customer made
        private void printBill() {
            Customer shopper = selectShopper();
            System.out.println("Hi, Customer: " + shopper.getName());
            System.out.println("Your Id: " + shopper.getId());
            printBalance(shopper);
        }


        private void printBalance(Customer selected) {
            System.out.printf("Balance: $%.2f\n", selected.getBalance());
        }
    }
}
