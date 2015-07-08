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

import util.FormatUtil;

import com.opensymphony.xwork2.ActionContext;


public class RequestCheck extends BaseAction{
	final int REQUEST_CHECK = 2;
	final int STATUS_SUCCESS = 1;
	private static final long serialVersionUID = -3411182619836352505L;
	private String avBalance;
	private double checkAmount;
	private int userId;
	private User user;
	private String firstName;
	private String lastName;
	private String feedBack;
	private SqlSession session;
	private SqlSessionFactory sessionFactory;
	private Reader reader;
	private String resource;
	private Account account;
	private String balance;
	private String frozen;
	private String available;
	
	
	@Override
	public String init() throws IOException {
		//initialize environment
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		try {
			//validate user loged in
			String userObject = ActionContext.getContext().getSession().get("userId").toString();
			if (userObject == null)
				return "login";
			//setup header data field
			userId = Integer.parseInt(userObject);
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1)
				throw new Exception();
			firstName=user.getFirstName();
			lastName=user.getLastName();
			
			setAccountInformation(userId);
		} catch (Exception e) {
			feedBack = "Invaild user.";
			session.close();
			return "login";
		}
		
		try {//setup content data field avBalance
			if (getAvail()) return "initSuccess";
			else throw new Exception();
		} catch (Exception e) {
			return "fail";
		}
	}
	
	
	/*
	 * getAvail() method is designed to get current available balance from
	 * database and setup corrdinating data field: avBalance
	 */
	public boolean getAvail() throws IOException {
		try {
			Account acc = session.selectOne("selectAccountByUserId", userId);
			setReturnField(acc, "");
		} catch (Exception e) {
			setReturnField(null, "Unexpected error occurs when fetching user data.");
			return false;
		} finally {
			session.close();
		}
		
//		long aB;
//		try {
//			aB = session.selectOne("selectAvailBalanceByUserId", userId);
//		} catch (Exception e) {
//			setReturnField(0, "Unexpected error occurs when fetching user data.");
//			return false;
//		} finally {
//			session.close();
//		}
//		setReturnField(aB, "");
//		session.close();
		return true;
	}
	
	public String requestCheck() throws IOException {
		//clean up previous message
		feedBack = "";
		//initialize orm and sql session
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		//get current user information
		long check;
		try {
			String userObject = ActionContext.getContext().getSession().get("userId").toString();
			if (userObject == null || userObject =="")
				throw new Exception();
			userId = Integer.parseInt(userObject);
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1)
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			feedBack = "Invaild user";
			session.close();
			return "login";
		}
		
		
		// get current account information
		account = session.selectOne("selectAccountByUserId", userId);
		if (account == null) {
			setReturnField("Account not found. Please contact system admin.");
			session.close();
			return "fail";
		}
		// see if the account is lock, lock the account if not locked
		try {
			if (lockAccount(userId) == null) {	//is locked
				setReturnField("Your account is being manipulated now. Please try again later.");
				session.close();
				return "fail";
			}
		} catch (Exception e) {
			setReturnField("Unexpected error occurs when fetching account information.");
			session.close();
			return "fail";
		}
		//get front end check's information
		try {
			check = (long) (checkAmount * 100);
		} catch (Exception e) {
			e.printStackTrace();
			setReturnField(account, "Invaild check amount.");
			session.close();
			return "fail";
		}
		//After locking the account, do the operations
		if (check > account.getAvailBalance()) {		//validation of the check amount
			setReturnField(account, "There is not enough balance in the account.");
			try {
				unlockAccount(userId);
			} catch (Exception e2) {
				e2.printStackTrace();
			} 
			session.close();
			return "fail";
		} else {
			//account will be unlock when finishing the transaction
			Account accUpdate = new Account();
			accUpdate.setUserId(userId);
			accUpdate.setAvailBalance(account.getAvailBalance() - check);
			accUpdate.setBalance(account.getBalance() - check);
			//there is no need to lock transaction table since it is an insert ops
			Transaction trans = new Transaction();
			trans.setAccountId(account.getAccountId());
			trans.setUserId(userId);
			trans.setAmount(check);
			trans.setTransactionType(REQUEST_CHECK);
			trans.setStatus(STATUS_SUCCESS);
			trans.setExecuteDate(new Date());
			//commit the changes
			try {
				session.update("checkUpdate", accUpdate);
				session.insert("createTransaction", trans);
				session.commit();
				setAccountInformation(userId);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					unlockAccount(userId);		//unlock the account if possible
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				setReturnField(account, "Unexpected error occurs when processing transaction.");
				session.close();
				return "fail";
			} finally {
				try {
					unlockAccount(userId);		//unlock the account if possible
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				session.close();
			}
			
			setReturnField(accUpdate, "Check is successfully processed.");
			return "success";
		}
	}
	
	
	//lock the data field, return false if the account is already locked 
	//or exception is caught.
	public Account lockAccount(int userId) throws Exception {
		Account Vaccount;
		try {
			Vaccount = session.selectOne("selectAccountByUserId", userId);
			if (Vaccount == null) throw new Exception("account not found");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		if (Vaccount.getIsLock() == 1) { // check if locked
			return null;
		} else {
			try { // lock the table before updating
				session.update("lockAccountTable", Vaccount.getAccountId());
				session.commit();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return Vaccount;
		}
	}
	
	
	//if commit fail unlock the table
	public void unlockAccount(int userId) throws Exception {
		Account Vaccount;
		try {
			Vaccount = session.selectOne("selectAccountByUserId", userId);
			if (Vaccount == null) throw new Exception("account not found");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		try {
			session.update("unLockAccountTable", Vaccount.getAccountId());
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return;
	}

	public boolean setAccountInformation(int userId) throws IOException {
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		account = session.selectOne("selectAccountByUserId", userId);
		Account account = session.selectOne("selectAccountByUserId", userId);
		if (account == null) return false;
		ActionContext.getContext().getSession().put(
				"balance", FormatUtil.parseValue((double)account.getBalance()/100));   
		ActionContext.getContext().getSession().put(
				"frozen", FormatUtil.parseValue((double)account.getFrozenBalance()/100)); 
		ActionContext.getContext().getSession().put(
				"available",FormatUtil.parseValue((double)account.getAvailBalance()/100));
		return true;
	}
	
	public void setReturnField(String message) throws IOException {
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		account = session.selectOne("selectAccountByUserId", userId);
		if (account == null) {
			avBalance = " ";
		} else {
			avBalance = FormatUtil.parseValue((double) account
					.getAvailBalance() / 100);
		}
		feedBack = message;
		return;
	}

	public void setReturnField(Account acc, String message) {
		if (acc == null) {
			feedBack = message;
		} else {
			avBalance = FormatUtil.parseValue(((double)acc.getAvailBalance()) / 100);
			balance = FormatUtil.parseValue((double) acc.getBalance() / 100);
			frozen = FormatUtil.parseValue((double) acc.getFrozenBalance() / 100);
			available = FormatUtil.parseValue((double) acc.getAvailBalance() / 100);
			feedBack = message;
		}
		return;
	}
	
	
	public double getCheckAmount() {
		return checkAmount;
	}

	public String getResult() {
		return feedBack;
	}

	public int getUserId() {
		return userId;
	}

	public void setCheckAmount(double checkAmount) {
		this.checkAmount = checkAmount;
	}

	public void setResult(String result) {
		this.feedBack = result;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}



	public User getUser() {
		return user;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAvBalance() {
		return avBalance;
	}

	public void setAvBalance(String avBalance) {
		this.avBalance = avBalance;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}


	public String getBalance() {
		return balance;
	}


	public String getFrozen() {
		return frozen;
	}


	public String getAvailable() {
		return available;
	}


	public void setBalance(String balance) {
		this.balance = balance;
	}


	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}


	public void setAvailable(String available) {
		this.available = available;
	}



}
