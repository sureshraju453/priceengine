package com.price.engine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PriceEngineApplication {

	private static final DecimalFormat formatter = new DecimalFormat("#.#");

	private static final String HIGH = "H";
	private static final String LOW = "L";

	public static void main(String[] args) {
		try {
			Path path = Paths.get(PriceEngineApplication.class.getClassLoader().getResource("input2.txt").toURI());
			final StringBuilder inputBuilder = new StringBuilder();
			Stream<String> lines = Files.lines(path);
			lines.forEach(line -> inputBuilder.append(line).append("\n"));
			lines.close();
			String[] inputdata = inputBuilder.toString().split("\n");

			if (inputdata != null && inputdata.length > 0) {
				int nop = Integer.parseInt(inputdata[0]);
				List<Product> productslist = PriceEngineUtil.createProducts(inputdata, nop);
				Map<String, Competitor> competitorMap = PriceEngineUtil.createCompetitors(inputdata, nop);
				for (Product product : productslist) {
					if (product.getSupply().equals(HIGH) && product.getDemand().equals(HIGH)) {
						System.out.println(product.getName() + " "
								+ formatter.format(PriceEngineUtil.getRecommendedPrice(competitorMap, product)));
					} else if (product.getSupply().equals(LOW) && product.getDemand().equals(LOW)) {
						System.out.println(product.getName() + " "
								+ formatter.format(1.1 * PriceEngineUtil.getRecommendedPrice(competitorMap, product)));
					} else if (product.getSupply().equals(LOW) && product.getDemand().equals(HIGH)) {
						System.out.println(product.getName() + " "
								+ formatter.format(1.05 * PriceEngineUtil.getRecommendedPrice(competitorMap, product)));
					} else if (product.getSupply().equals(HIGH) && product.getDemand().equals(LOW)) {
						System.out.println(product.getName() + " "
								+ formatter.format(0.95 * PriceEngineUtil.getRecommendedPrice(competitorMap, product)));

					}
				}

			}

		} catch (URISyntaxException e) {

		} catch (IOException e) {

		}
	}

}
