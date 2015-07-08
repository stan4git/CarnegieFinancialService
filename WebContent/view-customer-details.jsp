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
					
						<div class="account-details">
						<span class="account-name">${sessionScope.userName}</span>
						<span class="account-role">Administrator</span>
					</div> <!-- /account-details -->
					
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
					View Customer Account					
				</h1>
				
				<div class="row">
					
					<div class="span9">
				
						<div class="widget">
						<div class="widget-content">
					
					<div class="tabbable">
						<ul class="nav nav-tabs">
						  <li id="1tab">
						    <a href="#1" data-toggle="tab">Basic Information</a>
						  </li>
						  <li id="2tab">
						  	<a href="#2" data-toggle="tab">Reset Password</a>
						  </li>
						</ul>
						
						<br />
						
							<div class="tab-content">
								<div class="tab-pane" id="1">
								<form id="edit-profile" class="form-horizontal" action="depositcheck_init.action" method="post">
								<span id="successMeg" style="color:blue">${feedback1}</span>
								<span id="successMeg" style="color:red">${feedback}</span>
									<fieldset>
										<div class="control-group">											
											<label class="control-label" for="firstname">Username</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="userName" name="userName" value="${user.userName}" disabled/>
												
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
						
										<div class="control-group">											
											<label class="control-label" for="firstname">First Name</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="firstname" value="${user.firstName}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="lastname">Last Name</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="lastname" value="${user.lastName}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="email">Currnt Address</label>
											<div class="controls">
												<input type="text" class="input-large disabled" id="address" value="${user.addrLine1}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											<label class="control-label" for="password1">Last Trading Day</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="tradingday" value="${lastTradingDay}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="password2">Cash Balance</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="balance" value="${balance}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->	
										<br/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;
										<s:hidden name="targetUser" value="%{user.userId}"></s:hidden>
										<button type="submit" class="btn btn-small btn-warning" style="margin-left:20px">Deposit Check</button>
										<a href="viewaccount_getUserInfo" class="btn btn-small btn-warning" style="text-align:left">Back to Homepage</a>
										
										
									</fieldset>
								</form>
								
								
								</div>
								
								<div class="tab-pane" id="2">
									<form class="form-horizontal" onsubmit="return check()" action="changecustomerpassword_changeCustomer" method="post">
									<fieldset>
										
										<span id="successMeg" style="color:blue">${feedback3}</span>
										<span id="failMsg" style="color:red">${feedback2}</span>
										<div class="control-group">											
											<label class="control-label " for="userName">Username</label>
											<div class="controls">
												<s:textfield cssClass="input-medium disabled" name="userName" value="%{user.userName}" readonly="true"/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">					
											<label class="control-label" for="newpwd">New Password</label>
											<div class="controls">
												<input type="password" class="input-medium" name="newPassword" id="newPassword" />
												<span id="mes2" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											<label class="control-label" for="confirmpwd">Confirm Password</label>
											<div class="controls">
												<input type="password" class="input-medium" name="confirmPassword" id="confirmPassword" />
												<span id="mes3" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
									</fieldset>
									<br/>
									<div style="padding-left:80px;">
									<button type="submit" class="btn btn-small btn-warning" style="width:60px;">
										Reset
									</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="viewaccount_getUserInfo" class="btn btn-small btn-warning" style="text-align:left">Back to Homepage</a>
									
									</div>
									
								</form>
								</div>
								
								
							</div>
						  
						  
						</div>
								
							</div> <!-- /widget-content -->
							
						</div> <!-- /widget -->
						
				<div class="widget widget-table">
										
					<div class="widget-header">
						<i class="icon-th-list"></i>
						<h3>Transaction History</h3>
					</div> <!-- /widget-header -->
					
					<div class="widget-content">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th style="text-align:center">TRANSACTION DATE</th>
									<th style="text-align:center">TRANSACTION TYPE</th>
									<th style="text-align:center">FUND NAME</th>
									<th style="text-align:center">SHARES</th>
									<th style="text-align:center">PRICE</th>
									<th style="text-align:center">AMOUNT</th>
									<th style="text-align:center">STATUS</th>
									<th style="text-align:center">ERROR MESSAGE</th>
								</tr>
							</thead>
							
							<tbody>
							<s:iterator value="tranList" var="tran">
								<tr>
									<td style="text-align:center"><s:property value="#tran.execuateDate"/></td>
									<td style="text-align:center"><s:if test="%{#tran.transactionType==0}">
										Buy Fund
										</s:if>
										<s:if test="%{#tran.transactionType==1}">
										Sell Fund
										</s:if>
										<s:if test="%{#tran.transactionType==2}">
										Withdrawal Check
										</s:if>
										<s:if test="%{#tran.transactionType==3}">
										Deposit Check
										</s:if>
									</td>	
									<td style="text-align:center">
										<s:property value="#tran.fundName"/>
									</td>
									<td style="text-align:right">
										<s:property value="#tran.shares"/>
									</td>
									<td style="text-align:right">
										<s:property value="#tran.price"/>
									</td> 
									<td style="text-align:right">
										<s:property value="#tran.amount"/>
									</td>
									<td style="text-align:center"><s:if test="%{#tran.status==0}">
										Pending
										</s:if>
										<s:if test="%{#tran.status==1}">
										Success
										</s:if>
										<s:if test="%{#tran.status==2}">
										Failed
										</s:if>
									</td>
									<td style="text-align:center">
										<s:property value="#tran.errorMsg"/>
									</td>
								</tr>
							</s:iterator>
							</tbody>
						</table>
					
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
<script src="./js/excanvas.min.js"></script>
<script src="./js/jquery.flot.js"></script>
<script src="./js/jquery.flot.pie.js"></script>
<script src="./js/jquery.flot.orderBars.js"></script>
<script src="./js/jquery.flot.resize.js"></script>

<script src="./js/bootstrap.js"></script>
<script src="./js/charts/bar.js"></script>

<script>
// 	stan: tab switch
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


  /* zyj: validation*/
  function check()
	{
  	with(document.all){
  		
  		
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