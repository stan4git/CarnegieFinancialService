package action;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import model.Transaction;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import util.FormatUtil;
import vo.TransactionVO;

import com.opensymphony.xwork2.ActionContext;

public class CustomerTransactionHistory extends BaseAction {
	private static final long serialVersionUID = -4433857501338814229L;
	//stan 1/26 8:00 P.M edit username field
	private String username;
	private List<TransactionVO> tranList;
	private String result;
	private int userId = -1;
	private User user;
	private String firstName;
	private String lastName;
	String resource ;
	Reader reader ;
	SqlSessionFactory sessionFactory;
	SqlSession session;
	
	//stan 1/26 8:00 P.M added init()
	@Override
	public String init() throws IOException {
		try {
			resource = "orm/configuration.xml";
			reader = Resources.getResourceAsReader(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			session = sessionFactory.openSession();
			userId = Integer.parseInt(ActionContext.getContext().getSession().get("userId").toString());
			user=session.selectOne("selectuserbyuserid", userId);
			firstName=user.getFirstName();
			lastName=user.getLastName();
		} catch (Exception e) {
			session.close();
			return "login";
		}
		if (username == null) {
			session.close();
			return "initSuccess";
		}
		if (getCustomerTransactionHistory())
			return "success";
		else
			return "fail";
	}
	
	public boolean getCustomerTransactionHistory() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		
		List<Transaction> temp;
		List<TransactionVO> LTVO = new LinkedList<TransactionVO>();
		try {
			String uname = username;
			temp = session.selectList("selectTransactions", uname);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return false;
		}
		if (temp == null) {
			tranList = LTVO ;
			result = "Unexpected problem occurs when fetching data.";
			session.close();
			return false;
		}
		//stan edited model and parsing 1/26 8:25
		for (Transaction t : temp) {
			TransactionVO tvo = new TransactionVO();
			tvo.setAccount(t.getAccount());
			tvo.setAccountId(t.getAccountId());
			tvo.setAmount(FormatUtil.parseValue((double)t.getAmount()/100));
			tvo.setExecuateDate(
					(t.getExecuteDate() == null)? "" : DateUtil.dateToString(t.getExecuteDate()));
			tvo.setFundName(t.getFundName());
			tvo.setPrice(FormatUtil.parseValue((double)t.getPrice()/100));
			tvo.setShares(FormatUtil.parseShare((double)t.getShares()/1000));
			tvo.setStatus(t.getStatus());
			tvo.setTransactionId(t.getTransactionId());
			tvo.setTransactionType(t.getTransactionType());
			tvo.setUserId(t.getUserId());
			LTVO.add(tvo);
		}
		tranList = LTVO;
		session.close();
		return true;
	}
	

	public List<TransactionVO> getTranList() {
		return tranList;
	}

	public void setTranList(List<TransactionVO> tranList) {
		this.tranList = tranList;
	}

	//stan 1/26 8:00 P.M edited getter and setter
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
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

}
