package model;

import java.util.Date;
//stan added userName, fundName, isLock
public class Transaction {
	private int transactionId;
	private int userId;
	private String userName;
	private int accountId;
	private String fundName;
	private int fundId;
	private int positionId;
	private long shares;  
    private int transactionType;
    private long amount;  
	private Date executeDate;
    private int status;
    private String errorMessage;
    private long price;
    private User user;
    private Fund fund;
    private Account account;
    private boolean isLock;
    
	
	
    public int getTransactionId() {
		return transactionId;
	}
	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public int getAccountId() {
		return accountId;
	}
	public String getFundName() {
		return fundName;
	}
	public int getFundId() {
		return fundId;
	}
	public int getPositionId() {
		return positionId;
	}
	public long getShares() {
		return shares;
	}
	public int getTransactionType() {
		return transactionType;
	}
	public long getAmount() {
		return amount;
	}
	public Date getExecuteDate() {
		return executeDate;
	}
	public int getStatus() {
		return status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public long getPrice() {
		return price;
	}
	public User getUser() {
		return user;
	}
	public Fund getFund() {
		return fund;
	}
	public Account getAccount() {
		return account;
	}
	public boolean isLock() {
		return isLock;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setFund(Fund fund) {
		this.fund = fund;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
 }
