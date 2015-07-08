package action;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.struts2.ServletActionContext;

import util.FormatUtil;
import util.ValidationUtil;
import vo.FundVO;

import com.opensymphony.xwork2.ActionContext;

import model.Account;
import model.Fund;
import model.Position;
import model.Transaction;
import model.User;

public class SellFund {
	private int userId;
	private User user;
	private Fund fund;
	private FundVO fundVo;
	private int fundId;
	private String fundName;
	private Account account;
	private Position position;
	private int positionId;
	private long availShares;
	private String availSharesStr;
	private String newShare;
	private String sellsharesStr;
	private long sellsharesLong;
	private String feedback1;
	private List<Position> positions = new ArrayList<Position>();
	private List<FundVO> fundVos;
	private String firstName;
	private String lastName;
	private String available;
	private String balance;
	private String frozen;
	private String userName;

	

	public String init() throws IOException {
		// use two tables, position -> which user has which funds; and
		// fundpricehistory -> check the bought price and the current price
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		try {
			if (ActionContext.getContext().getSession().get("userId") != null) {
				userId = Integer.parseInt(ActionContext.getContext().getSession()
						.get("userId").toString());
			} else {
				session.close();
				return "nulluser";
			}
			user = session.selectOne("selectuserbyuserid",userId);
			if (user == null || user.getRole() == 1)
				throw new Exception();
			firstName = user.getFirstName();
			lastName = user.getLastName();
			account=session.selectOne("selectaccountbyuserid",userId);
			balance = FormatUtil.parseValue((double) account.getBalance() / 100);
			frozen = FormatUtil.parseValue((double) account.getFrozenBalance() / 100);
			available = FormatUtil.parseValue((double) account.getAvailBalance() / 100);
			positions = session.selectList("selectAllPositions", userId);
		} catch (Exception e) {
			session.close();
			return "nulluser";
		}
		if (positions != null && positions.size() > 0) {
			fundVos = new ArrayList<FundVO>();
			for (Position p : positions) {
				// get the fundid
				int fundid = p.getFundId();
				Fund fund = session.selectOne("selectfundbyfundid", fundid);
				FundVO fvo = new FundVO();
				fvo.setFundId(fundid);
				fvo.setFundName(fund.getFundName());
				fvo.setPositionId(p.getPositionId());
				fundVos.add(fvo);
			}
		}
		session.close();
		return "initSuccess";
	}
	
