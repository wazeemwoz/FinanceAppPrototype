package com.wsuleman.finance.stockgraph;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MarketDataBean {

	String	symbol; 
	String  name;
	double	price;
	double	change;
	double	volume;
	long	lastUpdate;

	public String getSymbol() {  
		return symbol;  
	}
	
	public String getName() {  
		return name;  
	}
	
	public void setSymbol(String symbol) {  
		this.symbol = symbol;  
	}  
	
	public void setName(String name) {  
		this.name = name;  
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
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if(symbol != null){
			sb.append("symbol:");
			sb.append(symbol); 
			sb.append(",");
		}

		if(name != null){
			sb.append(name); 
		}
		
		sb.append("price:"); sb.append(price); sb.append(",");
		sb.append("change:"); sb.append(change); sb.append(",");
		sb.append("volume:");sb.append(volume); sb.append(",");
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(lastUpdate);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	    sb.append("lastUpdate:");
	    sb.append(sdf.format(c.getTime()));
	    sb.append(",");
	    
		sb.append("]");
		return sb.toString();
	}
}  