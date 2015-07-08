package vo;

public class PositionAndFund {
	private int userId; 
	private int fundId;
    private String shares;
    private String fundValue;
    private String ticker;
    private String fundName;
    private String sharesxValue;
    private int positionId;
	
    public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFundId() {
		return fundId;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public void setShares(String shares) {
		this.shares = shares;
	}
	public void setFundValue(String fundValue) {
		this.fundValue = fundValue;
	}
	public String getShares() {
		return shares;
	}
	public String getFundValue() {
		return fundValue;
	}
	public String getSharesxValue() {
		return sharesxValue;
	}
	public void setSharesxValue(String sharesxValue) {
		this.sharesxValue = sharesxValue;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	
    
    

}

