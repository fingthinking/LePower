/*
 * 日期选择器（还需改动）
 */
$(function() {
	$("#input_date").datepicker();
})

$(document)
		.ready(
				function() {
					Highcharts.setOptions({
						colors : [ '#FF9966', '#84C0CB', '#99CC99' ]
					});
					var curWwwPath = window.document.location.href;
					var pathName = window.document.location.pathname;
					var pos = curWwwPath.indexOf(pathName);
					var localhostPath = curWwwPath.substring(0, pos);
					var projectName = pathName.substring(0, pathName.substr(1)
							.indexOf('/') + 2);
					var basePath = localhostPath + projectName;
					// alert(basePath);
					var now = new Date();
					var year = now.getFullYear();
					var month = now.getMonth() + 1;
					var day = now.getDate();
					var hour = now.getHours();
					var minute = now.getMinutes();
					var second = now.getSeconds();
					if (month < 10)
						month = "0" + month;
					if (day < 10)
						day = "0" + day;
					var beginDate = year + '-' + month + '-' + '01';
					var endDate = year + '-' + month + '-' + day;
					// alert(beginDate+","+endDate);

					$
							.get(
									basePath
											+ "websport/getSportData?beginDate="
											+ beginDate + "&endDate=" + endDate,
									function(data) {
										var runCalSum = data.runCalSum;
										var walkCalSum = data.walkCalSum;
										var bikeCalSum = data.bikeCalSum;

										var runCal = data.runCal;
										var walkCal = data.walkCal;
										var bikeCal = data.bikeCal;

										var runDis = data.runDis;
										var walkDis = data.walkDis;
										var bikeDis = data.bikeDis;

										var runTime = data.runTime;
										var walkTime = data.walkTime;
										var bikeTime = data.bikeTime;

										var totalCal = Number(runCalSum)
												+ Number(walkCalSum)
												+ Number(bikeCalSum);
										if (totalCal > 100)
											$("#info")
													.html(
															"&nbsp;&nbsp;&nbsp;&nbsp;运动非常棒，可适当休息！");
										else {
											if (total > 60) {
												$("#info")
														.html(
																"&nbsp;&nbsp;&nbsp;&nbsp;运动不错，继续保持！");
											} else {
												$("#info")
														.html(
																"&nbsp;&nbsp;&nbsp;&nbsp;运动太少，请加油！");
											}
										}
										// alert(runCal+","+walkCal+","+bikeCal);
										// $.ajax({
										// url:
										// basePath+"websport/getSportData",
										// data:{Full:"fu"},
										// type: "POST",
										// dataType:'json',
										// success:function(data){
										// alert(data.result);
										// alert("ss");
										// },
										// error:function(er){
										// alert("error");
										// }
										// });

										/*
										 * 消耗卡路里 参数：x:日期 y:卡路里 柱状：跑步、竞走、骑车
										 * 曲线：总卡路里 圆饼：卡路里分布
										 */

										var title = {
											text : '卡路里消耗图',
											style : {
												left : '0px'
											}
										};
										var xAxis = {
											title : {
												text : '日期'
											},
											categories : [ '1', '2', '3', '4',
													'5', '6', '7', '8', '9',
													'10', '11', '12', '13',
													'14', '15', '16', '17',
													'18', '19', '20', '21',
													'22', '23', '24', '25',
													'26', '27', '28', '29',
													'30', '31' ]
										};
										var yAxis = {
											title : {
												text : '消耗卡路里'
											},
											plotLines : [ {
												value : 0,
												width : 1,
												color : '#808080'
											} ]
										};
										var tooltip = {
											formatter : function() {
												return ''
														+ this.series.name
														+ ''
														+ this.x
														+ ': '
														+ Highcharts
																.numberFormat(
																		this.y,
																		2, '.')
														+ ' 焦耳';
											}
										}
										var labels = {
											items : [ {
												html : '消耗卡路里',
												style : {
													left : '50px',
													top : '-20px',
													color : (Highcharts.theme && Highcharts.theme.textColor)
															|| 'black'
												}
											} ]
										};

										var series = [
												{
													type : 'column',
													name : '跑步',
													data : eval(runCal)
												// [12,51,42,3,5]
												},
												{
													type : 'column',
													name : '竞走',
													data : eval(walkCal)
												// [34,23,12,13,32,12,51,42,34,2,34,12]
												},
												{
													type : 'column',
													name : '骑车',
													data : eval(bikeCal)
												// [34,23,12,13,32,12,51,42,34,2,34,12]
												},
												// {
												// type: 'spline',
												// name: '消耗',
												// data:
												// [42,3,5,54,34,13,32,12,51,42,34,100],
												// marker: {
												// lineWidth: 2,
												// lineColor:
												// Highcharts.getOptions().colors[3],
												// fillColor: 'white'
												// }
												// },
												{
													type : 'pie',
													name : '总消耗',
													data : [
															{
																name : 'run',
																y : eval(runCalSum),
																color : Highcharts
																		.getOptions().colors[0]
															// 跑步 的颜色
															},
															{
																name : 'walk',
																y : eval(walkCalSum),
																color : Highcharts
																		.getOptions().colors[2]
															// 竞走 的颜色
															},
															{
																name : 'bike',
																y : eval(bikeCalSum),
																color : Highcharts
																		.getOptions().colors[3]
															// 骑车 的颜色
															} ],
													center : [ 100, 30 ],
													size : 100,
													showInLegend : false,
													dataLabels : {
														enabled : false
													}
												} ];

										var json = {};
										json.title = title;
										json.xAxis = xAxis;
										json.yAxis = yAxis;
										json.tooltip = tooltip;
										json.labels = labels;
										json.series = series;
										$('#sum_cal').highcharts(json);
										// });

										/*
										 * 每日运动时间曲线图 参数：x:日期 y:时长（分钟） 曲线：总步数
										 * 
										 */
										// $(document).ready(function() {
										var title = {
											text : '运动时间曲线图'
										};
										var subtitle = {
										// text: 'Source:LeSports.com'
										};
										var xAxis = {
											title : {
												text : '日期'
											},
											categories : [ '1', '2', '3', '4',
													'5', '6', '7', '8', '9',
													'10', '11', '12', '13',
													'14', '15', '16', '17',
													'18', '19', '20', '21',
													'22', '23', '24', '25',
													'26', '27', '28', '29',
													'30', '31' ]
										};
										var yAxis = {
											title : {
												text : '时间（分钟）'
											},
											plotLines : [ {
												value : 0,
												width : 1,
												color : '#808080'
											} ]
										};

										var tooltip = {
											formatter : function() {
												return ''
														+ this.series.name
														+ ''
														+ this.x
														+ ': '
														+ Highcharts
																.numberFormat(
																		this.y,
																		2, '.')
														+ ' 分钟';
											}
										}

										var legend = {
											layout : 'vertical',
											align : 'right',
											verticalAlign : 'middle',
											borderWidth : 0
										};

										var series = [ {
											type : 'spline',
											name : '跑步',
											data : eval(runTime)
										// [6.9, 9.5, 14.5, 18.2, 21.5,
										// 25.2,26.5, 23.3, 18.3, 13.9, 9.6]
										}, {
											name : '竞走',
											data : eval(walkTime)
										// [0.8, 5.7, 11.3, 17.0, 22.0,
										// 24.8,24.1, 20.1, 14.1, 8.6, 2.5]
										}, {
											name : '骑车',
											data : eval(bikeTime)
										// [ 0.4, 6.7, 11.3, 16.0, 26.0,
										// 27.8,23.1, 10.1, 4.1, 6.6, 2.5]
										} ];

										var json = {};

										json.title = title;
										json.subtitle = subtitle;
										json.xAxis = xAxis;
										json.yAxis = yAxis;
										json.tooltip = tooltip;
										json.legend = legend;
										json.series = series;

										$('#step_chart').highcharts(json);
										// });

										/*
										 * 每日步数图 参数：x:24小时 y:公里 曲线：路程
										 * 
										 */
										// $(document).ready(function() {
										var title = {
											text : '里程曲线图'
										};
										var subtitle = {
										// text: 'Source:LeSports.com'
										};
										var xAxis = {
											title : {
												text : '日期'
											},
											categories : [ '1', '2', '3', '4',
													'5', '6', '7', '8', '9',
													'10', '11', '12', '13',
													'14', '15', '16', '17',
													'18', '19', '20', '21',
													'22', '23', '24', '25',
													'26', '27', '28', '29',
													'30', '31' ]
										};
										var yAxis = {
											title : {
												text : '里程（公里）'
											},
											plotLines : [ {
												value : 0,
												width : 1,
												color : '#808080'
											} ]
										};

										var tooltip = {
											formatter : function() {
												return ''
														+ this.series.name
														+ ''
														+ this.x
														+ ': '
														+ Highcharts
																.numberFormat(
																		this.y,
																		2, '.')
														+ ' 公里';
											}
										}

										var legend = {
											layout : 'vertical',
											align : 'right',
											verticalAlign : 'middle',
											borderWidth : 0
										};

										var series = [ {
											name : '跑步',
											data : eval(runDis)
										// [0,7.0, 6.9, 9.5, 14.5, 18.2, 21.5,
										// 25.2,26.5, 23.3, 18.3, 13.9, 9.6,7.0,
										// 6.9, 9.5, 14.5,0, 21.5, 25.2,0,0]
										}, {
											name : '竞走',
											data : eval(walkDis)
										// [0.2, 0.8, 5.7, 11.3, 17.0, 22.0,
										// 24.8,24.1, 20.1, 14.1, 8.6, 2.5]
										}, {
											name : '骑车',
											data : eval(bikeDis)
										// [ 0.4, 6.7, 11.3, 16.0, 26.0,
										// 27.8,23.1, 10.1, 4.1, 6.6, 2.5]
										} ];

										var json = {};

										json.title = title;
										json.subtitle = subtitle;
										json.xAxis = xAxis;
										json.yAxis = yAxis;
										json.tooltip = tooltip;
										json.legend = legend;
										json.series = series;

										$('#mile_chart').highcharts(json);
									});
				}, "json");
