package model;

import model.exception.InvalidAmountException;
import model.exception.LessBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Customer c1;
    private Customer c2;
    private Customer c3;

    @BeforeEach
    void runBefore() {
        c1 = new Customer("Jack", 0);
        c2 = new Customer("Sam", 200.00);
        c3 = new Customer("Mary", 150.00);
    }

    @Test
    void testConstructor() {
        assertEquals("Jack", c1.getName());
        assertEquals(0, c1.getBalance());
        assertEquals("Sam", c2.getName());
        assertEquals(200.00, c2.getBalance());
    }

    @Test
    void testDepositOnce() {
        try {
            c1.deposit(30.00);
            assertEquals(30.00, c1.getBalance());
            c3.deposit(50.00);
            assertEquals(200.00, c3.getBalance());
        } catch (InvalidAmountException e) {
            fail();
        }
    }

    @Test
    void testWithdrawOnce() {
        try {
            c2.deposit(100.00);
            assertEquals(300.00, c2.getBalance());
        } catch (InvalidAmountException e) {
            fail();
        }

        try {
            c2.withdraw(100.00);
        } catch (LessBalanceException e) {
            fail();
        }

    }

    @Test
    void testDepositMultipleTimes() {
        try {
            c3.deposit(40);
            c3.deposit(10);
            assertEquals(200.00, c3.getBalance());
            c1.deposit(20);
            c1.deposit(30);
            assertEquals(50.00, c1.getBalance());
        } catch (InvalidAmountException e) {
            fail();
        }
        try {
            c2.deposit(-20);
            fail();
        } catch (InvalidAmountException e) {
            //expected
        }
    }



    @Test
    void testWithdrawMultipleTimes() {
        try {
            c2.deposit(50.00);
            assertEquals(250.00, c2.getBalance());
        } catch (InvalidAmountException e) {
            fail();
        }
        try {
            c2.withdraw(20.00);
            c2.withdraw(30.00);
            assertEquals(200.00, c2.getBalance());
        } catch (LessBalanceException e) {
            fail();
        }
        try {
            c2.withdraw(500.00);
            fail();
        } catch (LessBalanceException e) {
            //expected
        }
    }

    @Test
    void testCanWithdraw() {
        assertFalse(c2.canWithdraw(300.00));
        assertTrue(c3.canWithdraw(50.00));
    }

    // delete or rename this class!
}