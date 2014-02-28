package impl;

import java.util.List;

/*
 * Price facets structure to get all possible price values foiund in Zappos
 */
class PriceFacets {		
		
	private List<PriceUnit> values;	
	private List<PriceFacets> facets;
		
	public List<PriceFacets> getFacets()	{	return facets;	}
	public List<PriceUnit> getValues()	{	return values;	}	
	
}
