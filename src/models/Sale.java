package models;

import java.io.Serializable;

public class Sale implements Serializable {
	private Client client;
	private Product product;
	private int quantity;

	public Sale(Client client, Product product, int quantity) {
		this.client = client;
		this.product = product;
		this.quantity = quantity;
	}

	public Client getClient() { return client; }
	public Product getProduct() { return product; }
	public int getQuantity() { return quantity; }
}
