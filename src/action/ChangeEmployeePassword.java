package action;

import java.io.IOException;
import java.io.Reader;

import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.opensymphony.xwork2.ActionContext;

public class ChangeEmployeePassword extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String resource;
	private Reader reader;
	private SqlSessionFactory sessionFactory;
	private SqlSession session;
	private User user;
	private int userId;
	private String feedback;
	private String feedback1;
	private String userPassword;
	private String newPassword;
	
	@Override
	public String init() throws IOException {
		resource = "orm/configuration.xml";
		reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sessionFactory.openSession();
		try {
			userId = Integer.parseInt(ActionContext.getContext()
					.getSession().get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() != 1) throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return "needlogin";
		} finally {
			session.close();
		}
		return "employee-init";
	}
	
	
	
	public String change() throws IOException {
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		try {
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userId").toString());
			user = session.selectOne("selectuserbyuserid", userId);
			if (user == null || user.getRole() != 1) throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return "needlogin";
		} 
		if(!user.getPassWord().equals(userPassword)){
			feedback="Your original password is incorrect!";
			session.close();
			return "fail-employee";
		}else{
			user.setUserId(userId);
			user.setPassWord(newPassword);
			try {
				session.update("updatepassword",user);
				session.commit();
			} catch (Exception e) {
				e.printStackTrace();
				feedback = "Error occurs when updating user information.";
				return "fail-employee";
			} finally {
				session.close();
			}
			feedback1="Your password has been changed successfully!";
			feedback="";
			return "success-employee";
		}
	}

	public User getUser() {
		return user;
	}

	public int getUserId() {
		return userId;
	}

	public String getFeedback() {
		return feedback;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



	public String getFeedback1() {
		return feedback1;
	}



	public void setFeedback1(String feedback1) {
		this.feedback1 = feedback1;
	}	
	
	
}
