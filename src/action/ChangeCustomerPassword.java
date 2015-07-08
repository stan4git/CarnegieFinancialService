package action;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;

import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class ChangeCustomerPassword extends BaseAction{
	private static final long serialVersionUID = 1L;
	private User user;
	private int userId;
	private String userName;
	private String userName1;
	private String userPassword;
	private String newPassword;
    private String confirmPassword;
    private String feedback;
    private String feedback1;
    private String feedback2;
    private String feedback3;
    private String resource;
    private Reader reader;
    private SqlSessionFactory sessionFactory;
    private SqlSession session;
	private String tab2Active;
	private String firstName;
	private String lastName;
    
    public String init() throws IOException {
    	resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		try {
			int userId = Integer.parseInt(ActionContext.getContext()
					.getSession().get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() != 0) throw new Exception();
			firstName = user.getFirstName();
			lastName = user.getLastName();
		} catch (Exception e) {
			e.printStackTrace();
			return "needlogin";
		} finally {
			session.close();
		}
		return "customer-init";
    }
    
	public String change() throws IOException{
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		try {
			userId = Integer.parseInt(ActionContext.getContext().getSession().get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() != 0) throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "needlogin";
		} 
		if(!user.getPassWord().equals(userPassword)){
			feedback="Your original password is incorrect!";
			session.close();
			return "fail-customer";
		}else{
			user.setUserId(userId);
			user.setPassWord(newPassword);
			try {
				session.update("updatepassword",user);
				session.commit();
			} catch (Exception e) {
				e.printStackTrace();
				feedback = "Error occurs when updating user information.";
				return "fail-customer";
			} finally {
				session.close();
			}
			feedback="";
			feedback1="Your password has been changed successfully!";
			return "success-customer";
		}
	}
	
	public String changeCustomer() throws IOException{
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		try {
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() != 1) throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "needlogin";
		} 
		
		HttpServletRequest request=ServletActionContext.getRequest();
		String username=request.getParameter("userName");
		user=session.selectOne("selectuserbyusername", username);
		if (user == null) {
			feedback2 ="Invaild user name. Please contact system admin.";
			tab2Active="true";
			session.close();
			return "fail-EV-customer";
		}
		ActionContext.getContext().getSession().put("customerId",user.getUserId());  
		try {
			userId = user.getUserId();
			user.setPassWord(newPassword);
			session.update("updatepassword", user);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tab2Active="true";
			feedback2 = "Error occurs when updating user information.";
			return "fail-EV-customer";
		} finally {
			session.close();
		}
		tab2Active="true";
		feedback2="";
		feedback3="Your password has been changed successfully!";
		return "success-EV-customer";
	}

	public String cancel(){
		return "cancel";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getFeedback1() {
		return feedback1;
	}

	public void setFeedback1(String feedback1) {
		this.feedback1 = feedback1;
	}

	public String getUserName1() {
		return userName1;
	}

	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}


	public String getFeedback2() {
		return feedback2;
	}

	public String getFeedback3() {
		return feedback3;
	}

	public void setFeedback2(String feedback2) {
		this.feedback2 = feedback2;
	}

	public void setFeedback3(String feedback3) {
		this.feedback3 = feedback3;
	}

	public String getTab2Active() {
		return tab2Active;
	}

	public void setTab2Active(String tab2Active) {
		this.tab2Active = tab2Active;
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
