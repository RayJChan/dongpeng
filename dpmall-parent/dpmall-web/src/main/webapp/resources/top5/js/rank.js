$(document).ready(function(){
	var appParam = {};
	appParam["code"] = $("#code").val();
	appParam["startTime"] = $("#startTime").val();
	appParam["endTime"] = $("#endTime").val();
	appParam["category"] = $("#category").val();
	appParam["isStore"] = $("#isStore").val();
	appParam["storeId"] = $("#storeId").val();
	console.log(appParam)
	console.log(JSON.stringify(appParam))
	
	var projectName = $("#projectName").val();
	var url = $("#url").val();
	$.ajax({
		url: projectName + "/reportDetail/"+url,//跳转路径
		data:JSON.stringify(appParam),//传参数
		type: "POST",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			console.log(data)
			if(data.resultCode==200){
//				console.log(data.data.length)
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
		error: function() {
			alert("网络开小差了~")
		}
	});
});


