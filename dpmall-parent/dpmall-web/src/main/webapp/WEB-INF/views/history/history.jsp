<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var = "ctx" value="${pageContext.request.contextPath }" scope = "page"/>
<html>
	<head>
		<title>历史趋势</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<meta name="author" content="Cleam Lee">
		<link rel="stylesheet" type="text/css" href="${ctx }/resources/history/css/initialize.css" />
		<link rel="stylesheet" type="text/css" href="${ctx }/resources/history/css/history.css"/>
		<script src="${ctx }/resources/history/js/mobileRem.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		
		<div class="main">
			<div id="loading"><!--<img src="img/loading.gif"/><p>加载中</p>--></div>
			<div id="container"></div>
			<p class="thep"><span class="time">时间</span><span class="mean">${apiNameReturn }</span></p>
			<ul class="history">
				<!-- <li>
					<span>2017年11月</span>
					<span>¥19100.22</span>
				</li>
				<li>
					<span>2017年10月</span>
					<span>¥19200.22</span>
				</li>
				<li>
					<span>2017年09月</span>
					<span>¥195500.22</span>
				</li>
				<li>
					<span>2017年08月</span>
					<span>¥100.22</span>
				</li> -->
			</ul>
		</div>
		<p class="noData">暂无数据</p>
		
		<input id= "projectName" value="${ctx }" type="hidden">
		
		
		<script src="${ctx }/resources/history/js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ctx }/resources/history/js/highcharts.js" type="text/javascript" charset="utf-8"></script>
		<%-- <script src="${ctx }/resources/history/js/history.js" type="text/javascript" charset="utf-8"></script> --%>
		
		<script type="text/javascript" charset="utf-8">
		var appParam = {};
		appParam["code"] = "${inParam.code}";
		appParam["dateFormat"] = "${inParam.dateFormat}";
		appParam["startTime"] = "${inParam.startTime}";
		appParam["endTime"] = "${inParam.endTime}";
		appParam["startNum"] = "${inParam.startNum}";
		appParam["pageSize"] = "${inParam.pageSize}";
		appParam["isStore"] = "${inParam.isStore}";
		appParam["storeId"] = "${inParam.storeId}";
		
		
		
		
		$(function() {//ready方法缩写 
			//异步读取数据并加载到图表
			$.ajax({
				 url: "${ctx}/reportDetail/${url}",//跳转路径 
				/* url: "${ctx}/reportDetail/orderAcceptRateHistory",//跳转路径 */
				data:JSON.stringify(appParam),//传参数
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				headers:{/*请求头*/
					id:'${inParam.accessId}', //登陆id
				 	token:'${inParam.token}' //登陆token
					},
				beforeSend:function(XMLHttpRequest){
	              	$("#loading").html("<img src='${ctx }/resources/history/img/loading.gif'/><p>加载中</p>"); 
		        }, 
	 			complete:function(XMLHttpRequest,textStatus){
	           		$("#loading").slideUp().empty(); 
	           	}, 
				success: function(data) {
					if(data.resultCode == 200) {
						if(data.data.length > 0) {

							var oChart = null;
							var X_axis = [];
							var X_value = [];
							/* var company = data.data[0].result; */
							var prefix = "";
							var Suffix = "";
							var str = "";
							//为图表赋值 
							if('${url}'.indexOf("AVG") >= 0) {
								prefix = "￥";
								Suffix = "";
							} else if('${url}'.indexOf("Rate") >= 0) {
								Suffix = "%";
								prefix = "";
							} else if('${url}'.indexOf("Time") >= 0 ){
								Suffix = "分钟";
								prefix = "";
							}
						
							

							for(var i = 0; i < data.data.length; i++) {

								var value_x = String(data.data[i].xDate);
								var value_y = Number(data.data[i].yResult);

								X_axis.push(value_x);
								X_value.push(value_y);

								str += "<li>" +
									"<span>" + data.data[i].historyDate + "</span>" +
									"<span>" + data.data[i].result + "</span>" +
									"</li>"
							}

							 $(".history").html(str); 

							//定义oChart的布局环境
							var oOptions = {

								chart: {
									renderTo: 'container', //设置显示的页面块
									type: 'area'
								},

								title: {
									text: null
								},

								xAxis: {
									title: {
										text: null
									},
									categories: X_axis,
								},

								yAxis: {
									title: {
										text: null
									},
									tickAmount: 6
								},

								//图例
								legend: {
									enabled: false
								},

								//官网标识链接
								credits: {
									enabled: false,
								},

								//数据列
								series: [{
									name: "",
									data: X_value
								}],

								//数据列配置
								plotOptions: {
									area: {
										fillColor: {
											linearGradient: {
												x1: 0,
												y1: 0,
												x2: 0,
												y2: 1
											},
											stops: [
												[0, Highcharts.getOptions().colors[5]],
												[1, Highcharts.Color(Highcharts.getOptions().colors[5]).setOpacity(0).get('rgba')]
											]
										},
										lineWidth: 1,
										marker: {
											radius: 4,
											fillColor: '#FFFFFF',
											lineWidth: 2,
											lineColor: null
										},
										states: {
											hover: {
												lineWidth: 1
											}
										},
										threshold: 0
									},
									series: {
										color: '#d21e2f'
									}
								},

								//数据提示框
								tooltip: {
									backgroundColor: '#F16083',
									borderRadius: 10,
									style: {
										fontSize: 14,
										color: "#fff",
										fontWeight: 'bold',
										borderColor: '#F16083',
										fontFamily: "arial",
										lineHeight: 20
									},
									formatter: function() {

										return '<b>' + this.x + '</b> <br> <b>' + prefix + this.y + Suffix + '</b>';
									}

								},
							};
							$(".thep").css("visibility","visible");
							oChart = new Highcharts.Chart(oOptions);

						} else {
							$(".main").hide();
							$(".noData").show();
						}
					}  else if(data.resultCode == 510) {
						$(".main").hide();
						$(".noData").show().text("参数错误");
					} else {
						$(".main").hide();
						$(".noData").show().text("系统错误");
					} 
				},
				error:function(XMLHttpRequest,textStatus,errorThrown){ 
	              	alert(textStatus+"异常信息："+errorThrown); 
	             	$("#loading").empty(); 
	          	} 
			});

		});
		
		</script>
	</body>

</html>