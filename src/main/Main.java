package main;

import impl.Fetcher;
import impl.N_Prices;
import impl.PriceUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static void start(final int N, final double TARGET){
		
		/*
		 * Get list of all possible price values found in Zappos
		 */
		List<PriceUnit> allPrices = Fetcher.getAllPrices();		
		
		// Select only those price values that are equal or less than the target
		List<Double> priceList = new ArrayList<>();
		for(PriceUnit eachPrice : allPrices){
			if(eachPrice.getPrice() <= TARGET)	
				priceList.add(eachPrice.getPrice());
		}				
		
		
		/*
		 * Get total price and combination of N item indices found in priceList.
		 * Its a N-subset sum HashMap  		 
		 * <Total, <Hash Items>>
		 */		
		HashMap<Double, HashMap<Integer, Boolean>> selectPrices = N_Prices.subSum(priceList, TARGET, N);		
		
		printResults(selectPrices, priceList);			// Print all combinations
	}
	
	
	
	/*
	 * Print output
	 */
	private static void printResults(HashMap<Double, HashMap<Integer, Boolean>> selectPrices, List<Double> priceList){
		
		HashMap<Double, String> alreadyParsed = new HashMap<>();
		int i=0;
		System.out.println("Total results found: " + selectPrices.size());
		System.out.println();
		
		for(double eachTotal : selectPrices.keySet()){
			if(i==5){
				System.out.println();
				String input = getUserInput("Press 'Enter' to see more OR 'q' to Exit: ");
				if(input.equals("q"))	break;
				i = 0;
			}
			
			HashMap<Integer, Boolean> indices = selectPrices.get(eachTotal);
			String line ="{";
			for(int index : indices.keySet()){
				double itemPrice = priceList.get(index);
				if(alreadyParsed.containsKey(itemPrice))
					line += alreadyParsed.get(itemPrice) + "-$" + itemPrice;				
				else{
					String item = Fetcher.getProducts(priceList.get(index));
					alreadyParsed.put(itemPrice, item);
					line += item + "-$" + itemPrice;
				}
				line += ", ";
			}
			line += "Total: $" + eachTotal;
			line += "}";
			System.out.println(line);
			i++;
		}		
	}
		
	
	/*
	 * Taking input from user
	 */
	private static String getUserInput(String msg){
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner (System.in);
		System.out.print(msg);  
		String input = scanner.nextLine();		
		return input;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = Integer.parseInt(getUserInput("Desired # of Products: "));
		double X = Double.parseDouble(getUserInput("Desired $ Ampunt: "));
		if(N < 1 || X < 1){
			System.out.println("Nothing found!");
			System.exit(0);
		}
		System.out.println();
		
		start(N, X);
		
	}

}
