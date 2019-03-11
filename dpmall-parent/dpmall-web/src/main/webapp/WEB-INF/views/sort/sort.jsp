<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 项目名称 -->
<<c:set var ="ctx" value="${pageContext.request.contextPath }" scope="page"/>
<html>
	<head>
		<meta charset="utf-8">
		<title>下级排名</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${ctx }/resources/sort/css/mui.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/resources/sort/css/initialize.css"/>
		<link rel="stylesheet" href="${ctx }/resources/sort/css/xjpm.css">
		<style type="text/css">
			body,.mui-content{
				background: #fff;
			}
			.mui-table-view:before{
				height: 0;
				background-color: #fff;
			}
			.mui-table-view:after {
			    height: 1px;
			    background-color: #F4F4F4;
			}
			.mui-add-loading{
    			display: none;
    			margin-top: 70%;
    		}
    		.mui-add-loading .mui-spinner{
				width: 44px;
				height: 44px;
			}
    		.mui-add-loading .load_txt{
    			text-align: center;
    			margin-top: 10px;
    			font-size: 14px;
    		}
		</style>
	</head>

	<body>
		<!--下拉刷新容器-->
		
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--数据列表-->
				<div class="mui-table-view mui-table-view-chevron">
					<ul class="topNav" id="topNav">
						<li class="">
						</li>
						<li class="">
						</li>
						<li class="">
						</li>
					</ul>
		
					<ul class="pm_list data-list " id="dataList">
					</ul>
					<div id="tips" style="text-align:center;line-height:40px;font-size:16px;"></div>
				</div>
				<div class="mui-loading mui-add-loading">
					<div class="mui-spinner"></div>
					<div class="load_txt">
						加载中...
					</div>
				</div>
			</div>	
		</div>
		
		<script src="${ctx }/resources/sort/js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ctx }/resources/sort/js/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var page = 1;
			mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					down: {
						callback: pulldownRefresh
					},
					up: {
						contentrefresh: '正在加载...',
						contentnomore:'没有更多数据了',
						callback: pullupRefresh
					}
				}
			});
			
			
			var pastRowNumJs = "";//当前页最后一个序号
			var pastResultJs = ""//当前页最后一个结果
			var str1 = '';//显示字符
			var pageNum = 0;
			
			var appParam ={};//入参
			appParam["code"] = '${inParam.code}';
			appParam["index"] = '${inParam.index}';
			appParam["roleCode"] = '${inParam.roleCode}';
			appParam["dateFormat"] = '${inParam.dateFormat}';
			appParam["startTime"] = '${inParam.startTime}';
			appParam["endTime"] = '${inParam.endTime}';
			appParam["startNum"] = pageNum;
			appParam["pageSize"] = 12;
			appParam["pastRowNum"] = '${inParam.pastRowNum}';
			appParam["pastResult"] = '${inParam.pastResult}';
			
			
			//上拉加载
			function pullupRefresh() {
				setTimeout(function() {
					appParam["startNum"] = pageNum*(Number(appParam["pageSize"]));
					appParam["pastRowNum"] = pastRowNumJs;
					appParam["pastResult"] = pastResultJs;
					 page += 1; 
					console.log("上拉加载");
					console.log(appParam);
					addData(page);
					//提示没有数据
						 setTimeout(function(){
							mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
							},1000) 
					
					
				}, 500);
			}
			//下拉刷新
			function pulldownRefresh() {
				setTimeout(function() {
					appParam["startNum"] = 0;
					appParam["pastRowNum"] = "0";
					appParam["pastResult"] = "0";
					pageNum = 0;
					console.log("下拉刷新");
					console.log(appParam);
					str1 = '';
					addData(page);
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);//这里有问题
				}, 1000);
			}
			

			//第一次请求接口
			$(function(){
				$.ajax({
					 url: "${ctx}/reportDetail/${url}", 
					data:JSON.stringify(appParam),
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					beforeSend: function() {
						$('.mui-add-loading').show();
						
				    },
				    complete: function() {
						$('.mui-add-loading').hide();
				    },
					headers:{/*请求头*/
						id:'${inParam.accessId}', //登陆id
					 	token:'${inParam.token}' //登陆token
						},
					
					success: function(data) { 
						if(data.resultCode == 200){
							console.log("打印返回数据");
							console.log(data);
								if (data.data==null){
									$("#tips").html("没有数据了");
									return;
								}else{
									$("#tips").html("");//
								}
							
						pageNum = pageNum+1;
						pastRowNumJs = data.data[(data.data.length)-1].rowNum;//记录值
						pastResultJs = data.data[(data.data.length)-1].yResult;//记录值
							$.each(data.data,function(k,v){
								if (k<3 && page ==1){
									if (k==0 ){
										var str = '<h2>'+v.groupName+'</h2>\
										<span class="jd_tit">${apiName}</span>\
										<h3>'+v.result+'</h3>';
										$('#topNav li').eq(1).html(str);
									}else if (k==1){
										var str = '<h2>'+v.groupName+'</h2>\
										<span class="jd_tit">${apiName}</span>\
										<h3>'+v.result+'</h3>';
										$('#topNav li').eq(0).html(str);
									}else if (k==2){
										var str = '<h2>'+v.groupName+'</h2>\
										<span class="jd_tit">${apiName}</span>\
										<h3>'+v.result+'</h3>';
										$('#topNav li').eq(2).html(str);
									}
								}else{
										// 4-10名显示颜色 
										var rowNum = parseInt(v.rowNum);
										if (rowNum == 1){
											str1 += '<li class="clearfix">\
												<div class="l-l" style="color: #F09717;">\
													'+v.rowNum+'\
												</div>\
												<div class="l-c">\
													'+v.groupName+'\
												</div>\
												<div class="l-r" style="color: #F09717;">\
													'+v.result+'\
												</div>\
											</li>';
										}
										else if (rowNum == 2){
											str1 += '<li class="clearfix">\
												<div class="l-l" style="color: #F09717;">\
													'+v.rowNum+'\
												</div>\
												<div class="l-c">\
													'+v.groupName+'\
												</div>\
												<div class="l-r" style="color: #F09717;">\
													'+v.result+'\
												</div>\
											</li>';
										}
										
										else if (rowNum == 3){
											str1 += '<li class="clearfix">\
												<div class="l-l" style="color: #F09717;">\
													'+v.rowNum+'\
												</div>\
												<div class="l-c">\
													'+v.groupName+'\
												</div>\
												<div class="l-r" style="color: #F09717;">\
													'+v.result+'\
												</div>\
											</li>';
										}
										else if((rowNum >= 4) && (rowNum <= 10))	{
											str1 += '<li class="clearfix">\
														<div class="l-l" style="color: #F09717;">\
															'+v.rowNum+'\
														</div>\
														<div class="l-c">\
															'+v.groupName+'\
														</div>\
														<div class="l-r" style="color: #F09717;">\
															'+v.result+'\
														</div>\
													</li>';
										}
										// 全体平均值显示的颜色 
										else if(!rowNum)
										{
											str1 += '<li class="clearfix" style="background-color: rgb(240,240,240);">\
														<div class="l-l">\
															'+v.rowNum+'\
														</div>\
														<div class="l-c" style="color: rgb(32,202,179);">\
															'+v.groupName+'\
														</div>\
														<div class="l-r" style="color: rgb(32,202,179);">\
															'+v.result+'\
														</div>\
													</li>';
										}
										// 11名之后显示的颜色 
										else if((rowNum >= 11) )
										{
											str1 += '<li class="clearfix">\
														<div class="l-l" style="color: rgb(19,138,214);">\
															'+v.rowNum+'\
														</div>\
														<div class="l-c">\
															'+v.groupName+'\
														</div>\
														<div class="l-r" style="color: rgb(19,138,214);">\
															'+v.result+'\
														</div>\
													</li>';
										}	
									}	
									
							});
						}
						else {
						 mui.toast("网络开小差了~")
						}
                       	 $('#dataList').html(str1);
						
					 },
				 error: function() {
						mui.toast("网络开小差了~")
					}
				}); 
				
			});
			
			
			
			//加载和更新时调的方法
			function addData(page) {
				$.ajaxSetup({
					async: false
				});
				var jsonParam = JSON.stringify(appParam)//转json格式
				console.log("打印传入参数");
				console.log(jsonParam);
				
				$.ajax({
					 url: "${ctx}/reportDetail/${url}", 
					data:jsonParam,
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					headers:{/*请求头*/
						id:'${inParam.accessId}', //登陆id
					 	token:'${inParam.token}' //登陆token
						},
					beforeSend: function() {
						$('.mui-add-loading').show();
				    },
				    complete: function() {
						$('.mui-add-loading').hide();
				    },
					success: function(data) { 
						if(data.resultCode == 200){
							console.log("打印返回数据");
							console.log(data);
								if (data.data==null){
									$("#tips").html("没有数据了");
									return;
								}else{
									$("#tips").html("");//
								}
							
							pageNum = pageNum+1;
						pastRowNumJs = data.data[(data.data.length)-1].rowNum;//记录值
						pastResultJs = data.data[(data.data.length)-1].yResult;//记录值
							$.each(data.data,function(k,v){
								if (k<3 && page ==1){
									if (k==0 ){
										var str = '<h2>'+v.groupName+'</h2>\
										<span class="jd_tit">${apiName}</span>\
										<h3>'+v.result+'</h3>';
										$('#topNav li').eq(1).html(str);
									}else if (k==1){
										var str = '<h2>'+v.groupName+'</h2>\
										<span class="jd_tit">${apiName}</span>\
										<h3>'+v.result+'</h3>';
										$('#topNav li').eq(0).html(str);
									}else if (k==2){
										var str = '<h2>'+v.groupName+'</h2>\
										<span class="jd_tit">${apiName}</span>\
										<h3>'+v.result+'</h3>';
										$('#topNav li').eq(2).html(str);
									}
								}else{
										// 4-10名显示颜色 
										var rowNum = parseInt(v.rowNum);
										if (rowNum == 1){
											str1 += '<li class="clearfix">\
												<div class="l-l" style="color: #F09717;">\
													'+v.rowNum+'\
												</div>\
												<div class="l-c">\
													'+v.groupName+'\
												</div>\
												<div class="l-r" style="color: #F09717;">\
													'+v.result+'\
												</div>\
											</li>';
										}
										else if (rowNum == 2){
											str1 += '<li class="clearfix">\
												<div class="l-l" style="color: #F09717;">\
													'+v.rowNum+'\
												</div>\
												<div class="l-c">\
													'+v.groupName+'\
												</div>\
												<div class="l-r" style="color: #F09717;">\
													'+v.result+'\
												</div>\
											</li>';
										}
										
										else if (rowNum == 3){
											str1 += '<li class="clearfix">\
												<div class="l-l" style="color: #F09717;">\
													'+v.rowNum+'\
												</div>\
												<div class="l-c">\
													'+v.groupName+'\
												</div>\
												<div class="l-r" style="color: #F09717;">\
													'+v.result+'\
												</div>\
											</li>';
										}
										else if((rowNum >= 4) && (rowNum <= 10))	{
											str1 += '<li class="clearfix">\
														<div class="l-l" style="color: #F09717;">\
															'+v.rowNum+'\
														</div>\
														<div class="l-c">\
															'+v.groupName+'\
														</div>\
														<div class="l-r" style="color: #F09717;">\
															'+v.result+'\
														</div>\
													</li>';
										}
										// 全体平均值显示的颜色 
										else if(!rowNum)
										{
											str1 += '<li class="clearfix" style="background-color: rgb(240,240,240);">\
														<div class="l-l">\
															'+v.rowNum+'\
														</div>\
														<div class="l-c" style="color: rgb(32,202,179);">\
															'+v.groupName+'\
														</div>\
														<div class="l-r" style="color: rgb(32,202,179);">\
															'+v.result+'\
														</div>\
													</li>';
										}
										// 11名之后显示的颜色 
										else if((rowNum >= 11) )
										{
											str1 += '<li class="clearfix">\
														<div class="l-l" style="color: rgb(19,138,214);">\
															'+v.rowNum+'\
														</div>\
														<div class="l-c">\
															'+v.groupName+'\
														</div>\
														<div class="l-r" style="color: rgb(19,138,214);">\
															'+v.result+'\
														</div>\
													</li>';
										}	
									}	
									
							});
						}
						else {
						 mui.toast("网络开小差了~")
						}
                       	 $('#dataList').html(str1);//追加数据
					 },
				 error: function() {
						mui.toast("网络开小差了~")
					}
				}); 
				
			}
			
			

			
			
			//调用该方法 
			/*  $(function(){
				addData(1);
			});  */
			

		</script>
	</body>

</html>