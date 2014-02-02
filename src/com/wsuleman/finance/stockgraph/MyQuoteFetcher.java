package com.wsuleman.finance.stockgraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MyQuoteFetcher {
	private static Map<String, MarketDataBean> stocks = new HashMap<String, MarketDataBean>();
	private StringBuffer stockBuffer = new StringBuffer();
	private long updateIntervall;
	
	public MyQuoteFetcher(List<String> symbols, double updateIntervallInSeconds){
	    super();
	    this.updateIntervall = (long)(updateIntervallInSeconds * 1000.0);
	    this.downloadData(symbols, 20);
	}

	public MarketDataBean getData(String symbol) throws IOException
	{
		updateData(symbol);
		return stocks.get(symbol);
	}
	
	public void downloadData(List<String> symbols, int downloadIteration){
		// Check if we need to update
		StringBuffer symbolBuffer = new StringBuffer();
		
		System.out.println("Downloading stock data");

		stockBuffer = new StringBuffer();
		for(int i = 0; i < symbols.size(); i++){
			
			symbolBuffer.append(symbols.get(i));
			
			if(i < (symbols.size()-1)){
				if(i % downloadIteration == 0 && i != 0){
					downloadData(symbolBuffer.toString());
					symbolBuffer = new StringBuffer();
				} else {
					symbolBuffer.append("+");
				}
			}
		}

		downloadData(symbolBuffer.toString());
		symbolBuffer = new StringBuffer();
        writeToFile(stockBuffer.toString());
	}
	
	public synchronized void downloadData(String symbols){

		/*
		 * Fetch CSV data from Yahoo. The format codes (f=) are:
		 * s=symbol, l1 = last, c1 = change, d1 = trade day, t1 = trade time, o = open, h = high, g = low, v = volume
		 */
		MarketDataBean stockInfo = new MarketDataBean();
        try {
			System.out.println(symbols);
			
        	URL ulr = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + symbols + "&f=sl1c1vd1t1ohg&e=.csv");
            URLConnection urlConnection = ulr.openConnection();
            BufferedReader reader = null;
	        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {
	        	stockBuffer.append(inputLine);
	        	stockBuffer.append("\n");
	        	
	            String[] yahooStockInfo = inputLine.split(",");
	            stockInfo = new MarketDataBean();
	            stockInfo.setSymbol(yahooStockInfo[0].replaceAll("\"", ""));
	            stockInfo.setPrice(stringToDouble(yahooStockInfo[1]));
	            stockInfo.setChange(stringToDouble(yahooStockInfo[2]));
	            stockInfo.setVolume(stringToDouble(yahooStockInfo[3]));
	            stockInfo.setLastUpdated(System.currentTimeMillis());
	            stocks.put(stockInfo.getSymbol(), stockInfo);
	        }
            if(reader != null){ 
            	reader.close();
            }
        } catch(Exception e){
        	e.printStackTrace();
        	System.out.println(stockInfo);
        } 
	}
	
	private double stringToDouble(String str){
		if(str.trim().equals("N/A")){
			return 0;
		}
		return Double.valueOf(str);
	}
	
	private void writeToFile(String stocks){
		try{
			String filePath = "C:/tempStock.csv";
			System.out.println("Writing to file");
			File file = new File(filePath);
	
			// if file doesnt exists, then create it
			if (!file.exists()) {
				System.out.println("Created file");
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			bw.write(stocks);
			bw.close();
	
			System.out.println("Done writing to file:" + stocks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void updateData(String symbol) throws IOException {
		
		// Check if we need to update
		MarketDataBean dataBean = stocks.get(symbol);
		if(dataBean != null && System.currentTimeMillis() - dataBean.getLastUpdated() < updateIntervall) return;

		/*
		 * Fetch CSV data from Yahoo. The format codes (f=) are:
		 * s=symbol, l1 = last, c1 = change, d1 = trade day, t1 = trade time, o = open, h = high, g = low, v = volume
		 */
        URL ulr = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl1c1vd1t1ohg&e=.csv");
        URLConnection urlConnection = ulr.openConnection();
        BufferedReader reader = null;
        try {
	        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {
	            String[] yahooStockInfo = inputLine.split(",");
	            MarketDataBean stockInfo = new MarketDataBean();
	            stockInfo.setSymbol(yahooStockInfo[0].replaceAll("\"", ""));
	            stockInfo.setPrice(Double.valueOf(yahooStockInfo[1]));
	            stockInfo.setChange(Double.valueOf(yahooStockInfo[2]));
	            stockInfo.setVolume(Double.valueOf(yahooStockInfo[3]));
	            stockInfo.setLastUpdated(System.currentTimeMillis());
	            stocks.put(symbol, stockInfo);  
	            break;  
	        }
        }
        finally {
            if(reader != null) reader.close();
        }
     }
}
