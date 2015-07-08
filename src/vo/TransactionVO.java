package vo;

import java.util.Date;

import model.Account;
import model.Fund;
import model.User;

public class TransactionVO {

	private int transactionId;
	private int userId;
	private int accountId;
	private String fundName;
	private String shares;
	private int transactionType;
	private String amount;
	private String execuateDate;
	private int status;
	private String price;
	private User user;
	private Fund fund;
	private Account account;
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}


	public String getExecuateDate() {
		return execuateDate;
	}


	public void setExecuateDate(String execuateDate) {
		this.execuateDate = execuateDate;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getShares() {
		return shares;
	}

	public int getStatus() {
		return status;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
