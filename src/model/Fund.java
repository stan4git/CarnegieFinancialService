package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fund {
	private int fundId;  
    private String fundName;
    private String description;
    private String ticker;
    private long fundValue;
    private Date lastTradingDate;
    private List<FundPriceHistory> fundPriceHistories = new ArrayList<FundPriceHistory>();
    private int isLock; 
	
	public int getFundId() {
		return fundId;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<FundPriceHistory> getFundPriceHistories() {
		return fundPriceHistories;
	}
	public void setFundPriceHistories(List<FundPriceHistory> fundPriceHistories) {
		this.fundPriceHistories = fundPriceHistories;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public long getFundValue() {
		return fundValue;
	}
	public void setFundValue(long fundValue) {
		this.fundValue = fundValue;
	}
	public Date getLastTradingDate() {
		return lastTradingDate;
	}
	public void setLastTradingDate(Date lastTradingDate) {
		this.lastTradingDate = lastTradingDate;
	}
	public int getIsLock() {
		return isLock;
	}
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
	
 }
