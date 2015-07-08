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
import model.Position;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import util.FormatUtil;
import vo.FundVO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FundResearch extends ActionSupport{

	private static final long serialVersionUID = 6431539247377556370L;
	private String fundName;  
    private String ticker;
    private String startDate;
    private String endDate;
    private String startDate1;
    private String endDate1;
    private String description;
    private List<FundVO> fundVos;
    private List<FundVO> doubleFundValues;
    private List<FundPriceHistory> fphs;
    private List<String> errorlist; 
    private List<Fund> funds;
    private List<Position> positions;
	private User user;
	private String firstName;
	private String lastName;
	private int userId;
	private String feedback;
	private String balance;
	private String frozen;
	private String available;
	private String resource;
	private Reader reader;
	private SqlSessionFactory sessionFactory;
	private SqlSession session;
	private int targetFund;
	
	public String getFundList() throws IOException, ParseException{
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
    	
		try {
			String userObject;
			userObject = ActionContext.getContext().getSession().get("userId").toString();
			if (userObject == null) throw new Exception();
			userId = Integer.parseInt(userObject);
			user=session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1) throw new Exception();
			firstName=user.getFirstName();
			lastName=user.getLastName();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "login";
		}
		
		
		funds = session.selectList("selectAllFund");
		if (funds == null) {
			feedback = "Fund not found.";
			session.close();
			return "fail";
		}
		System.out.println("funds size:"+funds.size());
		List<FundVO> doubleFund = new ArrayList<FundVO> ();
		for (Fund f : funds){
			FundVO fvo = new FundVO();
			fvo.setFundId(f.getFundId());
			fvo.setTicker(f.getTicker());
			fvo.setDescription(f.getDescription());
			fvo.setFundName(f.getFundName());
			System.out.println("fvo:"+fvo.getFundValue());
			doubleFund.add(fvo);	
		}
		doubleFundValues = doubleFund;
		setAccountInformation(userId);
		session.close();
		return "success-fund";
		
    }
    
	
	public String fundResearch() throws IOException, ParseException{
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		
		try {
			String userObject;
			userObject = ActionContext.getContext().getSession().get("userId").toString();
			if (userObject == null) throw new Exception();
			userId = Integer.parseInt(userObject);
			user=session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1) throw new Exception();
			firstName=user.getFirstName();
			lastName=user.getLastName();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "login";
		}
		
		try{
//				if (targetFund != 0) {
//					
//				} else {
//					
//				}
				Map<String, Object> param=new HashMap<String, Object>();
				param.put("fundName", fundName);
				param.put("ticker", ticker);
				
				Fund fund = session.selectOne("selectfundByMap", param);
				if (fund == null)  {
					feedback = "Fund not found.";
					session.close();
					return "fail";
				}
				param.clear();
				int fundId = fund.getFundId();
				String fundName = fund.getFundName();
				positions=session.selectList("selectPositionByFundName",fundName);
				param.put("fundId", fundId);
				startDate1=startDate;
				endDate1=endDate;
				description=fund.getDescription();
				if(startDate.compareTo(endDate)>0){
					feedback="Start day should be earlier than end day";
					return "fail";
					
				}
				if(startDate!=null&&!startDate.equals("")){
					param.put("startDate",DateUtil.stringToDate(startDate));
				}
				if(endDate!=null&&!endDate.equals("")){
					param.put("endDate", DateUtil.stringToDate(endDate));
				}
				fphs = session.selectList("selectfundHistoryByMap", param);
				session.commit();
				List<FundVO> fundVoTemps = new ArrayList<FundVO>();
				if(fphs.size()>0){
					for(FundPriceHistory fph:fphs){
						FundVO fundVo = new FundVO();
						fundVo.setFundName(fundName);
						fundVo.setTransactionDate(DateUtil.dateToString(fph.getPriceDate()));
						fundVo.setFundValue(CoverNumUtil.priceToString(new Double(fph.getPrice()/100.0)));
						fundVoTemps.add(fundVo);
					}
				}
				fundVos = fundVoTemps;
				setAccountInformation(userId);
		}  finally{
			session.close();
		} 
		return "success-research";
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
	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<FundVO> getFundVos() {
		return fundVos;
	}
	public void setFundVos(List<FundVO> fundVos) {
		this.fundVos = fundVos;
	}
	
	public List<FundPriceHistory> getFphs() {
		return fphs;
	}

	public void setFphs(List<FundPriceHistory> fphs) {
		this.fphs = fphs;
	}

	public List<String> getErrorlist() {
		return errorlist;
	}

	public void setErrorlist(List<String> errorlist) {
		this.errorlist = errorlist;
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


	public String getFeedback() {
		return feedback;
	}


	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}


	public String getStartDate1() {
		return startDate1;
	}


	public void setStartDate1(String startDate1) {
		this.startDate1 = startDate1;
	}


	public String getEndDate1() {
		return endDate1;
	}


	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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


	public int getTargetFund() {
		return targetFund;
	}


	public void setTargetFund(int targetFund) {
		this.targetFund = targetFund;
	}
	
	public List<Position> getPositions() {
		return positions;
	}


	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}



}
