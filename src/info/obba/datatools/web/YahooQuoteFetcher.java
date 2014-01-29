/*
 * Created on 12.12.2009
 *
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christian-fries.de.
 */
package info.obba.datatools.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


/**
 * @author Christian Fries
 */
public class YahooQuoteFetcher {

	private static HashMap<String, MarketDataBean> stocks = new HashMap<String, MarketDataBean>();
	private long updateIntervall;

	public static void main(String[] args)
	{
		try {
			YahooQuoteFetcher fetcher = new YahooQuoteFetcher(5.0);
			MarketDataBean bean = fetcher.getData("AAPL");
			System.out.println("Price: "+bean.getPrice());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * @param updateIntervall
     */
    public YahooQuoteFetcher(double updateIntervallInSeconds) {
	    super();
	    this.updateIntervall = (long)(updateIntervallInSeconds * 1000.0);
    }


	public MarketDataBean getData(String symbol) throws IOException
	{
		updateData(symbol);
		return stocks.get(symbol);
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
