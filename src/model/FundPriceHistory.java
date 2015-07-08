package model;

import java.util.Date;

public class FundPriceHistory {
	private int fundHistoryId;
	private int fundId;
    private Date priceDate;
    private long price;
    private Fund fund;
    
	public int getFundHistoryId() {
		return fundHistoryId;
	}
	public void setFundHistoryId(int fundHistoryId) {
		this.fundHistoryId = fundHistoryId;
	}
	public int getFundId() {
		return fundId;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
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
	public Fund getFund() {
		return fund;
	}
	public void setFund(Fund fund) {
		this.fund = fund;
	}
	
	
 }
