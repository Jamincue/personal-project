package persistence;

import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {

    private static final String TEST_FILE = "./data/testCustomers.txt";
    private Writer testWriter;
    private Customer lady;
    private Customer men;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        lady = new Customer("Sandy", 220.00);
        men = new Customer("J", 350.00);
    }

    @Test
    void testWriteAccounts() {
        // save chequing and savings accounts to file
        testWriter.write(lady);
        testWriter.write(men);
        testWriter.close();

        // now read them back in and verify that the accounts have the expected values
        try {
            List<Customer> customers = Reader.readCustomers(new File(TEST_FILE));
            Customer lady = customers.get(0);
            assertEquals(1, lady.getId());
            assertEquals("Sandy", lady.getName());
            assertEquals(220.00, lady.getBalance());

            Customer men = customers.get(1);
            assertEquals(2, men.getId());
            assertEquals("J", men.getName());
            assertEquals(350.00, men.getBalance());

            // verify that ID of next account created is 3 (checks that nextAccountId was restored)
            Customer next = new Customer("Cindy", 0.00);
            assertEquals(3, next.getId());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
