package action;

import java.io.IOException;
import java.io.Reader;
import java.util.List;


import model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.opensymphony.xwork2.ActionContext;

public class Login extends BaseAction{
	private int userId;
	private String userName;  
    private String passWord;
    private int role;
    private List<User> userlist;
    private String feedback1;
    private String feedback2;
    
    
    public String init() throws IOException{
		System.out.println("username in login="+userName);
		System.out.println("password in login="+passWord);
		String resource = "orm/configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		try{
			userlist = session.selectList("selectuserbyusername", userName);
			if(userlist.isEmpty()){ 
				feedback1="Register first please";
				session.close();
				return "loginfalse";
			}
			else{
				for (User a: userlist) {
					if(passWord.toString().equals(a.getPassWord())){   
						userId=a.getUserId();
						ActionContext.getContext().getSession().put("userName",userName);   
						ActionContext.getContext().getSession().put("userId",userId); 
						System.out.println(a.getRole());
						if(a.getRole()==0)
							return "success-customer";
						if(a.getRole()==1)
							return "success-admin";
					}
					else{
						feedback2="Incorrect password";
						return "loginfalse";
					}
				}
			}
		} finally {
			session.close();
		} 
		return "loginfalse";
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
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}	
	public String getFeedback1() {
		return feedback1;
	}

	public void setFeedback1(String feedback1) {
		this.feedback1 = feedback1;
	}

	public String getFeedback2() {
		return feedback2;
	}

	public void setFeedback2(String feedback2) {
		this.feedback2 = feedback2;
	}
	
	

}
