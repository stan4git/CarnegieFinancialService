package action;

import java.io.IOException;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport{
	
	public abstract String init() throws IOException;

}
