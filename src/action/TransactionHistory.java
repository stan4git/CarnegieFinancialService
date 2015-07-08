package action;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import model.Account;
import model.Transaction;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import util.FormatUtil;
import vo.TransactionVO;

import com.opensymphony.xwork2.ActionContext;

public class TransactionHistory extends BaseAction {
	private static final long serialVersionUID = 1330808390174898482L;
	private List<TransactionVO> tranList;
	private int userId;
	private User user;
	private String firstName;
	private String lastName;
	private String resource;
	private String feedBack;
	private String balance;
	private String frozen;
	private String available;
	Reader reader;
	SqlSessionFactory sessionFactory;
	SqlSession session;
	
	
	@Override
	public String init() throws IOException {
		//initialize environment
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		
		//validate user access
		try {
			String userObject = ActionContext.getContext().getSession().get("userId").toString();
			if (userObject == null || userObject == "") 
				return "login";
			//setup headre information
			userId = Integer.parseInt(userObject);
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() == 1)
				throw new Exception();
			firstName = user.getFirstName();
			lastName = user.getLastName();
		} catch (Exception e) {
			e.printStackTrace();
			feedBack = "Invalid user or user not found.";
			session.close();
			return "login";
		}
		
		if(getTransactionList()) return "initSuccess";
		else return "fail";
	}
	
	public boolean getTransactionList() throws IOException {
		//initialize environment
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		
		List<Transaction> temp;
		try {
			temp = session.selectList("selectTransactionsByUserId", userId);
			if (temp == null) {
				feedBack = "User not found.";
				return false;
			}
		} catch (Exception e) {
			feedBack = "Error occurs when fetching user information.";
			e.printStackTrace();
			session.close();
			return false;
		}
		
		//stan edited model and parsing 1/26 8:25
		List<TransactionVO> LTVO = new LinkedList<TransactionVO>();
		for (Transaction t : temp) {
			TransactionVO tvo = new TransactionVO();
			tvo.setAccountId(t.getAccountId());
			tvo.setAmount(FormatUtil.parseValue((double)t.getAmount()/100));
			tvo.setExecuateDate((t.getExecuteDate() == null) ? "" : DateUtil.dateToString(t.getExecuteDate()));
			tvo.setFundName(t.getFundName());
			tvo.setPrice(FormatUtil.parseValue((double)t.getPrice()/100));
			tvo.setShares(FormatUtil.parseShare((double)t.getShares()/1000));
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

	public void setAccountInformation(int userId) {
		try {
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
			feedBack = "Unexcepted error occurs when fetching account data.";
		} finally {
			session.close();
		}
		return;
	}
	
	public List<TransactionVO> getTranList() {
		return tranList;
	}

	public void setTranList(List<TransactionVO> tranList) {
		this.tranList = tranList;
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