	public String fundSearch() throws IOException, ParseException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		
		try {
		if (ActionContext.getContext().getSession().get("userId") != null) {
			userId = Integer.parseInt(ActionContext.getContext().getSession().get("userId").toString());
		} else {
			return "nulluser";
		}
		user = session.selectOne("selectuserbyuserid", userId);
		firstName = user.getFirstName();
		lastName = user.getLastName();
		if (fundName.equals("") || fundName == null){
			feedback1 = "Please select a fund !";
			return "fail-searchfund";
		}
		positions = session.selectList("selectAllPositions", userId);
		if (positions != null && positions.size() > 0) {
			fundVos = new ArrayList<FundVO>();
			for (Position p : positions) {
				int fundid = p.getFundId();
				Fund fund = session.selectOne("selectfundbyfundid", fundid);
				FundVO fvo = new FundVO();
				fvo.setFundId(fundid);
				fvo.setFundName(fund.getFundName());
				fvo.setPositionId(p.getPositionId());
				fundVos.add(fvo);
			}
		}
		fund = session.selectOne("selectfundbyfundname",fundName);
		fundId = fund.getFundId();

		Position positionTmp = new Position();
		positionTmp.setFundId(fund.getFundId());
		positionTmp.setUserId(userId);
		Position positionTmp2 = session.selectOne("selectPositionByCondition",positionTmp);
		positionId = positionTmp2.getPositionId();
		availShares=positionTmp2.getAvailShares();
		availSharesStr=FormatUtil.parseShare(new Double(availShares/1000.0d));
		FundVO fvo = new FundVO();
		fvo.setFundId(fundId);
		fvo.setFundName(fund.getFundName());
		fvo.setNewPrice(FormatUtil.parseValue(new Double(fund.getFundValue() / 100.0d)));
		fvo.setNewShare(availSharesStr);
		fvo.setPositionId(positionId);
		fvo.setTicker(fund.getTicker());
		fundVo=fvo;
		
		} finally {
			session.close();
		}
		return "success-searchfund";
	}
	// public sell fund
	public String sellFund() throws IOException, ParseException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();
		try{
		if (ActionContext.getContext().getSession().get("userId") != null) {
			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userId").toString());
		} else {
			return "nulluser";
		}
		if (positionId < 1 ){
			feedback1 = "You must select a fund !";
			return "false";
		}
		if(sellsharesStr==null||sellsharesStr.equals("")){
			feedback1="Please input the amount";
			session.close();
			return "false";
		}
		if(!ValidationUtil.isNumber(sellsharesStr)){
			feedback1="The amount should be a number";
			session.close();
			return "false";
		}
		if(Double.parseDouble(sellsharesStr)<0.001||Double.parseDouble(sellsharesStr)>100000000){
			feedback1="The amount should not be greater than 100,000,000 or less than 0.01";
			session.close();
			return "false";
		}
		position = session.selectOne("selectPositionByPositionId", positionId);
		if(position!=null&&position.getIsLock()==1){
			feedback1 = "System is busy, please try later!";
			return "false";
		}
		
		user = session.selectOne("selectuserbyuserid", userId);
		userName=user.getUserName();
		account = session.selectOne("selectaccountbyuserid", userId);
		availShares=position.getAvailShares();
		sellsharesLong=(long)(Double.parseDouble(sellsharesStr)*1000);
		if(availShares<sellsharesLong){
			feedback1 = "Your input is larger than your shares!";
			return "false";
		}
		fundId = position.getFundId();
		try {
			//1.lock the position table
			fund = session.selectOne("selectfundbyfundid",fundId);
			session.update("lockPositionTable",positionId );
			session.commit();
			//2.update positon 
			position.setAvailShares(position.getAvailShares()-sellsharesLong);
			position.setFrozenShares(position.getFrozenShares()+sellsharesLong);
			session.update("updatePositionByTD",position);
			//3.insert transaction
			Transaction trans = new Transaction();
			trans.setAccountId(account.getAccountId());
			trans.setFundId(fundId);
			trans.setUserId(userId);
			trans.setUserName(userName);
			trans.setFundName(fund.getFundName());
			trans.setPositionId(positionId);
			trans.setTransactionType(1);
			trans.setShares(sellsharesLong);
			trans.setAmount(0);
			session.update("createTransaction",trans);
			//4.unlock
			session.update("unLockPositionTable", positionId);
			session.commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			session.update("unLockPositionTable", positionId);
			session.commit();
		}

		}catch(Exception e){
				e.printStackTrace();
			} finally {
				session.close();
			}
		feedback1 = "You have successfully sold the fund!";
		return "finish";
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public long getAvailShares() {
		return availShares;
	}

	public void setAvailShares(long availShares) {
		this.availShares = availShares;
	}

	public String getfeedback1() {
		return feedback1;
	}

	public void setfeedback1(String feedback1) {
		this.feedback1 = feedback1;
	}

	public FundVO getFundVo() {
		return fundVo;
	}

	public void setFundVo(FundVO fundVo) {
		this.fundVo = fundVo;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public String getAvailSharesStr() {
		return availSharesStr;
	}

	public void setAvailSharesStr(String availSharesStr) {
		this.availSharesStr = availSharesStr;
	}

	public String getNewShare() {
		return newShare;
	}

	public void setNewShare(String newShare) {
		this.newShare = newShare;
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

	public List<FundVO> getFundVos() {
		return fundVos;
	}

	public void setFundVos(List<FundVO> fundVos) {
		this.fundVos = fundVos;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSellsharesStr() {
		return sellsharesStr;
	}

	public void setSellsharesStr(String sellsharesStr) {
		this.sellsharesStr = sellsharesStr;
	}

}
