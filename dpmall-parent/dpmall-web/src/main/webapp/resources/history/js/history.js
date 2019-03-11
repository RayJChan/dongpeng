	$(function() {
		//异步读取数据并加载到图表
		$.ajax({
			url: 'rank.json',
			type: 'POST',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success: function(data) {

				if(data.resultCode == 200) {
					if(data.data.length > 0) {

						var oChart = null;
						var X_axis = [];
						var X_value = [];
						var company = data.data[2].result;
						var prefix = "";
						var Suffix = "";
						var str = "";

						if(company.indexOf("￥") >= 0) {
							prefix = "￥";
							Suffix = "";
						} else if(company.indexOf("%") >= 0) {
							Suffix = "%";
							prefix = "";
						} else {
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

						//console.log(X_axis, X_value,prefix,Suffix);

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

						oChart = new Highcharts.Chart(oOptions);

					} else {
						$(".main").hide();
						$(".noData").show();
					}
				} else if(data.resultCode == 510) {
					$(".main").hide();
					$(".noData").show().text("参数错误");
				} else {
					$(".main").hide();
					$(".noData").show().text("系统错误");
				}
			},
			error: function() {
				alert("网络开小差了~")
			}
		});

	});