package com.wsuleman.finance.stockgraph;

public class StockGraph {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SymbolLoader sp500Symbols = new SP500SymbolFetcher("C:/test.dat");
		MyQuoteFetcher stocks = new MyQuoteFetcher(sp500Symbols.getSymbols(), 5);
		
	}

}
