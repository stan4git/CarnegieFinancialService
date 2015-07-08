package action;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import util.*;
import model.Account;
import model.Fund;
import model.Position;
import model.Transaction;
import vo.PositionAndFund;
import vo.TransactionVO;
import model.User;

public class ViewAccount extends BaseAction {
	private int userId;
	private String userName;
	private String passWord;
	private int role;
	private String lastName;
	private String firstName;
	private String addrLine1;
	private List<Position> positions;
	private Account account;
	private User user;
	private List<User> users;
	private String lastTradingDay;
	private Fund fund;
	private List<Fund> funds = new ArrayList<Fund>();
	private List<PositionAndFund> fundDetail;
	private String balance;
	private String frozen;
	private String available;
	private String feedback1;

	// added by stan 1/27 19:00
	private List<TransactionVO> tranList;
	private String resultList;
	private String resource;
	private Reader reader;
	private SqlSessionFactory sessionFactory;
	private SqlSession session;
	private Position position;
	private int positionId;
	private String buyamount;
	private String sellshares;
	private int fundId;
	private String tab2;
	private String tab2Active;
	private String feedback2;
	
	
	

	public String init() throws IOException {
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();

		// may need some user access validation here --stan
		try {
			if (ActionContext.getContext().getSession().get("userId") == null)
				throw new Exception();
			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1)
				throw new Exception();
			firstName = user.getFirstName();
			lastName = user.getLastName();
			addrLine1 = user.getAddrLine1();
			account = session.selectOne("selectaccountbyuserid", userId);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "needlogin";
		}

		lastTradingDay = DateUtil.dateToString(account.getLastTradingDay());
		balance = FormatUtil.parseValue((double) account.getBalance() / 100);
		frozen = FormatUtil
				.parseValue((double) account.getFrozenBalance() / 100);
		available = FormatUtil
				.parseValue((double) account.getAvailBalance() / 100);

		ActionContext.getContext().getSession().put("balance", balance);
		ActionContext.getContext().getSession().put("frozen", frozen);
		ActionContext.getContext().getSession().put("available", available);

