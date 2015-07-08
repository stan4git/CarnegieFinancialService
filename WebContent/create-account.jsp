<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>Carnegie Financial Services</title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />    
    
    <link href="./css/bootstrap.min.css" rel="stylesheet" />
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet" />
    
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600" rel="stylesheet" />
    <link href="./css/font-awesome.css" rel="stylesheet" />
    
    <link href="./css/adminia.css" rel="stylesheet" /> 
    <link href="./css/adminia-responsive.css" rel="stylesheet" /> 
    
    <link href="./css/pages/dashboard.css" rel="stylesheet" /> 
    

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
	
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>

<body onload="active2()">
	
<div class="navbar navbar-fixed-top">
	
	<div class="navbar-inner">
		
		<div class="container">
			
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 				
			</a>
			
			<a class="brand" href="./viewaccount_getUserInfo.action">Carnegie Financial Services</a>
			
			<div class="nav-collapse">
			
				<ul class="nav pull-right">
					
					<li class="divider-vertical"></li>
					
					<li class="dropdown">
						
						<a data-toggle="dropdown" class="dropdown-toggle " href="#">
							${sessionScope.userName} <b class="caret"></b>							
						</a>
						
						<ul class="dropdown-menu">
							<li>
								<a href="changeemployeepassword_init"><i class="icon-lock"></i> Change Password</a>
							</li>
							
							<li class="divider"></li>
							
							<li>
								<a href="logout_out.action"><i class="icon-off"></i> Logout</a>
							</li>
						</ul>
					</li>
				</ul>
				
			</div> <!-- /nav-collapse -->
			
		</div> <!-- /container -->
		
	</div> <!-- /navbar-inner -->
	
</div> <!-- /navbar -->




<div id="content">
	
	<div class="container">
		
		<div class="row">
			
			<div class="span3">
				
				<div class="account-container">
				
					<div class="account-avatar">
						<img src="./img/headshot.png" alt="" class="thumbnail" />
					</div> <!-- /account-avatar -->
				
					<div class="account-details">
						<span class="account-name">${userName}</span>
						<span class="account-role">Administrator</span>
					</div> <!-- /account-details -->
				
				</div> <!-- /account-container -->
				
				<hr />
				
				<ul id="main-nav" class="nav nav-tabs nav-stacked">
					
					<li >
						<a href="./viewaccount_getUserInfo.action">
							<i class="icon-home"></i>
							View Customer Account
						</a>
					</li>
					
					<li class="active">
						<a href="./createcustomer_init">
							<i class="icon-gift"></i>
							Create Account	
						</a>
					</li>
					
					<li>
						<a href="./cf_init.action">
							<i class="icon-gift"></i>
							Create Fund	
						</a>
					</li>
					
					<li>
						<a href="./td_init.action">
							<i class="icon-signal"></i>
							Transition Day							
						</a>
					</li>	
				</ul>	
				
				<hr />
				
				<div class="sidebar-extra">
					<p>Contact Us: 1-80-CARNEGIE</p>
				</div> <!-- .sidebar-extra -->
				
				<br />
		
			</div> <!-- /span3 -->
			
			
			
			<div class="span9">
				
				<h1 class="page-title">
					<i class="icon-th-large"></i>
					Create Account					
				</h1>
				
				
				<div class="row">
					
					<div class="span9">
				
						<div class="widget">
						<div class="widget-content">
					
					<div class="tabbable">
						<ul class="nav nav-tabs">
						  <li id="1tab">
						    <a href="#1" data-toggle="tab">Create Customer</a>
						  </li>
						  <li id="2tab">
						  	<a href="#2" data-toggle="tab">Create Employee</a>
						  </li>
						</ul>
						
						<br />
						
							<!-- Customer -->
							<div class="tab-content">
