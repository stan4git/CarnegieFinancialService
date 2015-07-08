package action;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;

import model.Account;
import model.Transaction;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.opensymphony.xwork2.ActionContext;

public class DepositCheck extends BaseAction {
	private static final long serialVersionUID = -4593086581613510557L;
	private final int DEPOSIT = 3;
	private final int STATUS_SUCCESS = 1;
	private String feedBack;
	private int targetUser;
	private int customerIdField;
	private double checkAmount;
	private int userId;
	private User user;
	private String firstName;
	private String lastName;
	private SqlSession session;
	private SqlSessionFactory sessionFactory;
	private Reader reader;
	private String resource;
	private Account account;
	private String tab2;
	
	@Override
	public String init() throws IOException {
		//initialize environment
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		
		try { //test the authorization of current user
			String userObject = ActionContext.getContext().getSession().get("userId").toString();
			if (userObject == null || userObject =="")
				throw new Exception();
			userId = Integer.parseInt(userObject);
			ActionContext.getContext().getSession().put("targetUser", targetUser);
			//initialize header information
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 0)
				throw new Exception();
			firstName = user.getFirstName();
			lastName = user.getLastName();
		} catch (Exception e) {
			feedBack = "Invaild user.";
			return "login";
		} finally {
			session.close();
		}
		return "initSuccess";
	}
	
	
	public String depositCheck() throws IOException {
		//initialize environment
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		
		
		try { //test the authorization of current user
			String userObject = ActionContext.getContext().getSession().get("userId").toString();
			if (userObject == null || userObject =="")
				throw new Exception();
			userId = Integer.parseInt(userObject);
			//initialize header information
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 0)
				throw new Exception();
		} catch (Exception e) {
			feedBack = "Invaild user.";
			tab2="false";
			session.close();
			return "login";
		}
		
		
		// fetching current account data
		try {
			customerIdField = Integer.parseInt(ActionContext.getContext().getSession().get("targetUser").toString());
			account = session.selectOne("selectAccountByUserId", customerIdField);
			if (account == null) {
				feedBack = "Cannot find the account.";
				tab2="false";
				session.close();
				return "fail";
			}
			//put on locks before updating the account
			if (!lockAccount(account)) {
				feedBack = "Database is now busy. Please try again later.";
				tab2="false";
				unlockAccount(account);
				session.close();
				return "fail";
			}
		} catch (Exception e) {
			feedBack = "Unexpected error occurs when fetching account data.";
			tab2="false";
			unlockAccount(account);
			session.close();
			return "fail";
		}
		long depositAmount;
		depositAmount = (long) (checkAmount * 100);
		//fetch check information
		try {
			if (account.getBalance() + depositAmount > 999999999999999L) 
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			feedBack = "Balance overflow. The maximum balance allowed is 9,999,999,999,999.99!";
			tab2="false";
			unlockAccount(account);
			session.close();
			return "fail";												 
		}
		//update the account after locking the account
		Account accUpdate = new Account();
		accUpdate.setAvailBalance(account.getAvailBalance() + depositAmount);
		accUpdate.setBalance(account.getBalance() + depositAmount);
		accUpdate.setUserId(customerIdField);
		accUpdate.setIsLock(0);
		
		Transaction tran = new Transaction();
		tran.setAccountId(account.getAccountId());
		tran.setAmount(depositAmount);
		tran.setTransactionType(DEPOSIT);
		tran.setUserId(customerIdField);
		tran.setStatus(STATUS_SUCCESS);
		tran.setExecuteDate(new Date());
		try {
			session.insert("createTransaction", tran);
			session.update("checkUpdate", accUpdate);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			feedBack = "Unexpected error occurs when processing data.";
			tab2="false";
			unlockAccount(account);
			return "fail";
		} finally {
			unlockAccount(account);
			session.close();
		}
		feedBack = "Check is successfully deposited.";
		tab2="false";
		return "success";
	}
	
	
	public boolean lockAccount(Account account) {
		try {
			account = session.selectOne("selectAccountByUserId", customerIdField);
			if (account == null) return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		try { // lock the table before updating
			session.update("lockAccountTable", account.getAccountId());
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public void unlockAccount(Account account) {
		try {
			account.setIsLock(0);
			session.update("unLockAccountTable", account.getAccountId());
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	
	public double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(double checkAmount) {
		this.checkAmount = checkAmount;
	}


	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public int getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(int targetUser) {
		this.targetUser = targetUser;
	}

	public int getCustomerIdField() {
		return customerIdField;
	}

	public void setCustomerIdField(int customerIdField) {
		this.customerIdField = customerIdField;
	}


	public String getTab2() {
		return tab2;
	}


	public void setTab2(String tab2) {
		this.tab2 = tab2;
	}



}
