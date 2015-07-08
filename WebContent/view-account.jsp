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
			
			<a class="brand" href="./viewaccount_init.action">Carnegie Financial Services</a>
			<div class="nav-collapse">
				<ul class="nav pull-right">
					<li class="divider-vertical"></li>
					<li class="dropdown">
						<a data-toggle="dropdown" class="dropdown-toggle " href="#">
							${sessionScope.userName} <b class="caret"></b>							
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="viewaccount_init.action"><i class="icon-user"></i> View Account  </a>
							</li>
							
							<li>
								<a href="changecustomerpassword_init"><i class="icon-lock"></i> Change Password</a>
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
						<span class="account-name" >${firstName} ${lastName}</span>
						<span class="account-role">Balance: ${balance}</span>
						<span class="account-role">Frozen: ${frozen}</span>
						<span class="account-role">Available: ${available}</span>
					</div> <!-- /account-details -->
				
				</div> <!-- /account-container -->
				
				<hr />
				
				<ul id="main-nav" class="nav nav-tabs nav-stacked">
					
					<li class="active">
						<a href="./viewaccount_init.action">
							<i class="icon-home"></i>
							My Account 		
						</a>
					</li>
					
					<li>
						<a href="./buyfund_init.action" >
							<i class="icon-gift"></i>
							Buy Fund	
						</a>
					</li>
					
					<li>
						<a href="./sellfund_init.action">
							<i class="icon-file"></i>
							Sell Fund	
						</a>
					</li>
					
					<li>
						<a href="./requestcheck_init.action">
							<i class="icon-check"></i>
							Request Check	
						</a>
					</li>
					
					<li>
						<a href="./transactionhistory_init.action">
							<i class="icon-time"></i>
							Transaction History	
						</a>
					</li>
					
					<li>
						<a href="./fr_getFundList.action">
							<i class="icon-signal"></i>
							Research Fund							
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
					<h1 class="page-title"><i class="icon-home"></i>My Account</h1>	
				<div class="widget">
					<div class="widget-header">
					<i class="icon-user"></i>
					<h3>Basic Information</h3>
					</div> <!-- /widget-header -->
					
					<div class="widget-content">
					<div class="tabbable">
						<br />
							<div class="tab-content">
							
								<form id="edit-profile" class="form-horizontal" >
									<fieldset>
						
										<div class="control-group">											
											<label class="control-label" for="firstname">First Name</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="firstname" value="${firstName}" disabled/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="lastname">Last Name</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="lastname" value="${lastName}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="email">Currnt Address</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="address" value="${addrLine1}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											<label class="control-label" for="password1">Last Trading Day</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="tradingday" value="${lastTradingDay}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="password2">Available Balance</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="balance" value="${available}" disabled=""/>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->	
									</fieldset>
								</form>
								
								
								
								
						</div>
					</div>
				</div> <!-- /widget-content -->
				</div> <!-- /widget -->
				
				<span id="feedback" name="feedback1" style="color: red">${feedback1}</span><br/>
				<span id="feedback" name="feedback2" style="color: blue">${feedback2}</span><br/>
				
				<div class="widget widget-table">
										
					<div class="widget-header">
						<i class="icon-th-list"></i>
						<h3>Funds</h3>
					</div> <!-- /widget-header -->
					
					<div class="widget-content">
					
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th style="text-align:center;">#</th>
									<th style="text-align:center;">FUND NAME</th>
									<th style="text-align:center;">TICKER</th>
									<th style="text-align:center;">SHARE</th>
									<th style="text-align:center;">VALUE</th>
									<th style="text-align:center;">OPERATION</th>
								</tr>
							</thead>
							
							<tbody>
							<s:iterator value="fundDetail" status="stuts">
								<tr>
									<td>${stuts.count}</td>
									<td><s:property value="fundName" /></td>
									<td><s:property value="ticker" /></td>
									<td  style="text-align: right"><s:property value="shares" /></td>
									<td  style="text-align: right"><s:property value="sharesxValue" /></td>
									<!-- td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											Buy								
										</a>&nbsp;&nbsp;&nbsp;					
										<a href="javascript:;" class="btn btn-small">
											Sell					
										</a>
									</td> -->
									
									<td class="action-td">
										<form id="buy" class="form-horizontal" onsubmit="return buy()" action="viewaccount_buy.action" method="post" >
											<input type="text" id="buyamount" class="input-small disabled" name="buyamount" />
										 	<input type="hidden" id="userId" class="input-medium disabled" name="userId" value="${sessionScope.userId}" />
											<input type="hidden" id="positionId" class="input-medium disabled" name="positionId" value="${positionId}" />
											<div class="control-group" id="requestCheckBtnContainer">											
													<input type="submit" style="margin-top: 10px;" class="btn btn-warning" value="Buy"/>
											</div>
										</form>
									</td>
									
									<td class="action-td">
										<form id="sell" class="form-horizontal" onsubmit="return sell()" action="viewaccount_sell.action" method="post" >
											<input type="text" id="sellshares" class="input-small disabled" name="sellshares" />
										 	<input type="hidden" id="userId" class="input-medium disabled" name="userId" value="${sessionScope.userId}" />
											<input type="hidden" id="positionId" class="input-medium disabled" name="positionId" value="${positionId}" />
											<div class="control-group" id="requestCheckBtnContainer">											
													<input type="submit" style="margin-top: 10px;" class="btn btn-warning" value="Sell"/>
											</div>
										</form>
									</td>
									
								</tr>
							</s:iterator>
							</tbody>
						</table>
					
					</div> <!-- /widget-content -->
					
				</div> <!-- /widget -->
				<!-- /span9 -->
			</div> <!-- /span9 -->
			
			
		</div> <!-- /row -->
		
	</div> <!-- /container -->
	
