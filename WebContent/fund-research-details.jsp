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
	
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  </head>

<body>
	
<div class="navbar navbar-fixed-top">
	
	<div class="navbar-inner">
		
		<div class="container">
			
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 				
			</a>
			
			<a class="brand" href="./">Carnegie Financial Services</a>
			
			<div class="nav-collapse">
			
				<ul class="nav pull-right">
					
					<li class="divider-vertical"></li>
					
					<li class="dropdown">
						
						<a data-toggle="dropdown" class="dropdown-toggle " href="#">
							${sessionScope.userName} <b class="caret"></b>							
						</a>
						
						<ul class="dropdown-menu">
							<li>
								<a href="viewaccount_getInfo"><i class="icon-user"></i> View Account  </a>
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
		
			<div class="span9">
				
				<h1 class="page-title">
					<i class="icon-home"></i>
					Fund Research					
				</h1>
				<div class="widget">
				<div class="widget-header">
					<i class="icon-user"></i>
					<h3>Fund Information</h3>
				</div> <!-- /widget-header -->

				
				<div class="widget-content">
					<div class="tabbable">
						<br />
						<div class="tab-content">
								<form id="edit-profile" class="form-horizontal" action="fr_getFundList" method="post">
									<fieldset>
										<div class="control-group">											
											Fund Name:&nbsp;&nbsp;&nbsp;
											<input type="text" class="input-medium disabled" id="fundName" name="fundName" value="${fundName}" disabled/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											From:&nbsp;&nbsp;&nbsp;
											<input type="text" class="input-medium disabled" value="${startDate1}" disabled/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											To:&nbsp;&nbsp;&nbsp;
											<input type="text" class="input-medium disabled" value="${endDate1}" disabled/>
										</div> <!-- /control-group -->
										
										<div class="control-group">											
											Description:&nbsp;&nbsp;&nbsp;
											<input type="text" class="input-medium disabled" style="width:600px" value="${description}" disabled/>
											
										</div> <!-- /control-group -->
									</fieldset>
									
									<div style="padding-left:280px;">
									<button type="submit" class="btn btn-small btn-warning" style="width:130px;">
										Back to Search Fund
									</button>
									</div>
								</form>	
						</div>
					</div>
				</div> <!-- /widget-content -->
			</div> <!-- /widget -->
			
			<div class="widget">
					
					<div class="widget-header">
						<i class="icon-th-list"></i>
						<h3>Chart</h3>
					</div> <!-- /widget-header -->
														
					<div class="widget-content">
						
						<div id="chart_2" style="margin-left:30px;width:800px;height:400px;"></div>
										
					</div> <!-- /widget-content -->
					
				</div> <!-- /widget -->
				<s:iterator value="fundVos" status="stuts">
					<input id="fundDate" name="fundDate" type="hidden" value=<s:property value="transactionDate"/>/>
					<input id="fundPrice" name="fundPrice" type="hidden" value=<s:property value="fundValue"/>/>
				</s:iterator>
				
				<%-- <s:iterator value="positions">
					<input id="shares" name="shares" type="hidden" value=<s:property value="shares"/>/>
					<input id="userName" name="userName" type="hidden" value=<s:property value="userName"/>/>
				</s:iterator> --%>
				
			
			
			
			<%-- <div class="widget widget-table">
										
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
									<th>TRANSACTION DATE</th>
									<th>VALUE</th>
								</tr>
							</thead>
							
							<tbody>
							<s:iterator value="fundVos" status="stuts">
								<tr>
									<td>${stuts.count}</td>
									<td><s:property value="fundName" /></td>
									<td><s:property value="transactionDate" /></td>
									<td><s:property value="fundValue" /></td>
								</tr>
								<input id="fundDate" name="fundDate" type="hidden" value=<s:property value="transactionDate"/>/>
								<input id="fundPrice" name="fundPrice" type="hidden" value=<s:property value="fundValue"/>/>
							</s:iterator>
							</tbody>
						</table>
					
					</div> <!-- /widget-content -->
					
				</div> <!-- /widget --> --%> 

			
				
				
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
<script src="./js/jquery.flot.axislabels.js"></script>

