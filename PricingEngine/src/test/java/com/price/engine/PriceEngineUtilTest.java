package com.price.engine;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

public class PriceEngineUtilTest {

	@Test
	public void testCreateProduct() {
		String[] competitordetails = "ssd W 11.0".split(" ");
		Product product = PriceEngineUtil.createProduct(competitordetails);
		assertEquals(product.getName(), "ssd");
	}

	@Test
	public void testCreateCompetitors() throws URISyntaxException, IOException {
		Path path = Paths.get(PriceEngineApplication.class.getClassLoader().getResource("input2.txt").toURI());
		final StringBuilder inputBuilder = new StringBuilder();
		Stream<String> lines = Files.lines(path);
		lines.forEach(line -> inputBuilder.append(line).append("\n"));
		lines.close();
		String[] inputdata = inputBuilder.toString().split("\n");
		int nop = Integer.parseInt(inputdata[0]);
		Map<String, Competitor> competitorMap = PriceEngineUtil.createCompetitors(inputdata, nop);
		assertEquals(competitorMap.get("X").getName(), "X");
	}

	@Test
	public void testCreateProducts() throws URISyntaxException, IOException {
		Path path = Paths.get(PriceEngineApplication.class.getClassLoader().getResource("input2.txt").toURI());
		final StringBuilder inputBuilder = new StringBuilder();
		Stream<String> lines = Files.lines(path);
		lines.forEach(line -> inputBuilder.append(line).append("\n"));
		lines.close();
		String[] inputdata = inputBuilder.toString().split("\n");
		int nop = Integer.parseInt(inputdata[0]);
		List<Product> productlist = PriceEngineUtil.createProducts(inputdata, nop);
		assertEquals(productlist.size(), 2);
	}

	@Test
	public void testGetMaxFequency() {
		Map<Double, Integer> priceCountMap = new HashMap<Double, Integer>();
		priceCountMap.put(11.0, 2);
		priceCountMap.put(12.0, 2);
		int count = PriceEngineUtil.getMaxFequency(priceCountMap);
		assertEquals(count, 2);
	}

	@Test
	public void testGetRecommendedPrice() throws URISyntaxException, IOException {
		Path path = Paths.get(PriceEngineApplication.class.getClassLoader().getResource("input2.txt").toURI());
		final StringBuilder inputBuilder = new StringBuilder();
		Stream<String> lines = Files.lines(path);
		lines.forEach(line -> inputBuilder.append(line).append("\n"));
		lines.close();
		String[] inputdata = inputBuilder.toString().split("\n");
		int nop = Integer.parseInt(inputdata[0]);
		Map<String, Competitor> competitorMap = PriceEngineUtil.createCompetitors(inputdata, nop);
		Product product = new Product();
		product.setDemand("L");
		product.setSupply("H");
		product.setName("ssd");
		product.setPrice(11.0);
		double price = PriceEngineUtil.getRecommendedPrice(competitorMap, product);
		assertEquals(price, 11.0, 0.001);
	}

}
