package com.price.engine;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PriceEngineUtil.class)
public class PriceEngineApplicationTest {

	@Before
	public void setUp() {
		PowerMockito.mockStatic(PriceEngineUtil.class);
	}

	@Test
	public void testMain() throws URISyntaxException, IOException {

		Path path = Paths.get(PriceEngineApplication.class.getClassLoader().getResource("input2.txt").toURI());
		final StringBuilder inputBuilder = new StringBuilder();
		Stream<String> lines = Files.lines(path);
		lines.forEach(line -> inputBuilder.append(line).append("\n"));
		lines.close();
		String[] inputdata = inputBuilder.toString().split("\n");
		int nop = Integer.parseInt(inputdata[0]);
		List<Product> productlist = new ArrayList<Product>();
		Product product = new Product();
		product.setDemand("L");
		product.setSupply("H");
		Product product1 = new Product();
		product1.setDemand("H");
		product1.setSupply("H");
		Product product2 = new Product();
		product2.setDemand("H");
		product2.setSupply("L");
		Product product3 = new Product();
		product3.setDemand("L");
		product3.setSupply("L");
		productlist.add(product);
		productlist.add(product1);
		productlist.add(product2);
		productlist.add(product3);
		when(PriceEngineUtil.createProducts(inputdata, nop)).thenReturn(productlist);
		PriceEngineApplication.main(null);

	}

}
