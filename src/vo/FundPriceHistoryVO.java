package vo;

import java.util.Date;

public class FundPriceHistoryVO {
	private Date priceDate;
    private long price;
	
    public Date getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
    
    

}
