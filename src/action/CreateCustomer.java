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

public class CreateCustomer extends ActionSupport {
	private static final long serialVersionUID = -5409986436069897554L;
	
	private String userName1;
	private String firstName1;
	private String lastName1;
	private String password1;
	private String addrline1;
	private String addrline2;
	private String city;
	private String state;
	private String zip;
	private String feedback1;
	private int userId;
	private User loginUser;
	private String tab2;
	
	public String init() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		// the user must be logged in as an employee
		try {
			String userObject = ActionContext.getContext().getSession()
					.get("userId").toString();
			if (userObject == null || userObject == "")
				throw new Exception();
			setUserId(Integer.parseInt(userObject));
			loginUser = session.selectOne("selectuserbyuserid", userId);
			if (loginUser == null || loginUser.getRole() == 0) {
				throw new Exception();
			}
			tab2 = "false";
			return "initSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "nulluser";
		} finally {
			session.close();
		}
	}
			
	
	public String createCustomer() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		//the user must be logged in as an employee
		
		try {
			String userObject = ActionContext.getContext().getSession()
					.get("userId").toString();
			if (userObject == null || userObject == "") {
				throw new Exception();
			}
			setUserId(Integer.parseInt(userObject));
			loginUser = session.selectOne("selectuserbyuserid", userId);
			if (loginUser == null || loginUser.getRole() == 0) 
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "nulluser";
		} 
			
		
		//user name must be unique
		User newUser;
		try {
			newUser = session.selectOne("selectuserbyusername", userName1);
			if (newUser != null)
				throw new Exception();
		} catch (Exception e) {
			feedback1 = "The username already exists !";
			tab2 = "false";
			session.close();
			return "fail";
		} 
		//username or password or firstname or lastname cannot be null -> hasn't done
		//if( username == null ){}
		
		User user = new User();
		user.setUserName(userName1);
		user.setFirstName(firstName1);
		user.setLastName(lastName1);
		user.setPassWord(password1);
		user.setAddrLine1(addrline1);
		user.setAddrLine2(addrline2);
		user.setCity(city);
		user.setState(state);
		user.setZip(zip);
		user.setRole(0);
		
		
//		newAccount.setUserId(user.ger)
		try {
			session.insert("insertUser", user);
			session.commit();
			newUser = session.selectOne("selectuserbyusername", userName1);
			if (newUser == null) {
				feedback1 = "The account isn't created successfully...Try again later!";
				tab2 = "false";
				return "fail";
			} else {
				Account newAccount = new Account();
				newAccount.setUserId(user.getUserId());
				newAccount.setAvailBalance(0);
				newAccount.setBalance(0);
				newAccount.setFrozenBalance(0);
				
				feedback1 = "Customer account has been created successfully!";
				tab2 = "false";
				session.insert("insertAccount", newAccount);
				session.commit();
			}
		} catch (Exception e) {
			feedback1 = "The account isn't created successfully...Try again later!";
			tab2 = "false";
			e.printStackTrace();
			return "fail";
		} finally {
			session.close();
		}
		tab2 = "false";
		return "success";
	}

	public String getUserName1() {
		return userName1;
	}

	public String getFirstName1() {
		return firstName1;
	}

	public String getLastName1() {
		return lastName1;
	}

	public String getPassword1() {
		return password1;
	}

	public String getAddrline1() {
		return addrline1;
	}

	public String getAddrline2() {
		return addrline2;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}

	public void setFirstName1(String firstName1) {
		this.firstName1 = firstName1;
	}

	public void setLastName1(String lastName1) {
		this.lastName1 = lastName1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public void setAddrline1(String addrline1) {
		this.addrline1 = addrline1;
	}

	public void setAddrline2(String addrline2) {
		this.addrline2 = addrline2;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getFeedback1() {
		return feedback1;
	}


	public void setFeedback1(String feedback1) {
		this.feedback1 = feedback1;
	}


	public String getTab2() {
		return tab2;
	}


	public void setTab2(String tab2) {
		this.tab2 = tab2;
	}
	
}
