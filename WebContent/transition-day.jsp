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
								<a href="./"><i class="icon-off"></i> Logout</a>
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
					
					<li>
						<a href="./cf_init.action">
							<i class="icon-gift"></i>
							Create Fund	
						</a>
					</li>
					
					<li class="active">
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
					Transition Day					
				</h1>
				
				<div class="widget widget-table">
					
					<form id="edit-profile" class="form-horizontal" onsubmit="return checkDate()" action="td_executeTransiting" method="post"> 
					
					<div class="widget-content">
					<span id="feedBack" name="feedBack1" style="color: red">${feedBack1 }</span><br/>
					<br/>	
					<span id="feedBack" name="feedBack2" style="color: blue">${feedBack2 }</span><br/>
					<br/>	
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;															
					Last Transaction Day:&nbsp;&nbsp;&nbsp;<input type="text" style="width:150px" id="transactionDate" name="transactionDate" readonly value="${transactionDate}"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					Set New Transaction Day:&nbsp;&nbsp;&nbsp;<input type="text" style="width:150px" id="newDate" name="newDate" readonly value="${newDate}"/> 
					<br/><p>							
					<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>#</th>
									<th>FUND NAME</th>
									<th>TICKER</th>
									<th>VALUE</th>
									<th>SET NEW PRICE</th>
								</tr>
							</thead>
							<tbody>
							
							<s:iterator value="fundVolist" status="stat">
							<s:hidden name="fundVolist[%{#stat.index}].fundId" value="%{fundVolist[#stat.index].fundId}"/>			
								<tr>
									<td>${stat.count}</td>
									<td><s:textfield cssStyle="width:100px" theme="simple"  readonly="true" name="fundVolist[%{#stat.index}].fundName" value="%{fundVolist[#stat.index].fundName}"/></td>
									<td><s:textfield cssStyle="width:100px" theme="simple"  readonly="true" name="fundVolist[%{#stat.index}].ticker" value="%{fundVolist[#stat.index].ticker}"/></td>
									<td><s:textfield cssStyle="width:100px" theme="simple"  readonly="true" name="fundVolist[%{#stat.index}].fundValue" value="%{fundVolist[#stat.index].fundValue}"/></td>
									<td><s:textfield cssStyle="width:120px" theme="simple"  name="fundVolist[%{#stat.index}].newPrice" onchange="checkNumFormate(this);" value=""/></td>
								</tr>
							</s:iterator>
							
							</tbody>	
						</table>	
					</div> <!-- /widget-content -->
					<br/>
					
					<div class="pull-right">
							<button style="width:60px;height:30px;" type="submit" class="btn btn-small btn-warning">
								Submit
							</button>
						</div>
						
							</form>
					
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
function checkDate()
{
	var newDateValue = document.getElementById("newDate").value;
	if(newDateValue==null||newDateValue==""){
		alert("The new Transaction Day must not null!");
		document.getElementById("newDate").focus();
		return false;
	}
	var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/;
	if(!date_regex.test(newDateValue)){
		alert("Each part of the Date must be number and must following the format \"MM/dd/yyyy\"!");
		document.getElementById("newDate").focus();
		return false;
	}
    return date_regex.test(newDateValue);
}

function checkNumFormate(Field){
	var inputNumValue = Field.value;
	//1. is null
	if(inputNumValue==null||inputNumValue==''){
		alert("Please fill with this price!");
		Field.value = "";
		Field.focus();
		return;
	}
	//2.not a number
	if(isNaN(inputNumValue)){
		alert("Please type a number!");
		Field.value = "";
		Field.focus();
		return;
	}
	//3. is it smaller than 0.01
	var inputValue = parseFloat(inputNumValue);
	if(inputValue<0.01){
		alert("The price must greater than 0.01!");
		Field.value = "";
		Field.focus();
		return;
	}else if(inputValue>1000000.00){
		//4. is it greater than 1000000.00
		alert("The price must smaller than 1000000.00!");
		Field.value = "";
		Field.focus();
		return;
	}
}


</script>
  </body>
</html>