package com.price.engine;
import java.util.Map;

public class Competitor {

	private String name;

	private Map<String, Product> products;

	public Map<String, Product> getProducts() {
		return products;
	}

	public void setProducts(Map<String, Product> products) {
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
