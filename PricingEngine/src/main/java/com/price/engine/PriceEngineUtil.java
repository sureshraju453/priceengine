package com.price.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceEngineUtil {

	/**
	 * Returns recommended price from given competitors and product
	 *
	 * @param competitorMap
	 * @param product
	 * @return
	 */
	public static double getRecommendedPrice(Map<String, Competitor> competitorMap, Product product) {
		double averagePrice = 0;
		double recommendedPrice = Double.MAX_VALUE;
		Map<Double, Integer> priceCountMap = new HashMap<Double, Integer>();
		int k = 0;
		for (String key : competitorMap.keySet()) {
			Product comProduct = competitorMap.get(key).getProducts().get(product.getName());
			if (comProduct != null) {
				double competitorPrice = comProduct.getPrice();
				averagePrice = averagePrice + competitorPrice;
				if (priceCountMap.containsKey(competitorPrice)) {
					priceCountMap.put(competitorPrice, priceCountMap.get(competitorPrice) + 1);
				} else {
					priceCountMap.put(competitorPrice, 1);
				}

				++k;
			}
		}
		averagePrice = averagePrice / k;
		int maxFrequency = getMaxFequency(priceCountMap);
		for (double price : priceCountMap.keySet()) {
			if (!(price < (0.5 * averagePrice) || price > (1.5 * averagePrice)) && (price < recommendedPrice)
					&& priceCountMap.get(price) >= maxFrequency) {
				recommendedPrice = price;
			}

		}
		return recommendedPrice;
	}

	/**
	 * Returns max count for given prices map
	 *
	 * @param priceCountMap
	 * @return
	 */
	public static int getMaxFequency(Map<Double, Integer> priceCountMap) {
		int count = 0;
		for (double price : priceCountMap.keySet()) {
			if (priceCountMap.get(price) >= count) {
				count = priceCountMap.get(price);
			}
		}

		return count;
	}

	/**
	 * Creates products given an array of input String's
	 *
	 * @param inputdata
	 * @param nop
	 * @return
	 */
	public static List<Product> createProducts(String[] inputdata, int nop) {

		List<Product> productlist = new ArrayList<Product>();
		for (int i = 0; i <= nop; i++) {
			String[] productdetails = inputdata[i].split(" ");
			if (productdetails != null && productdetails.length > 2) {
				Product product = new Product();
				product.setName(productdetails[0]);
				product.setSupply(productdetails[1]);
				product.setDemand(productdetails[2]);
				productlist.add(product);
			}
		}
		return productlist;
	}

	/**
	 * Creates Competitors given an array of input String's
	 *
	 * @param inputdata
	 * @param nop
	 * @return
	 */
	public static Map<String, Competitor> createCompetitors(String[] inputdata, int nop) {
		int noc = Integer.parseInt(inputdata[nop + 1]);
		Map<String, Competitor> competitorMap = new HashMap<String, Competitor>();

		for (int j = nop + 2; j <= noc + nop; j++) {

			String[] competitordetails = inputdata[j].split(" ");
			if (competitordetails != null && competitordetails.length > 2) {
				if (!competitorMap.containsKey(competitordetails[1])) {
					Map<String, Product> competitorProductMap = new HashMap<String, Product>();
					Competitor competitor = new Competitor();
					competitor.setName(competitordetails[1]);
					Product comProduct = createProduct(competitordetails);
					competitorProductMap.put(competitordetails[0], comProduct);
					competitor.setProducts(competitorProductMap);
					competitorMap.put(competitordetails[1], competitor);

				} else {
					Competitor competitor = competitorMap.get(competitordetails[1]);
					Product comProduct = createProduct(competitordetails);
					competitor.getProducts().put(competitordetails[0], comProduct);

				}

			}
		}

		return competitorMap;
	}

	/**
	 * Creates Product given array of String's
	 *
	 * @param competitordetails
	 * @return
	 */
	public static Product createProduct(String[] competitordetails) {
		Product comProduct = new Product();
		comProduct.setName(competitordetails[0]);
		comProduct.setPrice(Double.parseDouble(competitordetails[2]));
		return comProduct;
	}

}
