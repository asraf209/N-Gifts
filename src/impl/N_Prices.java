package impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * This class actually compute N-subset sum. The way it does that is two-fold.
 * First, computes 2-subset sum
 * Then, computes subsequent 3,4,...N subset sum 
 */
public class N_Prices {
	
	private static List<Double> V = new ArrayList<>();									// Holds total sum of prices for k-items	
	private static List<HashMap<Integer, Boolean>> itemIndex = new ArrayList<>();		// Holds list of corresponding item indices
	
	/*
	 * Compute N-subset sum
	 * O(n*n + m*n + m)
	 * n = size of the original item list
	 * m = size of the computed sum list
	 */
	public static HashMap<Double, HashMap<Integer, Boolean>> subSum(List<Double> priceList, final double TARGET, final int N){								
		
		if(N < 1)	return null;
		if(N==1)	return singleItem(priceList, TARGET);
		
		/*
		 *  First get 2-subset sum
		 *  O(n*n)
		 */
		twoSum(priceList, TARGET);					
		
		
		/*
		 *  Then generate subsequent 3,4, .... N subset sum by running one pass over the V list each time.
		 *  O(m*n)
		 */
		for(int i=3; i<=N; i++){
			updateV(priceList, TARGET);			
		}				
		
		
		/*
		 *  Take only those sum values that is equal or above 80% of the targeted value
		 *  Also get rid of duplicated sum found in V
		 *  O(m)
		 *  m = size of the computed sum list V
		 */
		HashMap<Double, HashMap<Integer, Boolean>> finalList = new HashMap<>();				
		for(int i=0; i<V.size(); i++){						
			if(finalList.containsKey(V.get(i)))	continue;
			else{
				if(V.get(i) >= Global.PERCENTILE*TARGET)
					finalList.put(V.get(i), itemIndex.get(i));				
			}						
		}
		
		//print(priceList, target);
		
		return finalList;
		
	}
	
	
	
	/*
	 * Update the V list. 
	 * In any subsequent subsum, if its value > target, then remove that entry
	 * O(m*n)
	 * m = size of the computed sum list V
	 * n = size of original item list
	 */
	private static void updateV(List<Double> priceList, double target){
		for(int i=0; i<V.size(); i++){
			boolean updated = false;			
			for(int j=0; j<priceList.size(); j++){								
				//if(priceList.get(j) >= target)	break;
				
				double total = V.get(i) + priceList.get(j); 			
				
				if(total > target)	break;				
				else{			
					if(!itemIndex.get(i).containsKey(j)){
						V.set(i, total);
						itemIndex.get(i).put(j, true);
						updated = true;
						break;
					}										
				}								
			}	
			
			if(!updated){
				V.remove(i);
				itemIndex.remove(i);
				--i;				
			}
			
		}
	}
	
	
	
	/*
	 * Get combination of two prices which is <= target
	 */
	private static void twoSum(List<Double> priceList, double target){		
				
		for(int i = 0; i < priceList.size()-1; i++){
			//if(priceList.get(i) >= target)	break;
		    
			for(int j=i+1; j<priceList.size(); j++){		    			    			    			    
			    //if(priceList.get(j) >= target)	break;			    			    			    				   
			    
			    double sum = priceList.get(i) + priceList.get(j);		    			    					  
			    
			    if(sum > target)	break;
			    
			    //if(sum <= target && sum >= 0.4*target){
			    if(sum <= target){
			    	V.add(sum);		    			    		
			    		
			   		HashMap<Integer, Boolean> tinyMap = new HashMap<>();
			   		tinyMap.put(i, true);
			   		tinyMap.put(j, true);			    		    	
			   		itemIndex.add(tinyMap);			    		
		    	}			    				    			
		    }
		}		  
	}// twoSum
	

	
	
	/*
	 * Get single item whose price is closest to target
	 * O(n)
	 */
	private static HashMap<Double, HashMap<Integer, Boolean>> singleItem(List<Double> priceList, final double TARGET){						
		HashMap<Double, HashMap<Integer, Boolean>> finalList = new HashMap<>();		
		for(int i=0; i<priceList.size(); i++){						
			if(priceList.get(i) >= Global.PERCENTILE*TARGET){
				HashMap<Integer, Boolean> tmp = new HashMap<>();
				tmp.put(i, true);
				finalList.put(priceList.get(i), tmp);		
			}
		}		
		return finalList;		  
	}

	
	
	/*
	 * Print desired price combinations
	 */
	private static void print(List<Double> priceList, final double TARGET){
		HashMap<Double, Boolean> finalList = new HashMap<>();				
		for(int i=0; i<V.size(); i++){						
			if(finalList.containsKey(V.get(i)))	continue;
			else	finalList.put(V.get(i), true);
			
			if((V.get(i) >= Global.PERCENTILE*TARGET) ){
				System.out.print(V.get(i) + "= ");
				for(int key: itemIndex.get(i).keySet())
					System.out.print(priceList.get(key) + ", ");				
				System.out.println();
			}			
		}		
	}
	
	
}
