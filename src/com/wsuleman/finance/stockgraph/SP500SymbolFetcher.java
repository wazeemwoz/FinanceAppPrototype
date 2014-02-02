package com.wsuleman.finance.stockgraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SP500SymbolFetcher extends SymbolLoader {
	private List<String> symbols;
	private String wikiUrl = "http://en.wikipedia.org/wiki/List_of_S%26P_500_companies";
	public static final char seperator = '\n';
	
	public SP500SymbolFetcher(String filePath){
		symbols = new ArrayList<String>();
		if(isSymbolsOutOfDate(filePath)){
			System.out.println("File is out of date, downloading symbosl from : " + wikiUrl);
			symbols = downloadFromWikipedia(filePath);
		    writeSymbolsToFile(symbols, filePath);
		} else {
			System.out.println("File is within date, loading symbols from file");
			loadSymbols(filePath, symbols);
		}
	}
	
	public List<String> getSymbols(){
		return symbols;
	}
	
	private boolean isSymbolsOutOfDate(String filePath){
		File file = new File(filePath);
		if (!file.exists()) {
			return true;
		}
		
		Calendar lastModded = Calendar.getInstance();
		Calendar yesterday = Calendar.getInstance();
		yesterday.roll(Calendar.DAY_OF_MONTH, true);
		lastModded.setTimeInMillis(file.lastModified());
		
		if(lastModded.compareTo(yesterday) > 0){
			return true;
		}
		return false;
	}
	
	private List<String> downloadFromWikipedia(String filePath){
    	symbols = new ArrayList<String>();
        try{
			Document doc = Jsoup.connect(wikiUrl).get();
			Element table = doc.select(".wikitable.sortable").first();
			Elements col = table.select("td:eq(0)");
			for( Element element : col)
			{
			    System.out.println(element.text());
			    symbols.add(element.text().trim());
			}
		    System.out.println("Done downloading symbols");
        } catch (Exception e) {
			e.printStackTrace();
		}
        return symbols;
	}
	
	private void writeSymbolsToFile(List<String> symbols, String filePath){
        try {
			System.out.println("Writing to file");
			File file = new File(filePath);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				System.out.println("Created file");
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
		    for (String symbol : symbols) {
				bw.write(symbol + SP500SymbolFetcher.seperator);
		    }
		    
			bw.close();
 
			System.out.println("Done writing to file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
