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

    @Test
    public void testProductSetters() {
        Product product = new Product("Produto 1", 100.0, 10);

        product.setName("Produto Editado");
        product.setPrice(150.5);
        product.setQuantity(20);

        assertEquals("Produto Editado", product.getName());
        assertEquals(150.5, product.getPrice());
        assertEquals(20, product.getQuantity());
    }
}
