/*
 * Created on 11.12.2009
 *
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christian-fries.de.
 */
package info.obba.datatools.web;

/**
 * @author Christian Fries
 */
public class MarketDataBean {

	String	symbol;  
	double	price;
	double	change;
	double	volume;
	long	lastUpdate;

	public String getSymbol() {  
		return symbol;  
	}

	public void setSymbol(String symbol) {  
		this.symbol = symbol;  
	}  

	public double getPrice() {  
		return price;  
	}

	public void setPrice(double price) {  
		this.price = price;  
	}

	public double getChange() {  
		return change;  
	}

	public void setChange(double change) {  
		this.change = change;  
	}  


	/**
     * @return the volume
     */
    public double getVolume() {
    	return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(double volume) {
    	this.volume = volume;
    }

    public long getLastUpdated() {  
		return lastUpdate;  
	}  

	public void setLastUpdated(long lastUpdate) {  
		this.lastUpdate = lastUpdate;  
	}
}  