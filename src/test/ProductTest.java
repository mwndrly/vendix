package test;
import models.Product;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void testProductConstructorAndGetters() {
        Product product = new Product("Produto 1", 100.0, 10);

        assertEquals("Produto 1", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals(10, product.getQuantity());
    }
}
