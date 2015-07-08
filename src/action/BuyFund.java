package action;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.Fund;
import model.Position;
import model.Transaction;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import util.FormatUtil;
import util.ValidationUtil;
import vo.FundVO;

import com.opensymphony.xwork2.ActionContext;

public class BuyFund extends BaseAction {

	private static final long serialVersionUID = 1L;
	private User user;
	private int userId;
	private String ticker;
	private Account account;
	private int accountId;
	private long availBalance;
	private String available;
	private String balance;
	private String frozen;
	private String buyamount; // the double number that inputed by the user in
	private String firstName;
	private String lastName;
	private Fund fund;
	private FundVO fundVo;
	private String fundId;
	private String fundName;
	private String fundNameDisplay;
	private String fundVoValue;
	private List<Fund> funds = new ArrayList<Fund>();
	private String result;
	private String feedback1;
	private String feedback2;
	private List<FundVO> doubleFundValues;

	
	public String init() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		try {
			int userId = Integer.parseInt(ActionContext.getContext()
					.getSession().get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() != 0)
				throw new Exception();
			firstName = user.getFirstName();
			lastName = user.getLastName();
			account = session.selectOne("selectaccountbyuserid", userId);
			balance = FormatUtil
					.parseValue((double) account.getBalance() / 100);
			frozen = FormatUtil
					.parseValue((double) account.getFrozenBalance() / 100);
			available = FormatUtil.parseValue((double) account
					.getAvailBalance() / 100);
		} catch (Exception e) {
			session.close();
			return "nulluser";
		}
		
		// get all the funds selectAllFund
		funds = session.selectList("selectAllFund");
		List<FundVO> fundVoListTmp = new ArrayList<FundVO> ();
		for (Fund f : funds){
			FundVO fvo = new FundVO();
			fvo.setFundName(f.getFundName());
			fundVoListTmp.add(fvo);
		}
		doubleFundValues = fundVoListTmp;
		session.close();
		return "initSuccess";
	}

	// Michelle 01/28
	public String fundSearch() throws IOException, ParseException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		try {
			if (ActionContext.getContext().getSession().get("userId") != null) 
				userId = Integer.parseInt(ActionContext.getContext().getSession().get("userId").toString());
				user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1) 
				throw new Exception();
		} catch (Exception e) {
			session.close();
			return "nulluser";
		}
		
		try {
			if (fundName.equals("")) {
				feedback1 = "Please select a fund !";
				session.close();
				return "fail-searchfund";
			} else {
				Fund fundtmp = session.selectOne("selectfundbyfundname",fundName);
				session.commit();
				if (fundtmp == null) {
					feedback1 = "Oops... There is no such fund... ";
					session.close();
					return "fail-searchfund";
				} else {
					FundVO fundVoTmp = new FundVO();
					fundVoTmp.setFundNameDisplay(fundtmp.getFundName());
					fundVoTmp.setFundId(fundtmp.getFundId());
					fundVoTmp.setTicker(fundtmp.getTicker());
					fundVoTmp.setFundValue(FormatUtil.parseValue(new Double(fundtmp.getFundValue()/100.0d)));
					fundVo = fundVoTmp;
				}
			}
		} finally {
			session.close();
		}
		return "success-searchfund";
	}

	public String buyFund() throws IOException, ParseException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		fund = session.selectOne("selectfundbyfundid", fundId);
		if ( fund == null ){
			feedback1 = "You must select a fund !";
			session.close();
			return "false";
		}else if(fund.getIsLock()==1){
			feedback1 = "System is busy, please try later!";
			session.close();
			return "false";
		}
		userId = Integer.parseInt(ActionContext.getContext().getSession().get("userId").toString());
		user = session.selectOne("selectuserbyuserid", userId);
		if (user == null)
			return "nulluser";
		account = session.selectOne("selectaccountbyuserid", userId);
		accountId = account.getAccountId();
		availBalance = account.getAvailBalance();
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
		long longBuyAmount = (long) (Double.parseDouble(buyamount)*100);
		if(Double.parseDouble(buyamount)<0.01||Double.parseDouble(buyamount)>100000000){
			feedback1="The amount should not be greater than 100,000,000 or less than 0.01";
			session.close();
			return "false";
		}
		if (availBalance < longBuyAmount) {
			feedback1 = "There is no enough balance!";
			session.close();
			return "false";
		} else {
			Position searchCondition = new Position();
			searchCondition.setFundId(fund.getFundId());
			searchCondition.setUserId(userId);
			Position position = session.selectOne("selectPositionByCondition",searchCondition);
			int positionForTrans = 0;
			if(position!=null){
				positionForTrans = position.getPositionId();
			}
			// update the account table
			account.setAvailBalance(account.getAvailBalance()-longBuyAmount);
			account.setFrozenBalance(longBuyAmount+ account.getFrozenBalance());
			// update the transaction table
			Transaction trans = new Transaction();
			trans.setUserName(user.getUserName());
			trans.setFundId(fund.getFundId());
			trans.setUserId(userId);
			// transaction type 0 : buy fund
			trans.setTransactionType(0);
			trans.setFundName(fund.getFundName());
			trans.setAccountId(accountId);
			trans.setShares(0);
			trans.setAmount(longBuyAmount);
			trans.setStatus(0);
			trans.setPositionId(positionForTrans);
			
			try {
				session.update("lockAccountTable", accountId);
				session.commit();
				session.insert("createTransaction", trans);
				session.update("updateAccount", account);
				session.update("unLockAccountTable", accountId);
				session.commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.update("unLockAccountTable", accountId);
				session.commit();
				feedback1 = "Unexpected error occurs when processing transaction!";
				return "false";
			} finally {
				session.close();
			}
		}
		feedback2 = "You have successfully bought $ " + buyamount + " of funds!";
		return "finish";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBuyamount() {
		return buyamount;
	}

	public void setBuyamount(String buyAmount) {
		this.buyamount = buyAmount;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getfeedback1() {
		return feedback1;
	}

	public void setfeedback1(String feedback1) {
		this.feedback1 = feedback1;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public FundVO getFundVo() {
		return fundVo;
	}

	public void setFundVo(FundVO fundVo) {
		this.fundVo = fundVo;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public List<Fund> getFunds() {
		return funds;
	}

	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}
	
	public List<FundVO> getDoubleFundValues() {
		return doubleFundValues;
	}

	public void setDoubleFundValues(List<FundVO> doubleFundValues) {
		this.doubleFundValues = doubleFundValues;	
	}

	public String getFundVoValue() {
		return fundVoValue;
	}

	public void setFundVoValue(String fundVoValue) {
		this.fundVoValue = fundVoValue;
	}

	public long getAvailBalance() {
		return availBalance;
	}

	public void setAvailBalance(long availBalance) {
		this.availBalance = availBalance;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFundNameDisplay() {
		return fundNameDisplay;
	}

	public void setFundNameDisplay(String fundNameDisplay) {
		this.fundNameDisplay = fundNameDisplay;
	}
	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
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
	public String getFeedback2() {
		return feedback2;
	}

	public void setFeedback2(String feedback2) {
		this.feedback2 = feedback2;
	}
	
}