		List<Position> positions = session.selectList("selectPositionAndFundByUserId", userId);
		List<PositionAndFund> pafs = new ArrayList<PositionAndFund>();
		for (int i = 0; i < positions.size(); i++) {
			PositionAndFund paf = new PositionAndFund();
			double value1 = positions.get(i).getFundValue() / 100.0;
			paf.setFundValue(FormatUtil.parseValue(value1));
			double share1 = positions.get(i).getShares() / 1000;
			paf.setShares(FormatUtil.parseShare(share1));
			paf.setFundName(positions.get(i).getFundName());
			paf.setTicker(positions.get(i).getTicker());
			paf.setSharesxValue(FormatUtil.parseValue(value1 * share1));
			paf.setPositionId(positions.get(i).getPositionId());
			System.out.println("position id = "
					+ positions.get(i).getPositionId());
			pafs.add(paf);
		}
		fundDetail = pafs;
		session.close();
		return "success-customer";
	}

	// added by stan 1/27 19:00
	public boolean getTransactionList() throws IOException {
		// initialize environment
		// resource = "orm/configuration.xml";
		// reader = Resources.getResourceAsReader(resource);
		// sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		// session = sessionFactory.openSession();

		List<Transaction> temp;
		try {
			temp = session.selectList("selectTransactionsByUserId", userId);
			if (temp == null) {
				resultList = "User not found.";
				return false;
			}
		} catch (Exception e) {
			resultList = "Error occurs when fetching user information.";
			e.printStackTrace();
			session.close();
			return false;
		}

		// stan edited model and parsing 1/26 8:25
		List<TransactionVO> LTVO = new LinkedList<TransactionVO>();
		for (Transaction t : temp) {
			TransactionVO tvo = new TransactionVO();
			tvo.setAccountId(t.getAccountId());
			tvo.setAmount(FormatUtil.parseValue((double) t.getAmount() / 100));
			tvo.setExecuateDate((t.getExecuteDate() == null) ? "" : DateUtil
					.dateToString(t.getExecuteDate()));
			tvo.setFundName(t.getFundName());
			tvo.setPrice(FormatUtil.parseValue((double) t.getPrice() / 100));
			tvo.setShares(FormatUtil.parseShare((double) t.getShares() / 1000));
			tvo.setStatus(t.getStatus());
			tvo.setTransactionId(t.getTransactionId());
			tvo.setTransactionType(t.getTransactionType());
			tvo.setUserId(t.getUserId());
			tvo.setErrorMsg(t.getErrorMessage());
			LTVO.add(tvo);
		}
		tranList = LTVO;
		setAccountInformation(userId);
		session.close();
		return true;
	}

	public String getUserInfo() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		try {
			int userTestId = Integer.parseInt(
					ActionContext.getContext().getSession().get("userId").toString());
			User accessTest = session.selectOne("selectuserbyuserid", userTestId);
			if (accessTest == null || accessTest.getRole() == 0)
				throw new Exception();
			users = session.selectList("selectuserbyrole", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return "needlogin";
		} 
		setAccountInformation(userId);
		session.close();
		return "success-admin";

	}

	public String getUserDetail() throws IOException {
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		
		try {
			int userTestId = Integer.parseInt(
					ActionContext.getContext().getSession().get("userId").toString());
			User accessTest = session.selectOne("selectuserbyuserid", userTestId);
			if (accessTest == null || accessTest.getRole() == 0)
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "needlogin";
		}
		
		try {
			user = session.selectOne("selectuserbyusername", userName);
			if (user == null || user.getRole() == 1)
				throw new Exception();
			else {
				userId = user.getUserId();
			}
		} catch (Exception e) {
			e.printStackTrace();
			feedback1 = "User name not found. Please contact system admin.";
			if (tab2Active == null) tab2="false";
			else tab2="true";
			session.close();
			return "false";
		}
		account = session.selectOne("selectaccountbyuserid", userId);
		positions = session.selectList("selectpositionbyuserid", userId);
		lastTradingDay = DateUtil.dateToString(account.getLastTradingDay());
		balance = FormatUtil.parseValue((double) account.getBalance() / 100);

		// added by stan 1/27 19:00
		// initialize transaction history list
		// here we may need some error feedback
		getTransactionList();
		if (tab2Active == null) tab2="false";
		else tab2="true";
		session.close();
		return "success-detail";
	}

	public String buy() throws IOException {
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		try {
			if (ActionContext.getContext().getSession().get("userId") == null)
				throw new Exception();
			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1)
				throw new Exception();
			account = session.selectOne("selectaccountbyuserid", userId);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "needlogin";
		}

		position = session.selectOne("selectPositionByPositionId", positionId);
		fundId = position.getFundId();
		fund = session.selectOne("selectfundbyfundid", fundId);
		if(fund.getIsLock()==1){
			feedback1 = "System is busy, please try later!";
			session.close();
			return "false";
		}
		if(buyamount==null||buyamount.equals("")){
			feedback1="Please input the amount";
			session.close();
			return "false";
		}
		if(!ValidationUtil.isNumber(buyamount)){
			feedback1="The amount should be a number";
			session.close();
			return "false";
		}
		if(Double.parseDouble(buyamount)<0.01||Double.parseDouble(buyamount)>100000000){
			feedback1="The amount should not be greater than 100,000,000 or less than 0.01";
			session.close();
			return "false";
		}
		

		long longForBuyAmount = (long) (Double.parseDouble(buyamount) * 100);
		long availBalance = account.getAvailBalance();
		if (longForBuyAmount > availBalance) {
			feedback1 = "You can buy at most $ " + availBalance / 100
					+ " of funds at most right now ! ";
			session.close();
			return "false";
		} else {
			account.setAvailBalance(account.getAvailBalance()-longForBuyAmount);
			account.setFrozenBalance(longForBuyAmount+ account.getFrozenBalance());

			Transaction trans = new Transaction();
			trans.setUserName(user.getUserName());
			trans.setFundId(fund.getFundId());
			trans.setUserId(userId);
			// transaction type 0 : buy fund
			trans.setTransactionType(0);
			trans.setFundName(fund.getFundName());
			trans.setAccountId(account.getAccountId());
			trans.setShares(0);
			trans.setAmount(longForBuyAmount);
			trans.setStatus(0);
			try {
				session.update("lockAccountTable", account.getAccountId());
				session.commit();
				session.insert("createTransaction", trans);
				session.update("updateAccount", account);
				session.update("unLockAccountTable", account.getAccountId());
				session.commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.update("unLockAccountTable", account.getAccountId());
				session.commit();
				feedback1 = "Unexpected error occurs when processing transaction.";
				return "false";
			} finally {
				setAccountInformation(userId);
				session.close();
			}

			feedback2 = "You have successfully bought $ " + buyamount
					+ " of funds!";
			return "finish";
		}
	}

	public String sell() throws IOException {
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		try {
			if (ActionContext.getContext().getSession().get("userId") == null)
				throw new Exception();
			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1)
				throw new Exception();
			account = session.selectOne("selectaccountbyuserid", userId);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "needlogin";
		}
		// get positionId from jsp
		try {
			position = session.selectOne("selectPositionByPositionId", positionId);
			if(position!=null&&position.getIsLock()==1){
				feedback1 = "System is busy, please try later!";
				session.close();
				return "false";
			}
			if(sellshares==null||sellshares.equals("")){
				feedback1="Please input the amount";
				session.close();
				return "false";
			}
			if(!ValidationUtil.isNumber(sellshares)){
				feedback1="The amount should be a number";
				session.close();
				return "false";
			}
			if(Double.parseDouble(sellshares)<0.001||Double.parseDouble(sellshares)>100000000){
				feedback1="The amount should not be greater than 100,000,000 or less than 0.01";
				session.close();
				return "false";
			}
			long longForSellShares = (long) (Double.parseDouble(sellshares) * 1000);
			long availShares = position.getAvailShares();
			if (longForSellShares > availShares) {
				feedback1 = "You can sell at most  " + availShares / 1000
						+ "  funds at most right now ! ";
				session.close();
				return "false";
			} else {
				// update the position and insert into transaction
				//1.lock the position table
				fund = session.selectOne("selectfundbyfundid",position.getFundId());
				session.update("lockPositionTable",positionId );
				session.commit();
				//2.update positon 
				position.setAvailShares(position.getAvailShares()-longForSellShares);
				position.setFrozenShares(position.getFrozenShares()+longForSellShares);
				session.update("updatePositionByTD",position);
				//3.insert transaction
				Transaction trans = new Transaction();
				trans.setAccountId(account.getAccountId());
				trans.setFundId(fund.getFundId());
				trans.setUserId(userId);
				trans.setUserName(user.getUserName());
				trans.setFundName(fund.getFundName());
				trans.setPositionId(positionId);
				trans.setTransactionType(1);
				trans.setShares(longForSellShares);
				trans.setAmount(0);
				session.update("createTransaction",trans);
				//4.unlock
				session.update("unLockPositionTable", positionId);
				session.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.update("unLockPositionTable", positionId);
			session.commit();
			feedback1 = "Unexpected error occurs when processing transaction.";
			session.close();
			return "false";
		}finally{
			setAccountInformation(userId);
			session.close();
		}
		feedback2 = "You have successfully sold " + sellshares + " funds!";
		return "finish";
	}

	public void setAccountInformation(int userId) {
		try {
			resource = "orm/configuration.xml";
			reader = Resources.getResourceAsReader(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			session = sessionFactory.openSession();
			Account acc = session.selectOne("selectAccountByUserId", userId);
			if (acc == null) {
				throw new Exception();
			} else {
				balance = FormatUtil.parseValue((double) acc.getBalance() / 100);
				frozen = FormatUtil.parseValue((double) acc.getFrozenBalance() / 100);
				available = FormatUtil.parseValue((double) acc.getAvailBalance() / 100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getLastTradingDay() {
		return lastTradingDay;
	}

	public void setLastTradingDay(String lastTradingDay) {
		this.lastTradingDay = lastTradingDay;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public List<Fund> getFunds() {
		return funds;
	}

	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}

	public List<PositionAndFund> getFundDetail() {
		return fundDetail;
	}

	public void setFundDetail(List<PositionAndFund> fundDetail) {
		this.fundDetail = fundDetail;
	}

	public String getResultList() {
		return resultList;
	}

	public void setResultList(String resultList) {
		this.resultList = resultList;
	}

	public List<TransactionVO> getTranList() {
		return tranList;
	}

	public void setTranList(List<TransactionVO> tranList) {
		this.tranList = tranList;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getFrozen() {
		return frozen;
	}

	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getFeedback1() {
		return feedback1;
	}

	public void setFeedback1(String feedback1) {
		this.feedback1 = feedback1;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	
	public String getTab2() {
		return tab2;
	}

	public String getTab2Active() {
		return tab2Active;
	}

	public void setTab2Active(String tab2Active) {
		this.tab2Active = tab2Active;
	}

	public void setTab2(String tab2) {
		this.tab2 = tab2;
	}

	public String getBuyamount() {
		return buyamount;
	}

	public void setBuyamount(String buyamount) {
		this.buyamount = buyamount;
	}

	public String getSellshares() {
		return sellshares;
	}

	public void setSellshares(String sellshares) {
		this.sellshares = sellshares;
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	public String getFeedback2() {
		return feedback2;
	}

	public void setFeedback2(String feedback2) {
		this.feedback2 = feedback2;
	}

}
