<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 获取项目名称 -->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
	<head>
		<title>各品类TOP5排行  </title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	    <meta name="format-detection" content="telephone=no">
	    <meta name="format-detection" content="email=no">
	    <meta name="author" content="Cleam Lee"> 
		<link rel="stylesheet" type="text/css" href="${ctx }/resources/top5/css/initialize.css" />
		<link rel="stylesheet" type="text/css" href="${ctx }/resources/top5/css/rank.css"/>
		<script src="${ctx }/resources/top5/js/mobileRem.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<div id="loading"><!--<img src="img/loading.gif"/><p>加载中</p>--></div>
		<ul class="rank">
		</ul>
		<p class="noData">暂无数据</p>
		
		
		<!-- 传入的参数 -->
		<input id = "agencyId" value="${inParam.agencyId}" type="hidden">
		<input id = "category" value="${inParam.category}" type="hidden"> 
		<input id = "code" value="${inParam.code}" type="hidden">
		<input id = "startTime" value="${inParam.startTime}" type="hidden">
		<input id = "endTime" value="${inParam.endTime}" type="hidden">
		<input id = "isStore" value="${inParam.isStore}" type="hidden">
		<input id = "storeId" value="${inParam.storeId}" type="hidden">
		
		<!-- 项目名称 -->
		<input id ="projectName" value="${ctx }" type="hidden">
		<input id ="url" value="${url }" type="hidden">
		
		<!-- 引入的js -->
		<script src="${ctx }/resources/top5/js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
		<%-- <script src="${ctx }/resources/top5/js/rank.js" type="text/javascript" charset="utf-8"></script> --%>
		<script type="text/javascript" charset="utf-8">
		$(document).ready(function(){
			var appParam = {};
			appParam["code"] = $("#code").val();
			appParam["startTime"] = $("#startTime").val();
			appParam["endTime"] = $("#endTime").val();
			appParam["category"] = $("#category").val();
			appParam["isStore"] = $("#isStore").val();
			appParam["storeId"] = $("#storeId").val();
			console.log(JSON.stringify(appParam))
			
			var projectName = $("#projectName").val();
			var url = $("#url").val();
			$.ajax({
				url: projectName + "/reportDetail/"+url,//跳转路径
				data:JSON.stringify(appParam),//传参数
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				headers:{/*请求头*/
					id:'${inParam.accessId}', //登陆id
				 	token:'${inParam.token}' //登陆token
					},
				beforeSend:function(XMLHttpRequest){
		          	$("#loading").html("<img src='${ctx }/resources/top5/img/loading.gif'/><p>加载中</p>"); 
		        }, 
		        
		       
		        
		        
				complete:function(XMLHttpRequest,textStatus){
		       		$("#loading").slideUp().empty(); 
		       	},
				success: function(data) {
					console.log(data)
					if(data.resultCode==200){
						if(data.data.length>0) {
							var str="";
							for (var i = 0; i<data.data.length;i++) {
								str += "<li>"
									  +"<img src='"+projectName+"/resources/top5/img/one"+ i +".png'/>"
									  +"<div class='cont'>"
									  +"<p>型号: "+data.data[i].productModelNumber+"</p>"
									  +"<p>"+data.data[i].productName+"</p>"
									  +"</div>"
									  +"<p>"
									  +"<span>派单:<i>"+data.data[i].productCount+"</i>单</span>"
									  +"<span>销售占比:<i>"+data.data[i].productSalesRateStr+"</i></span>"
									  +"</p>"
									  +"</li>"
							}
							$(".rank").html(str);
						}else {
							$(".noData").show();
						}
					}else if(data.resultCode==510){
						$(".noData").show().text("参数错误");
					}else {
						$(".noData").show().text("系统错误");
					}
				},
				error:function(XMLHttpRequest,textStatus,errorThrown){ 
		          	alert(textStatus+"异常信息："+errorThrown); 
		      	}
			});
		});
		
		
		</script>
	</body>
</html>