<!-- 								<div class="tab-pane active" id="1"> -->
								<div class="tab-pane" id="1">
								<form id="edit-profile" class="form-horizontal" action="createcustomer_createCustomer" onsubmit="return checkCustomer()" method="post">
									<fieldset>
									<span id="feedback1" name="feedback1" style="color: blue">${feedback1 }</span><br/>
										<div class="control-group">
											<label class="control-label" for="userName1">Username</label>
											<div class="controls">
											<input type="text" class="input-medium disabled" name="userName1" id="userName1" />
											<br/>
											<span id="mes1" style="color:red"></span>
										</div>
									</div>
										
										<div class="control-group">											
											<label class="control-label" for="firstName1">First Name:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="firstName1" name="firstName1"/>
											<br/><span id="mes2" style="color:red"></span>	
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											<label class="control-label" for="lastName1">Last Name:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="lastName1" name="lastName1"/>
												<br/><span id="mes3" style="color:red"></span>	
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="password1">Password:</label>
											<div class="controls">
												<input type="password" class="input-medium disabled" id="password1" name="password1"/>
											<br/><span id="mes4" style="color:red"></span>	
											
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											<label class="control-label" for="addrline1">Address Line1:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="addrline1" name="addrline1" value=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->	
										
										<div class="control-group">											
											<label class="control-label" for="addrline1">Address Line2:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="addrline2" name="addrline2" value=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->	
										
										<div class="control-group">											
											<label class="control-label" for="city">City:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="city" name="city" value=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->	
										
										<div class="control-group">											
											<label class="control-label" for="state">State:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="state" name="state" value=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->	
										
										<div class="control-group">											
											<label class="control-label" for="zip">Zip:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="zip" name="zip" value=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->	
									</fieldset>
									<br/>
									<div style="padding-left:140px;">
									<button type="submit" class="btn btn-small btn-warning" style="width:60px;">
										Create
									</button>
									</div>
									
								</form>
								</div>
								
								
								<!-- employee -->
								<div class="tab-pane" id="2">
									<form id="edit-profile" class="form-horizontal" action="createemployee_createEmployee" onsubmit="return checkEmployee()" method="post">
									<fieldset>
										<span id="feedback2" name="feedback2" style="color: blue">${feedback2 }</span><br/>
										<div class="control-group">											
											<label class="control-label" for="userName2">Username:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="userName2" name="userName2"/>
												<br/><span id="mes5" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
						
										<div class="control-group">											
											<label class="control-label" for="firstName2">First Name:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="firstName2" name="firstName2"/>
												<br/><span id="mes6" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											<label class="control-label" for="lastName2">Last Name:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="lastName2" name="lastName2"/>
												<br/><span id="mes7" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											<label class="control-label" for="password2">Password:</label>
											<div class="controls">
												<input type="password" class="input-medium disabled" id="password2" name="password2"/>
												<br/><span id="mes8" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
									</fieldset>
									<br/>
									<div style="padding-left:140px;">
									<button type="submit" class="btn btn-small btn-warning" style="width:60px;">
										Create
									</button>
									</div>
									
								</form>
								</div>
								
							</div>
						  
						  
						</div>
								
								
								
								
							</div> <!-- /widget-content -->
							
						</div> <!-- /widget -->
						
					</div> <!-- /span9 -->
					
				</div> <!-- /row -->
				
			</div> <!-- /span9 -->
			
			
		</div> <!-- /row -->
		
	</div> <!-- /container -->
	
</div> <!-- /content -->
					
	
<div id="footer">
	
	<div class="container">				
		<hr />
		<p>&copy; 2014 Seagull Consulting.</p>
	</div> <!-- /container -->
	
</div> <!-- /footer -->


    

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/bootstrap.js"></script>

<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/excanvas.min.js"></script>
<script src="./js/jquery.flot.js"></script>
<script src="./js/jquery.flot.pie.js"></script>
<script src="./js/jquery.flot.orderBars.js"></script>
<script src="./js/jquery.flot.resize.js"></script>

<script src="./js/charts/bar.js"></script>
<script>

jQuery(document).ready(function() {
	CreateCustomer.init();
});

function active2() {
	var x = <s:property value="tab2"/>;
	if (x == true) {
		document.getElementById("1").className = "tab-pane";
		document.getElementById("1tab").className = "";
		document.getElementById("2").className = "tab-pane active";
		document.getElementById("2tab").className = "active";
		
	} else {
		document.getElementById("1").className = "tab-pane active";
		document.getElementById("1tab").className = "active";
		document.getElementById("2").className = "tab-pane";
		document.getElementById("2tab").className = "";
	}
}

function checkCustomer()
{
	with(document.all){
		if(userName1.value==null||userName1.value==""){
			document.getElementById("mes1").innerHTML="You must input username!";
			document.getElementById("userName1").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes1").innerHTML="";
			document.getElementById("userName1").style.borderColor="#cccccc";
		}
		
			
		
		if(firstName1.value==null||firstName1.value==""){
			document.getElementById("mes2").innerHTML="You must input First Name !";
			document.getElementById("firstName1").style.borderColor="RED";
			return false;
		}
		else
			{document.getElementById("mes2").innerHTML="";
			document.getElementById("firstName1").style.borderColor="#cccccc";}
		
		if(lastName1.value==null||lastName1.value==""){
			document.getElementById("mes3").innerHTML="You must input Last Name !";
			document.getElementById("lastName1").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes3").innerHTML="";
			document.getElementById("lastName1").style.borderColor="#cccccc";
		}
		
		
		if(password1.value==null||password1.value==""){
			document.getElementById("mes4").innerHTML="You must input password! ";
			document.getElementById("password1").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes4").innerHTML="";
			document.getElementById("password1").style.borderColor="#cccccc";
		}
	}
}



function checkEmployee()
{
	with(document.all){
		
		if(userName2.value==null||userName2.value==""){
			document.getElementById("mes5").innerHTML="You must input username !";
			document.getElementById("userName2").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes5").innerHTML="";
			document.getElementById("userName2").style.borderColor="#cccccc";
		}
			
		
		if(firstName2.value==null||firstName2.value==""){
			document.getElementById("mes6").innerHTML="You must input First Name !";
			document.getElementById("firstName2").style.borderColor="RED";
			return false;
		}
		else
			{document.getElementById("mes6").innerHTML="";
			document.getElementById("firstName2").style.borderColor="#cccccc";}
		
		if(lastName2.value==null||lastName2.value==""){
			document.getElementById("mes7").innerHTML="You must input Last Name !";
			document.getElementById("lastName2").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes7").innerHTML="";
			document.getElementById("lastName2").style.borderColor="#cccccc";
		}
		
		
		if(password2.value==null||password2.value==""){
			document.getElementById("mes8").innerHTML="You must input password! ";
			document.getElementById("password2").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes8").innerHTML="";
			document.getElementById("password2").style.borderColor="#cccccc";
		}
	}
}
</script>


  </body>
</html>
