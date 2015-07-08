package vo;

public class FundVO {
	private int fundId;
	private String fundName;
	private String ticker;
	private String description;
	private String fundValue; 	
	private String transactionDate;
	private String newPrice;	//for printing the fund value to jsp
	private String newDate;
	private String newShare;	//for printing the share amount to jsp 
	private int positionId;
	private double shares;
	private String sharesStr;
	private double availshares;
	private double frozenshares;
	private String fundNameDisplay;
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
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFundValue() {
		return fundValue;
	}
	public void setFundValue(String fundValue) {
		this.fundValue = fundValue;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}
	public String getNewDate() {
		return newDate;
	}
	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}
	public double getShares() {
		return shares;
	}
	public void setShares(double shares) {
		this.shares = shares;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public String getNewShare() {
		return newShare;
	}
	public void setNewShare(String newShare) {
		this.newShare = newShare;
	}
	public double getAvailshares() {
		return availshares;
	}
	public void setAvailshares(double availshares) {
		this.availshares = availshares;
	}
	public double getFrozenshares() {
		return frozenshares;
	}
	public void setFrozenshares(double frozenshares) {
		this.frozenshares = frozenshares;
	}
	public String getSharesStr() {
		return sharesStr;
	}
	public void setSharesStr(String sharesStr) {
		this.sharesStr = sharesStr;
	}
	public String getFundNameDisplay() {
		return fundNameDisplay;
	}
	public void setFundNameDisplay(String fundNameDisplay) {
		this.fundNameDisplay = fundNameDisplay;
	}
	public int getIsLock() {
		return isLock;
	}
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
}