<script>
	
		var fundPrices=[];
		var prices=document.getElementsByName("fundPrice");
		var date=document.getElementsByName("fundDate");
		for (var i = 0; i < prices.length; i += 1) {
			 fundPrices.push([new Date(date[i].value), parseFloat(prices[i].value)]);
        }
		var dataset = [{
            data: fundPrices,
            xaxis:1,
            color: "#0062E3",
            points: { fillColor: "#0062E3", show: true },
            lines: { show: true }
        },
		               {
		                   data: fundPrices,
		                   xaxis:2,
		                   color: "#0062E3",
		                   points: { fillColor: "#0062E3", show: true },
		                   lines: { show: true }
		               }];
		var dayOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat"];
		var options = {
			    series: {
			        shadowSize: 2
			    },
			    xaxes: [{
			        mode: "time",                
			        tickFormatter: function (val, axis) {
			            return dayOfWeek[new Date(val).getDay()];
			        },
			        color: "black",
			        position: "top",
			        tickSize: [1, "day"],
			        axisLabelUseCanvas: true,
			        axisLabelFontSizePixels: 12,
			        axisLabelFontFamily: 'Verdana, Arial',
			        axisLabelPadding: 5 
			    },
			    {
			        mode: "time",
			        tickSize: [1, "day"],
			        color: "black",        
			        axisLabel: "Date",
			        timeformat: "%y/%m/%d",
			        tickFormatter: function (val, axis) {
			            var d = new Date(val);
			            return d.toLocaleDateString();
			        },
			        axisLabelUseCanvas: true,
			        axisLabelFontSizePixels: 12,
			        axisLabelFontFamily: 'Verdana, Arial',
			        axisLabelPadding: 10
			    }],
			    yaxis: {        
			        color: "black",
			        tickDecimals: 2,
			        axisLabel: "Fund value in USD",
			        axisLabelUseCanvas: true,
			        axisLabelFontSizePixels: 12,
			        axisLabelFontFamily: 'Verdana, Arial',
			        axisLabelPadding: 5
			        
			    },
			    legend: {
			        noColumns: 0,
			        labelFormatter: function (label, series) {
			            return "<font color=\"white\">" + label + "</font>";
			        },
			        backgroundColor: "#000",
			        backgroundOpacity: 0.9,
			        labelBoxBorderColor: "#000000",
			        position: "nw"
			    },
			    grid: {
			        hoverable: true,
			        borderWidth: 3,
			        mouseActiveRadius: 50,
			        backgroundColor: { colors: ["#ffffff", "#EDF5FF"] },
			        axisMargin: 20
			    }
			};

			$(function () {
			    $.plot($("#chart_2"), dataset, options);
			    $("#chart_2").UseTooltip();
			});


			var previousPoint = null, previousLabel = null;
			var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

			$.fn.UseTooltip = function () {
			    $(this).bind("plothover", function (event, pos, item) {
			        if (item) {
			            if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
			                previousPoint = item.dataIndex;
			                previousLabel = item.series.label;
			                $("#tooltip").remove();
			                
			                var x = item.datapoint[0];
			                var y = item.datapoint[1];
			                var date = new Date(x);
			                var color = item.series.color;

			                showTooltip(item.pageX, item.pageY, color,
			                            "<strong>Value</strong><br>"  +
			                            (date.getMonth() + 1) + "/" + date.getDate() +
			                            " : <strong>" + y + "</strong> (USD)");
			            }
			        } else {
			            $("#tooltip").remove();
			            previousPoint = null;
			        }
			    });
			};

			function showTooltip(x, y, color, contents) {
			    $('<div id="tooltip">' + contents + '</div>').css({
			        position: 'absolute',
			        display: 'none',
			        top: y - 40,
			        left: x - 120,
			        border: '2px solid ' + color,
			        padding: '3px',
			        'font-size': '9px',
			        'border-radius': '5px',
			        'background-color': '#fff',
			        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
			        opacity: 0.9
			    }).appendTo("body").fadeIn(200);
			}

		

		
		

	</script> 


<script src="./js/bootstrap.js"></script>
<script src="./js/charts/bar.js"></script>

  </body>
</html>