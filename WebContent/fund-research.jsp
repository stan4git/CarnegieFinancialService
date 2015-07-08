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
    <link href="./css/jquery-ui.css" rel="stylesheet" /> 
    <link href="./css/base/all.css" rel="stylesheet" >
    
    
    

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
	
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>

<body onload="pickDate()">
	
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
								<a href="logout_out.logout_out.action"><i class="icon-off"></i> Logout</a>
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
						<span class="account-name">${firstName} ${lastName}</span>
						<span class="account-role">Balance:${balance}</span>
						<span class="account-role">Frozen:${frozen}</span>
						<span class="account-role">Available:${available}</span>
					</div> <!-- /account-details -->
				
				</div> <!-- /account-container -->
				
				<hr />
				
				<ul id="main-nav" class="nav nav-tabs nav-stacked">
					
					<li>
						<a href="./viewaccount_init.action">
							<i class="icon-home"></i>
							My Account 		
						</a>
					</li>
					
					<li>
						<a href="./buyfund_init.action">
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
					
					<li class="active">
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
			
			
			
			<!-- div class="span9">
				
				<h1 class="page-title">
					<i class="icon-home"></i>
					My Account					
				</h1>
				<div class="widget">
				<div class="widget-header">
					<i class="icon-user"></i>
					<h3>Basic Information</h3>
				</div> <!-- /widget-header --> 
			
			
			
			<div class="span9">
				
				<h1 class="page-title">
					<i class="icon-home"></i>
					Fund Research					
				</h1>
				<span name="feedback" style="color:red">${feedback}</span>
				<div class="widget">
				<div class="widget-header">
					<i class="icon-user"></i>
					<h3>Search Fund</h3>
				</div> <!-- /widget-header -->

				
				<div class="widget-content">
					<div class="tabbable">
						<br />
							<div class="tab-content">
								<form id="edit-profile" onsubmit="return check()" class="form-horizontal" action="fr_fundResearch" method="post">
									<fieldset>
										<%-- <div class="control-group">			
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span id="successMeg" style="color:blue">${feedback}</span>			
										</div> <!-- /control-group --> --%>
												
										<div class="control-group">		
											Fund Name:&nbsp;&nbsp;&nbsp;
											<input type="text" class="input-medium disabled" id="fundName" name="fundName"/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(or)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											Ticker:&nbsp;&nbsp;&nbsp;
											<input type="text" class="input-medium disabled" id="ticker" name="ticker"/>
											<span id="mes1" style="color:red"></span>
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											Start Date:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="text" class="input-medium disabled" id="datepicker" name="startDate"/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											End Date (Include):&nbsp;&nbsp;&nbsp;
											<input type="text" class="input-medium disabled" id="datepicker1" name="endDate"/>
										</div> <!-- /control-group -->
									</fieldset>
									
									
									<div style="padding-left:230px;">
									<button type="submit" class="btn btn-small btn-warning" style="width:70px;">
										Search
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
									<th></th>
									<th>FUND NAME</th>
									<th>TICKER</th>
									<th>DESCRIPTION</th>
								</tr>
							</thead>
							
							
							<tbody>
							<s:iterator value="doubleFundValues" status="stuts">
								<tr>
									<td>${stuts.count}</td>
									<td><s:property value="fundName" /></td>
									<td><s:property value="ticker" /></td>
									<td><s:property value="description" /></td>
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
<script src="./js/jquery-ui.js"></script>

<script>
function pickDate(){
	$("#datepicker").datepicker();
	$("#datepicker1").datepicker();
}

function compareDay(a,b){
    var a1 = a.split("/"); 
    var b1 = b.split("/"); 
    var d1 = new Date(a1[0],a1[1],a1[2]); 
    var d2 = new Date(b1[0],b1[1],b1[2]); 
	var msg;
    if(Date.parse(d1) - Date.parse(d2) == 0){ //a==b 
       msg= 0; 
    } 
    if (Date.parse(d1) - Date.parse(d2) < 0) {//a>b 
       msg= 1; 
    } 
    if (Date.parse(d1) - Date.parse(d2) > 0) {//a<b 
       msg= -1; 
    } 
    alert(msg);
	return msg;
} 


function check()
{
	with(document.all){
		if((fundName.value==null||fundName.value=="")&&(ticker.value==null||ticker.value=="")){
			document.getElementById("mes1").innerHTML="Fund name or Ticker must be filled out!";
			document.getElementById("fundName").style.borderColor="RED";
			document.getElementById("ticker").style.borderColor="RED";
			return false;
		}
		else{
			document.getElementById("mes1").innerHTML="";
			document.getElementById("userName").style.borderColor="#cccccc";
		}
			
		
		if(compareDate(startDate.value,endDate.value)>0){
			document.getElementById("mes1").innerHTML="Start Date must be earlier than End Date";
			return false;
		}
		else{
			document.getElementById("mes1").innerHTML="";
		} 
	}
		
}



</script>

<script src="./js/bootstrap.js"></script>
<script src="./js/charts/bar.js"></script>

  </body>
</html>