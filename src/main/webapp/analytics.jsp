<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>


<%
	//String[] data = (String[])request.getAttribute("data");
//int[] value = (int[])request.getAttribute("value");

List<String> data = (ArrayList<String>) request.getAttribute("dataList");
List<String> value = (ArrayList<String>) request.getAttribute("value");


String dataString = (String) request.getAttribute("data");



String price_g2 = (String) request.getAttribute("price");
List<String> value_g2 = (ArrayList<String>) request.getAttribute("pricecount");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script

	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <!-- Favicon -->
  <link rel="shortcut icon" href="https://via.placeholder.com/16/007bff/007bff" type="image/x-icon">

<style>

/* width */
.tableContainer::-webkit-scrollbar {
	width: 5px;
}

/* Track */
.tableContainer::-webkit-scrollbar-track {
	box-shadow: inset 0 0 5px grey;
	border-radius: 10px;
}

/* Handle */
.tableContainer::-webkit-scrollbar-thumb {
	background: #007bff;
	border-radius: 4px;
}

/* Handle on hover */
.tableContainer::-webkit-scrollbar-thumb:hover {
	background: green;
}
</style>

</head>
<body style="background-color: gray; background-image: url('http://localhost:7001/resources/images/analyticsheader.jpg');">
	<%@ include file="include/nav.jsp"%>
	<!-- row1 -->
		<div style="text-align: center; padding: 1em; ">
			<h1 style="color: white;">CShop Business Analysis</h1>
		</div>
	
	<div class="container-fluid">
		
		
		
		
		<!-- row2 -->
		<div class="row">

			<div class="col=lg-6 col-md-6 col-sm-12 col-xs-12" style="height:400px;"> 
			
			<div style="overflow-y:scroll;background-color:white;height:100%" class="tableContainer">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Product Category</th>
							<th>Count</th>

						</tr>
					</thead>
					<tbody>
					<% for(int i =0 ;i<data.size() && i< value.size();i++) {%>
						<tr>
							<td><%= data.get(i) %></td>
							<td><%= value.get(i) %></td>
						</tr>
					<%} %>	
						
					</tbody>
				</table>

				
			</div>
				
			</div>
			<div class="col=lg-6 col-md-6 col-sm-12 col-xs-12">

				<div id="container" style="width: 100%; height: 400px; margin: 0 auto"></div>

			</div>
		</div>
		
		<!-- row 3 -->
		<div class="row" style="margin-top:20px">
			<div class="col-sm-12 col-lg-12 col-xs-12 col-sm-12" id="container2" style="width: 100%; height: 400px;"></div>
		</div>
	</div>
	
	
	<script language="JavaScript">
	
	
	
	$(document).ready(
			function() {

				var chart = {
					type : 'bar'

				};
				var title = {
					text : 'Product Counts by Category'
				};
				var subtitle = {
					text : ''
				};
				var xAxis = {
					categories :
<%=dataString%>
,
					title : {
						text : 'Product Category'
					}
				};
				var yAxis = {
					min : 0,
					title : {
						text : 'Product (count)',
						align : 'high'
					},
					labels : {
						overflow : 'justify'
					}
				};
				var tooltip = {
					valueSuffix : ' Count'
				};
				var plotOptions = {
					bar : {
						dataLabels : {
							enabled : false
						}
					}
				};
				var legend = {
					layout : 'vertical',
					align : 'right',
					verticalAlign : 'bottom',
					x : 0,
					y : 100,
					floating : true,
					borderWidth : 1,

					backgroundColor : ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
					shadow : true
				};
				var credits = {
					enabled : false
				};
				var series = [ {
					name : 'Product Count is',
					data :
<%=value%>
} ];

				var json = {};
				json.chart = chart;
				json.title = title;
				json.subtitle = subtitle;
				json.tooltip = tooltip;
				json.xAxis = xAxis;
				json.yAxis = yAxis;
				json.series = series;
				json.plotOptions = plotOptions;
				json.legend = legend;
				json.credits = credits;
				$('#container').highcharts(json);

				secondChart();
				//thirdChart();

			});
	
	
	
	function secondChart() {
		var chart = {
			type : 'column'
		};
		var title = {
			text : 'Product Counts by Price'
		};
		var subtitle = {
			text : ''
		};
		var xAxis = {
			categories :
<%=price_g2%>
	,
			crosshair : true,
			min : 0,
			title : {
				text : 'Product Price $'
			}
		};
		var yAxis = {
			min : 0,
			title : {
				text : 'Product (Count)'
			}
		};
		var tooltip = {
			headerFormat : '<span style = "font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style = "color:{series.color};padding:0">{series.name}: </td>'
					+ '<td style = "padding:0"><b>{point.y:.1f} </b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		};
		var plotOptions = {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		};
		var credits = {
			enabled : false
		};
		var series = [ {
			name : 'Product count by Price',
			data :
<%=value_g2%>
	} ];

		var json = {};
		json.chart = chart;
		json.title = title;
		json.subtitle = subtitle;
		json.tooltip = tooltip;
		json.xAxis = xAxis;
		json.yAxis = yAxis;
		json.series = series;
		json.plotOptions = plotOptions;
		json.credits = credits;
		$('#container2').highcharts(json);

	}
	
	
	</script>

</body>


</html>