package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
	private int accountId;
	private int userId;
	private long balance;  
    private long frozenBalance;
    private long availBalance;  
    private Date lastTradingDay;
    private Date lastUpadateTime;
	private User user;
	private int isLock;
	
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public long getFrozenBalance() {
		return frozenBalance;
	}
	public void setFrozenBalance(long frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	public long getAvailBalance() {
		return availBalance;
	}
	public void setAvailBalance(long availBalance) {
		this.availBalance = availBalance;
	}
	public Date getLastTradingDay() {
		return lastTradingDay;
	}
	public void setLastTradingDay(Date lastTradingDay) {
		this.lastTradingDay = lastTradingDay;
	}
	public Date getLastUpadateTime() {
		return lastUpadateTime;
	}
	public void setLastUpadateTime(Date lastUpadateTime) {
		this.lastUpadateTime = lastUpadateTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public int getIsLock() {
		return isLock;
	}
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
	
 }
