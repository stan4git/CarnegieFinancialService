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

<body>
	
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
						<span class="account-name">${sessionScope.userName}</span>
						<span class="account-role">Administrator</span>
					</div> <!-- /account-details -->
				
				</div> <!-- /account-container -->
				
				<hr />
				
				<ul id="main-nav" class="nav nav-tabs nav-stacked">
					<li class="active">
						<a href="./viewaccount_getUserInfo.action">
							<i class="icon-home"></i>
							View Customer Account
						</a>
					</li>
					
					<li>
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
					<i class="icon-home"></i>
					My Account					
				</h1>
				<div class="widget">
				<div class="widget-header">
					<i class="icon-user"></i>
					<h3>Change Password</h3>
				</div> <!-- /widget-header -->

				
				<div class="widget-content">
								
					<div class="tabbable">
						
						<br />
						
							<div class="tab-content">
								<form id="edit-profile" class="form-horizontal" action="changeemployeepassword_change" onsubmit="return check()" method="post">
									<fieldset>
										<div class="control-group">			
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span id="successMeg" style="color:blue">${feedback1}</span>			
										</div> <!-- /control-group -->
										<div class="control-group">			
																		
											<label class="control-label" for="currentpwd">Current Password</label>
											<div class="controls">
												<input type="password" class="input-medium" id="userPassword" name="userPassword"/>
												<br/>
												<span id="mes1" style="color:red">${feedback}</span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="newpwd">New Password</label>
											<div class="controls">
												<input type="password" class="input-medium" name="newPassword" id="newPassword" />
												<br/>
												<span id="mes2" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											<label class="control-label" for="confirmpwd">Confirm Password</label>
											<div class="controls">
												<input type="password" class="input-medium" name="confirmPassword" id="confirmPassword" />
												<br/>
												<span id="mes3" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
									</fieldset>
									<br/>
									<div class="pull-right" style="padding-right:520px;">
										<button type="submit" class="btn btn-warning btn-large">
											Submit
										</button>
									</div>
								</form>
								
								
								
								
						</div>
					</div>
				</div> <!-- /widget-content -->
			</div> <!-- /widget -->
				
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

<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/excanvas.min.js"></script>
<script src="./js/jquery.flot.js"></script>
<script src="./js/jquery.flot.pie.js"></script>
<script src="./js/jquery.flot.orderBars.js"></script>
<script src="./js/jquery.flot.resize.js"></script>


<script src="./js/bootstrap.js"></script>
<script src="./js/charts/bar.js"></script>

<script>
  /* zyj: validation*/
  function check()
	{
  	with(document.all){
  		
  		if(userPassword.value==null||userPassword.value==""){
  			
  			document.getElementById("mes1").innerHTML="User password must be filled out!"
  			document.getElementById("userPassword").style.borderColor="RED";
  			return false;
  		}
  		else{
  			document.getElementById("mes1").innerHTML=""  	  			
  			document.getElementById("userPassword").style.borderColor="#cccccc";
  		}	
  		if(newPassword.value==null||newPassword.value==""){
  			document.getElementById("mes2").innerHTML="New password must be filled out!"
  			document.getElementById("newPassword").style.borderColor="RED";
  			return false;
  		}
  		else{
  			document.getElementById("mes2").innerHTML=""
  	  		document.getElementById("newPassword").style.borderColor="#cccccc";
  		}	
  		if(confirmPassword.value==null||confirmPassword.value==""){
  			document.getElementById("mes3").innerHTML="Confirm password must be filled out!"
  			document.getElementById("confirmPassword").style.borderColor="RED";
  			return false;
  		}
  		else{
  			document.getElementById("mes3").innerHTML=""
  	  		document.getElementById("confirmPassword").style.borderColor="#cccccc";
  		}	
  		if(newPassword.value!=confirmPassword.value){
  			document.getElementById("mes3").innerHTML="Passwords shoud be the same"
  			document.getElementById("confirmPassword").style.borderColor="RED";
  			document.getElementById("newPassword").style.borderColor="RED";
  			newPassword.value = "";
  			confirmPassword.value = "";
  			return false;
  		}
  		else{
  			document.getElementById("mes3").innerHTML=""
  			document.getElementById("confirmPassword").style.borderColor="#cccccc";
  			document.getElementById("newPassword").style.borderColor="#cccccc";
  			return true;
  			} 	
		}
	}
  </script>

  </body>
</html>