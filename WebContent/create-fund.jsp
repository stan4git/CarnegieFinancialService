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
						<span class="account-name">${userName}</span>
						<span class="account-role">Administrator</span>
					</div> <!-- /account-details -->
					
					
				
				</div> <!-- /account-container -->
				
				<hr />
				
				<ul id="main-nav" class="nav nav-tabs nav-stacked">
					
					<li>
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
					
					<li class="active">
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
					Create Fund					
				</h1>
				<div class="widget">
				<div class="widget-header">
					<i class="icon-user"></i>
					<h3>Fund Basic Information</h3>
				</div> <!-- /widget-header -->

				
				<div class="widget-content">
								
								
								
					<div class="tabbable">
						
						<br />
						
							<div class="tab-content">
							<span id="feedback" name="feedback1" style="color: blue">${feedBack1}</span><br/>
								<span id="feedback" name="feedback2" style="color: blue">${feedBack2}</span><br/>
								<form id="edit-profile" class="form-horizontal" action="cf_createFund" onsubmit="return check()" method="post">
									<fieldset>
						
										<div class="control-group">											
											<label class="control-label" for="fundName">Fund Name:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="fundName" name="fundName" value=""/>
												<br/><span id="mes1" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="ticker">Ticker:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="ticker" name="ticker" value=""/>
												<br/><span id="mes2" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										
										<div class="control-group">											
											<label class="control-label" for="description">Description:</label>
											<div class="controls">
												<input type="text" class="input-large disabled" id="description" name="description" value=""/>
												<br/><span id="mes3" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->
										
										<%-- <div class="control-group">											
											<label class="control-label" for="value">Value:</label>
											<div class="controls">
												<input type="text" class="input-medium disabled" id="fundValue" name="fundValue" value=""/>
												<br/><span id="mes4" style="color:red"></span>
											</div> <!-- /controls -->				
										</div> <!-- /control-group -->	 --%>
									</fieldset>
									<div style="padding-left:140px;">
									<button type="submit" class="btn btn-small btn-warning" style="width:60px;">
										Create
									</button>
									</div>
									
								</form>
								
								
								
								
						</div>
					</div>
				</div> <!-- /widget-content -->
			</div> <!-- /widget -->




				<div class="widget widget-table">
										
					<div class="widget-header">
						<i class="icon-th-list"></i>
						<h3>Funds</h3>
					</div> <!-- /widget-header -->
					
					<div class="widget-content">
					
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>#</th>
									<th>FUND NAME</th>
									<th>TICKER</th>
									<th>DESCRIPTION</th>
									<th style="text-align:right;padding-right: 20px;">VALUE</th>
								</tr>
							</thead>
							
							<tbody>
							<s:iterator value="FundVoList" status="stuts">
								<tr>
									<td>${stuts.count}</td>
									<td><s:property value="fundName" /></td>
									<td><s:property value="ticker" /></td>
									<td><s:property value="description" /></td>
									<td style="text-align:right;padding-right: 20px;"><s:property value="fundValue" /></td>
								</tr>
							</s:iterator>
							</tbody>
						</table>
					
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
function check()
{
	with(document.all){
		
		if(fundName.value==null||fundName.value==""){
			document.getElementById("mes1").innerHTML="fundName must be filled out!";
			document.getElementById("fundName").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes1").innerHTML="";
			document.getElementById("fundName").style.borderColor="#cccccc";
		}
			
		
		if(ticker.value==null||ticker.value==""){
			document.getElementById("mes2").innerHTML="ticker must be filled out!";
			document.getElementById("ticker").style.borderColor="RED";
			return false;
		}
		else
			{document.getElementById("mes2").innerHTML="";
			document.getElementById("ticker").style.borderColor="#cccccc";}
		
		if(description.value==null||description.value==""){
			document.getElementById("mes3").innerHTML="description must be filled out!";
			document.getElementById("description").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes3").innerHTML="";
			document.getElementById("description").style.borderColor="#cccccc";
		}
	}
}
</script>
  </body>
</html>