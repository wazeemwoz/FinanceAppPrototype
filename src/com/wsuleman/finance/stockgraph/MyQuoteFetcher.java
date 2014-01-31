package com.wsuleman.finance.stockgraph;

import info.obba.datatools.web.MarketDataBean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyQuoteFetcher {
	
	public void getSymbolsSP500(String url){
		Map<String,String> symbols = new HashMap<String,String>();
        try {
            URL ulr = new URL("http://en.wikipedia.org/w/index.php?title=List_of_S%26P_500_companies&action=edit&section=1");
            URLConnection urlConnection = ulr.openConnection();
            BufferedReader reader = null;
	        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {
		        System.out.println(inputLine);
	        	extractSymbolsFromLine(inputLine, symbols);
	            break;  
	        }
	        
			File file = new File("C:/temp.dat");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
		    Iterator it = symbols.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = " + pairs.getValue());

				bw.write(pairs.getKey() + "," + pairs.getValue() + "\n");
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		    
			bw.close();
 
			System.out.println("Done");
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void extractSymbolsFromLine(String line, Map<String, String> symbols){		
		if(line.length() > 0){
			return;
		}
		
		if(line.trim().equals("|-")){
			return;
		}
		
		line = line.substring(2);
		
		String[] cols = line.split("||");
		String symbol, company;
		symbol = cols[0].trim();
		company = cols[1].trim();
		
		symbol = symbol.substring(symbol.lastIndexOf('|'), symbol.length()-2);
		company = company.substring(2, company.length()-2);
		symbols.put(symbol, company);
		
		return;
	}
}
