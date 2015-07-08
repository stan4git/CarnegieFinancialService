package action;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import model.Fund;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.opensymphony.xwork2.ActionContext;

import util.CheckBadChar;
import util.FormatUtil;
import vo.FundVO;

public class CreateFund extends BaseAction {

	private static final long serialVersionUID = 3339569870142714000L;
	private int fundId;
	private String fundName;
	private String ticker;
	private String description;
	private double fundValue;
	private String feedBack1;
	private String feedBack2;
	private List<Fund> fundList;
	private List<FundVO> FundVoList;
	private int userId;
	private User user;

	@Override
	public String init() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();

		try {
			String userObject = ActionContext.getContext().getSession()
					.get("userId").toString();
			if (userObject == null || userObject == "") 
				throw new Exception();
			setUserId(Integer.parseInt(userObject));
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 0) 
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "login";
		} 
		
		
		fundList = session.selectList("selectAllFund");
		session.commit();
		List<FundVO> FundVoListTemp = new ArrayList<FundVO>();
		if(fundList.size()>0){
			for(Fund fund:fundList){
				FundVO fundvo = new FundVO();
				long valueTemp = fund.getFundValue();
				fundvo.setFundValue(FormatUtil.parseValue((double)(valueTemp/100)));
				fundvo.setFundName(fund.getFundName());
				fundvo.setTicker(fund.getTicker());
				fundvo.setDescription(fund.getDescription());
				FundVoListTemp.add(fundvo);
			}
		}
		FundVoList = FundVoListTemp;
		session.close();
		return "initSuccess";
	}

	public String createFund() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();
		try {
			Fund fundtemp1 = session.selectOne("selectfundbyfundname", CheckBadChar.fixBadChars(fundName));
			Fund fundtemp2 = session.selectOne("selectfundbytickerforJudge",CheckBadChar.fixBadChars(ticker));
			if(fundtemp1!=null){
				feedBack1="The Fundname has been used, please type another Fund Name!";
				return "failure";
			}else if(fundtemp2!=null){
				feedBack1="The ticker has been used, please type anohter ticker!";
				return "failure";
			}else{
				Fund fundView = new Fund();
				fundView.setFundName(CheckBadChar.fixBadChars(fundName));
				fundView.setTicker(CheckBadChar.fixBadChars(ticker));
				fundView.setDescription(CheckBadChar.fixBadChars(description));
				fundValue = FormatUtil.getdouble(fundValue);
				fundView.setFundValue((long)(fundValue*100));
				session.insert("insertFund", fundView);
				session.commit();
				fundList = session.selectList("selectAllFund");
				//Fund fund = session.selectOne("selectFundByCondition", searchCondition);
				session.commit();
				List<FundVO> FundVoListTemp = new ArrayList<FundVO>();
				if(fundList.size()>0){
					for(Fund fund:fundList){
						FundVO fundvo = new FundVO();
						long valueTemp = fund.getFundValue();
						fundvo.setFundValue(FormatUtil.parseValue((double)(valueTemp/100)));
						fundvo.setFundName(fund.getFundName());
						fundvo.setTicker(fund.getTicker());
						fundvo.setDescription(fund.getDescription());
						FundVoListTemp.add(fundvo);
					}
				}
				FundVoList = FundVoListTemp;
			}
		} finally {
			session.close();
		}
		feedBack2="The fund has been created successfully!";
		return "success";
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getFundValue() {
		return fundValue;
	}

	public void setFundValue(double fundValue) {
		this.fundValue = fundValue;
	}

	public List<Fund> getFundList() {
		return fundList;
	}

	public void setFundList(List<Fund> fundList) {
		this.fundList = fundList;
	}

	public List<FundVO> getFundVoList() {
		return FundVoList;
	}

	public void setFundVoList(List<FundVO> fundVoList) {
		FundVoList = fundVoList;
	}

	public String getFeedBack1() {
		return feedBack1;
	}

	public void setFeedBack1(String feedBack1) {
		this.feedBack1 = feedBack1;
	}

	public String getFeedBack2() {
		return feedBack2;
	}

	public void setFeedBack2(String feedBack2) {
		this.feedBack2 = feedBack2;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
