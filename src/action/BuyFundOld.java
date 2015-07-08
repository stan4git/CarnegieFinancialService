package action;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Account;
import model.Fund;
import model.FundPriceHistory;
import model.Transaction;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import util.FormatUtil;
import vo.FundVO;

import com.opensymphony.xwork2.ActionContext;

public class BuyFundOld extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private int userId;
    private String ticker;
	private Account account;
	private int accountId;
	private long balance;
	private double buyamount; // the double number that inputed by the user in the form
	private Fund fund;
	private int fundId;
	private String fundName;
	private List<FundVO> fundSearchRes;
	private List<Fund> funds = new ArrayList<Fund>();
	private List<FundVO> doubleFundValues;
	private String result;
	private String feedback;

	public List<FundVO> getDoubleFundValues() {
		return doubleFundValues;
	}

	public void setDoubleFundValues(List<FundVO> doubleFundValues) {
		this.doubleFundValues = doubleFundValues;
	}

	public String init() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();
		if(ActionContext.getContext().getSession().get("userId")!=null){
			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userId").toString());
		}else{
			return "nulluser";
		}
		user = session.selectOne("selectuserbyuserid", userId);
		
		// get the balance of the user
		account = session.selectOne("selectaccountbyuserid", userId);
		balance = account.getBalance();
		// get all the funds selectAllFund
		funds = session.selectList("selectAllFund");
		List<FundVO> doubleFund = new ArrayList<FundVO> ();
		for (Fund f : funds){
			FundVO fvo = new FundVO();
			fvo.setFundId(f.getFundId());
			fvo.setFundName(f.getFundName());
			fvo.setFundValue(FormatUtil.parseValue(f.getFundValue()/100));
			System.out.println(fvo.getFundValue());
			doubleFund.add(fvo);
		}
		doubleFundValues = doubleFund;
		return "initSuccess"; 
	}

	//Michelle 01/28
	public String fundSearch() throws IOException, ParseException {
		System.out.println("search the fund =" + fundName);

		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();
		if(ActionContext.getContext().getSession().get("userId")!=null){
			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userId").toString());
		}else{
			return "nulluser";
		}
		user = session.selectOne("selectuserbyuserid", userId);
		
		try {
			if (fundName.equals("")&&ticker.equals("")){
				//do what?
			}else {
				Map<String, Object> param=new HashMap<String, Object>();
				param.put("fundName", fundName);
				param.put("ticker", ticker);
				Fund fund = session.selectOne("selectfundByMap", param);
				session.commit();
				param.clear();
				int fundId = fund.getFundId();
				String ticker = fund.getTicker();
				String fundName = fund.getFundName();
				param.put("fundId", fundId);
				List<FundVO> fundSearchResTmp = new ArrayList<FundVO>();
				List<FundVO> fundVoTemps = new ArrayList<FundVO>();
				fundSearchResTmp = session.selectList("selectfundByMap", param);
				session.commit();
				
				if(fundSearchResTmp.size() > 0){
					for(FundVO fundVoTmp:fundSearchResTmp){
						Fund fundForPrice = session.selectOne("selectPerFundHistory",fundVoTmp);
						FundVO fundVo = new FundVO();
						fundVo.setFundName(fundVoTmp.getFundName());
						fundVo.setTicker(fundVoTmp.getTicker());
						fundVo.setNewPrice(FormatUtil.parseShare(((double)fundForPrice.getFundValue()/100)));
//						fundVo.setFundValue(CoverNumUtil.priceToString(new Double(fundVoTmp.g.getPrice()/100.0)));
						fundVoTemps.add(fundVo);
					}
				}
				fundSearchRes = fundVoTemps;
			}
			
		} finally {
			session.close();
		}
		return "success-searchfund";
	}

	public String buyFund() throws IOException, ParseException {
		List<String> errorlist = new ArrayList<String>();
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();

		userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userId").toString());
		
		// get the balance of the user
		fund = session.selectOne("selectfundbyfundid",fundId);
		account = session.selectOne("selectaccountbyuserid", userId);
		accountId = account.getAccountId();
		balance = account.getBalance();
		double userAvailBalance = account.getAvailBalance();
		long longBuyAmount = (long) (buyamount * 100);
		if (userAvailBalance < buyamount) {
			feedback = "Your input is larger than your available balance";
			return "false";
		} else {
			// update the account table
			Account newAccount = new Account();
			newAccount.setAccountId(accountId);
			newAccount.setBalance(account.getBalance());
			newAccount.setFrozenBalance(longBuyAmount
					+ account.getFrozenBalance());
			newAccount.setAvailBalance(account.getBalance() - newAccount.getFrozenBalance());
			newAccount.setLastTradingDay(account.getLastTradingDay());
			newAccount.setUserId(account.getUserId());
			
			// update the transaction table
			Transaction trans = new Transaction();
			trans.setAccountId(accountId);
			trans.setFundId(fund.getFundId());
			trans.setUserId(userId);
			// transaction type 0 : buy fund
			trans.setTransactionType(0);
			trans.setAmount(longBuyAmount);
			trans.setFundName(fund.getFundName());
			trans.setShares(0);
//			trans.setPositionId(positionId);
			try {
				session.update("lockAccountTable",accountId);
				session.commit();
				session.insert("createTransaction", trans);
				session.update("updateAccount", newAccount);
				session.commit();
				session.update("unLockAccountTable",accountId);
				session.commit();
				} catch (Exception e) {
					e.printStackTrace();
					session.update("unLockAccountTable",accountId);
					session.commit();
					feedback = "Unexpected error occurs when processing transaction.";
					return "false";  
			} finally {
				session.close();
			}
		}
		feedback = "You have successfully bought "+buyamount+" the funds!";
//		resultlist.add("You have successfully bought "+buyamount+" the funds!"); 
//		System.out.println("result list = "+resultlist.size());
		return "finish";
	}

	public List<Fund> getFunds() {
		return funds;
	}

	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public double getBuyamount() {
		return buyamount;
	}

	public void setBuyamount(double buyAmount) {
		this.buyamount = buyAmount;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public List<FundVO> getFundSearchRes() {
		return fundSearchRes;
	}

	public void setFundSearchRes(List<FundVO> fundSearchRes) {
		this.fundSearchRes = fundSearchRes;
	}
	
}