</div> <!-- /content -->
	
	<div id="footer">
		<div class="container">
			<hr />
			<p>&copy; 2014 Seagull Consulting.</p>
		</div>
		<!-- /container -->
	</div>
	<!-- /footer -->




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


	<script src="./js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="./js/login.js" type="text/javascript"></script>
	
	<script>

		jQuery(document).ready(function() {     
		  ViewAccount.init();
		});
		
		function buy() {
			with(document.all){
				
			var buyamount = document.getElementById("sellshares");
			if (buyamount.value==null||buyamount.value=="") {
				document.getElementById("feedback1").innerHTML="The amount must be filled out!";
				document.getElementById("buyamount").style.borderColor="RED";
				return false;
			}
			else if(isNaN(buyamount.value)){
				document.getElementById("feedback1").innerHTML="The amount must be a number!";
				document.getElementById("buyamount").style.borderColor="RED";
				return false;
			}
			else if (buyamount.value > 1000000){
				document.getElementById("feedback1").innerHTML="Your input is larger than permitted!";
				document.getElementById("buyamount").style.borderColor="RED";
				return false;
			}
			else if (buyamount.value < 0.01){
				document.getElementById("feedback1").innerHTML="Your input is smaller than permitted!";
				document.getElementById("buyamount").style.borderColor="RED";
				return false;
			}
			else{
				document.getElementById("buyamount").innerHTML="";
				document.getElementById("buyamount").style.borderColor="#cccccc";
			} 
			}
		}
		
		function sell() {
			with(document.all){
				
			var sellshares = document.getElementById("sellshares");
			if (sellshares.value==null||sellshares.value=="") {
				document.getElementById("feedback1").innerHTML="The amount must be filled out!";
				document.getElementById("sellshares").style.borderColor="RED";
				return false;
			}
			else if(isNaN(sellshares.value)){
				document.getElementById("feedback1").innerHTML="The amount must be a number!";
				document.getElementById("sellshares").style.borderColor="RED";
				return false;
			}
			else if (sellshares.value > 1000000){
				document.getElementById("feedback1").innerHTML="Your input is larger than permitted!";
				document.getElementById("sellshares").style.borderColor="RED";
				return false;
			}
			else if (sellshares.value < 0.001){
				document.getElementById("feedback1").innerHTML="Your input is smaller than permitted!";
				document.getElementById("sellshares").style.borderColor="RED";
				return false;
			}
			else{
				document.getElementById("sellshares").innerHTML="";
				document.getElementById("sellshares").style.borderColor="#cccccc";
			} 
			}
		}
	</script>

</body>
</html>