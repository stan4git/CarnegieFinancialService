package action;

import java.io.IOException;
import java.io.Reader;

import model.Account;
import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CreateEmployee extends ActionSupport {
	private static final long serialVersionUID = -5409986436069897554L;

	private String userName2;
	private String firstName2;
	private String lastName2;
	private String password2;
	private String feedback1;
	private String feedback2;
	private int userId;
	private User loginUser;
	private String tab2;
	

	public String init() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();
		// the user must be logged in as an employee
		try {
			String userObject = ActionContext.getContext().getSession()
					.get("userId").toString();
			if (userObject == null || userObject == "") 
				throw new Exception();
			setUserId(Integer.parseInt(userObject));
			loginUser = session.selectOne("selectuserbyuserid", userId);
			if (loginUser == null || loginUser.getRole() == 0) 
				throw new Exception();
			return "initSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "nulluser";
		} finally {
			session.close();
		}
	}

	public String createEmployee() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		SqlSession session = sessionFactory.openSession();
		
		try {
			String userObject = ActionContext.getContext().getSession().get("userId").toString();
			if (userObject == null || userObject == "") 
				throw new Exception();
			setUserId(Integer.parseInt(userObject));
			loginUser = session.selectOne("selectuserbyuserid", userId);
			if (loginUser == null || loginUser.getRole() == 0) 
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "nulluser";
		}
		
		User user;
		try {
			user = session.selectOne("selectuserbyusername", userName2);
			if (user != null)
				throw new Exception();
		} catch (Exception e){
			feedback2 = "The username already exists !";
			tab2="true";
			session.close();
			return "fail";
		}

		user = new User();
		user.setUserName(userName2);
		user.setFirstName(firstName2);
		user.setLastName(lastName2);
		user.setPassWord(password2);
		user.setRole(1);

		try {
			session.insert("insertUser", user);
			session.commit();
			session.commit();
			user = session.selectOne("selectuserbyusername", userName2);
			if (user == null) {
				feedback2 = "The account isn't created successfully...Try again later!";
				tab2="true";
				return "fail";
			} else {
				Account newAccount = new Account();
				newAccount.setUserId(user.getUserId());
				newAccount.setAvailBalance(0);
				newAccount.setBalance(0);
				newAccount.setFrozenBalance(0);

				feedback2 = "Empolyee account has been created successfully!";
				tab2="true";
				session.insert("insertAccount", newAccount);
				session.commit();
			}
		} catch (Exception e) {
			feedback2 = "The account isn't created successfully...Try again later!";
			tab2="true";
			e.printStackTrace();
			return "fail";
		} finally {
			session.close();
		}
		return "success";
	}

	public String getFirstName2() {
		return firstName2;
	}

	public String getLastName2() {
		return lastName2;
	}

	public String getPassword2() {
		return password2;
	}

	public void setFirstName2(String firstName2) {
		this.firstName2 = firstName2;
	}

	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getUserName2() {
		return userName2;
	}

	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTab2() {
		return tab2;
	}

	public void setTab2(String tab2) {
		this.tab2 = tab2;
	}

	public String getFeedback2() {
		return feedback2;
	}

	public void setFeedback2(String feedback2) {
		this.feedback2 = feedback2;
	}
}
