package impl;

import java.util.List;

public class Fetcher {
	
	public static List<PriceUnit> getAllPrices(){
		String pageUrl = Global.ZAPPOS_API + "/Search?term=&includes=[\"facets\"]&facets=[\"price\"]&facetSort=name&excludes=[\"results\"]" + "&key=" + Global.API_KEY;
		List<PriceUnit> priceList = Request.GET_priceFacets(pageUrl);
		return priceList;
	}
	
	
	/*
	 * Just return the very first product's Id and Name whose price is given.
	 * It can be extended for all other listed products with the same price tag
	 */
	public static String getProducts(double price){
		String pageUrl = Global.ZAPPOS_API + "/Search?term=&filters={\"price\":[" + "\"" + price + "\"" +"]}&limit=" + Global.LIMIT + "&key=" + Global.API_KEY;
		List<ProductsUnit> results = Request.GET_products(pageUrl);		
		
		ProductsUnit oneProduct = results.get(0);
		String text = oneProduct.getProductName() + "(" + oneProduct.getProductId() + ")";	    		   
	    return text;			   
	}
		
}
