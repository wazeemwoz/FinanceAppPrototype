package com.wsuleman.finance.stockgraph;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public abstract class SymbolLoader {
	abstract List<String> getSymbols();
	
	public void loadSymbols(String location, List<String> list){
		try{
			Scanner s = new Scanner(new File(location));
			while (s.hasNext()){
				String symbol = s.next().trim();
			    if(symbol.length() > 0)
			    	list.add(symbol);
			}
			s.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
