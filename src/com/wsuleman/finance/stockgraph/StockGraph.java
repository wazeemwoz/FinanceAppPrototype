package com.wsuleman.finance.stockgraph;

public class StockGraph {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Move hard coded strings for pathname to be passed to the program using args
		SymbolLoader sp500Symbols = new SP500SymbolFetcher("C:\\Users\\VHD\\test.dat");
		MyQuoteFetcher stocks = new MyQuoteFetcher(sp500Symbols.getSymbols(), 5, "C:\\Users\\VHD\\stockOutput.csv");
	}

}
