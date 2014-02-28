package impl;

import java.net.URL;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

/*
 * Handles http GET requests and responses, to and from Zappos API
 */
class Request {
	
	static List<PriceUnit> GET_priceFacets(String pageUrl){
		URL url;
		try {
			url = new URL(pageUrl);
			InputStream inputStream = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		    String response = reader.readLine();		    		    
		    
		    PriceFacets priceFacets = new Gson().fromJson(response, PriceFacets.class);
		    List<PriceUnit> values = priceFacets.getFacets().get(0).getValues();
		    return values;
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	static List<ProductsUnit> GET_products(String pageUrl){
		URL url;		
		try {
			url = new URL(pageUrl);
			InputStream inputStream = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		    String response = reader.readLine();		    		    		    
		    //System.out.println(response);
		    
		    ProductsUnit products = new Gson().fromJson(response, ProductsUnit.class);
		    List<ProductsUnit> results = products.getResults();
		    return results;		    
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		}
		return null;
	}
}
