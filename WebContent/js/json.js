$(document).ready(function(){
	
	$("#getList").click(function(){
		$.getJSON("jsontest!returnList.action",function(data){
			$("#message").html("");
			$.each(data.fundInfosList,function(i,value){
				$("#message").append("<div>"+(i+1)+"</div>")
						 .append("<div><font color='red'>Id"+value.priceDate+"</font></div>")
					     .append("<div><font color='red'>UserName"+value.price+"</font></div>");
			});
		});
	});
	
//	$("#getList").click(function(){
//		var params = $("form").serialize();
//		$.ajax({
//			url:"jsontest!gainUserInfo.action",
//			type:"POST",
//			data:params,
//			dataType:"json",
//			success:function(data){
//				
//			$("#message").html("");
//			
//			$("#message").append("<div><font color='red'>�û�ID��"+data.userInfo.userId+"</font></div>")
//					     .append("<div><font color='red'>�û���"+data.userInfo.userName+"</font></div>")
//					     .append("<div><font color='red'>���룺"+data.userInfo.password+"</font></div>")
//			}
//		});
//	});
});