package model;

public class Position {
	private int positionId;
	private int userId; 
	private String userName;
	private int fundId;
    private long shares;
    private long frozenShares;
    private long availShares;
    private long fundValue;
    private String ticker;
    private String fundName;
    private Fund fund;
    private User user;
    private int isLock;
	
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
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
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
	public Fund getFund() {
		return fund;
	}
	public void setFund(Fund fund) {
		this.fund = fund;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public long getFundValue() {
		return fundValue;
	}
	public void setFundValue(long fundValue) {
		this.fundValue = fundValue;
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
	public long getFrozenShares() {
		return frozenShares;
	}
	public void setFrozenShares(long frozenShares) {
		this.frozenShares = frozenShares;
	}
	public long getAvailShares() {
		return availShares;
	}
	public void setAvailShares(long availShares) {
		this.availShares = availShares;
	}
	public int getIsLock() {
		return isLock;
	}
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
    
    
 }
