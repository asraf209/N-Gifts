package impl;

import java.util.List;

/*
 * Catch a product information
 */
class ProductsUnit {	
	private String productId;
	private String productName;	
	private String brandName; 
    private List<ProductsUnit> results;

    public String getProductId()	{	return productId;	}
    public String getProductName()	{	return productName;	}
    public String getBrandName()	{	return brandName;	}
    public List<ProductsUnit> getResults() { return results; }               
}
