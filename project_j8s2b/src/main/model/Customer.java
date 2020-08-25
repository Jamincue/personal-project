package model;

import model.exception.InvalidAmountException;
import model.exception.LessBalanceException;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Customer implements People, Saveable {
    private static int nextCustomerId = 1;
    private int id;
    private String name;
    private double balance;

    // delete or rename this class!

    //REQUIRES: non-zero length of name, initial balance > 0
    //EFFECT: construct a customer

    public Customer(String accountName, double initialBalance) {
        id = nextCustomerId++;
        name = accountName;
        if (initialBalance >= 0) {
            balance = initialBalance;
        } else {
            balance = 0;
        }
    }

    public Customer(int nextId, int id, String name, double balance) {
        nextCustomerId = nextId;
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }


    @Override
    public String getName() {
        return name;
    }


    public double deposit(double amount) throws InvalidAmountException {
        if (amount >= 0) {
            balance += amount;
            return balance;
        } else {
            throw new InvalidAmountException("Sorry, the amount you add is invalid");
        }
    }

    public double withdraw(double amount) throws LessBalanceException {
        if (balance >= amount) {
            balance -= amount;
            return balance;
        } else {
            throw new LessBalanceException("Sorry, the balance is not enough");
        }

    }

    public boolean canWithdraw(double amount) {
        if (balance < amount) {
            return false;
        } else {
            return true;
        }
    }


    public double getBalance() {
        return balance;
    }

    /*
     * EFFECTS: returns a string representation of customer
     */
    @Override
    public String toString() {
        String balanceString = String.format("%.2f", balance);  // get balance to 2 decimal places as a string
        return "[ id = " + id + ", name = " + name + ", "
                + "balance = $" + balanceString + "]";
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(nextCustomerId);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(id);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(name);
        printWriter.print(Reader.DELIMITER);
        printWriter.println(balance);
    }

}

