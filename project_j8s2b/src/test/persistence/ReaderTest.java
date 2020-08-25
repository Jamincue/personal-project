package persistence;

import model.Customer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    @Test
    void testParseAccountsFile1() {
        try {
            List<Customer> customers = Reader.readCustomers(new File("./data/testCustomerFile1.txt"));
            Customer lady = customers.get(0);
            assertEquals(42, lady.getId());
            assertEquals("Sandy", lady.getName());
            assertEquals(134.56, lady.getBalance());

            Customer men = customers.get(1);
            assertEquals(43, men.getId());
            assertEquals("Alex", men.getName());
            assertEquals(66.78, men.getBalance());

            // check that nextAccountId has been set correctly
            Customer nextCustomer = new Customer("Cindy", 70.00);
            assertEquals(44, nextCustomer.getId());
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testParseAccountsFile2() {
        try {
            List<Customer> customers = Reader.readCustomers(new File("./data/testCustomerFile2.txt"));
            Customer lady = customers.get(0);
            assertEquals(1, lady.getId());
            assertEquals("J", lady.getName());
            assertEquals(45.78, lady.getBalance());

            Customer men = customers.get(1);
            assertEquals(2, men.getId());
            assertEquals("P", men.getName());
            assertEquals(76.22, men.getBalance());

            // check that nextAccountId has been set correctly
            Customer nextCustomer = new Customer("Cindy",70.00);
            assertEquals(3, nextCustomer.getId());
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readCustomers(new File("./path/does/not/exist/testCustomer.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
