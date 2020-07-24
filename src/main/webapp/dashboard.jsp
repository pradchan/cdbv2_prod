<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" media="screen"
	href="/resources/css/main.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Favicon -->
<link rel="shortcut icon"
	href="https://via.placeholder.com/16/007bff/007bff" type="image/x-icon">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>

<style>
.header {
	text-align: center;
	padding: 0.2em;
	color: black;
	font-style: 'bold 20px, Verdana, sans-serif';
	font-size: 18px;
}

table {
	text-align: center;
	font-style: 'bold 18px, Verdana, sans-serif';
	border-collapse: collapse;
	width: 100%;
}

td, th {
	font-style: 'bold 14px, Verdana, sans-serif';
	border: 0.5px dashed #dddddd;
	text-align: center;
	padding: 1.5px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}

img {
	margin-bottom: -5px;
	width: 100%;
	height: 150px;
	float: right;
}
</style>


</head>
<%
	int Total = (int) request.getAttribute("totalCount");
	//int Total = 109;
	//category vs count
	String category = (String) request.getAttribute("category");
	List<String> categoryList = (ArrayList<String>) request.getAttribute("categoryList");
	List<String> count = (List<String>) request.getAttribute("count");
	List<String> productName = (List<String>) request.getAttribute("productName");
	List<String> orderCount = (List<String>) request.getAttribute("orderCount");
	List<String> totalValue = (List<String>) request.getAttribute("totalValue");
	List<String> revenueRank = (List<String>) request.getAttribute("revenueRank");
	
	
	String dates = (String) request.getAttribute("dates");
	List<String> value = (List<String>) request.getAttribute("value");
	
	//pieChart
	//List<Object> pieChartData = new ArrayList<Object>();
	//pieChartData.add(category);
	//pieChartData.add(count);
	
	String sale = (String) request.getAttribute("sale");
	List<String> maleCount = (List<String>) request.getAttribute("maleCount");
	List<String> femaleCount = (List<String>) request.getAttribute("femaleCount");
	
	
	
	
	//end
%>
<body style="background-color: gray; background-image: url('/resources/images/analyticsheader.jpg');">
	<%@ include file="include/nav.jsp"%>
	<!-- script>
		var pieChartData = new Array();
		<% for(int i=0;i<categoryList.size();i++){%>
		  let temp ={};
		  temp.name="\""+categoryList.get(i)+"\"";
		  temp.count = count.get(i);
		  pieChartData.push(temp);
		<%}%>
		console.log(pieChartData);
	</script-->
	<div style="text-align: center; padding: 1em; border-bottom: 4px solid lightgray; background-image: url('/resources/images/analyticsheader.jpg');">
		<h1 style="color: white;">CShop Business Analysis</h1>
	</div>
	
	<div class="row">
	
		 <div  class="row" style = "width: 70%; height: 400px;">
            <div class="column" style = "width: 25%; height: 100px; border: 4px solid lightgray;background-color:white;">
                <h3 class="Box" style="text-align: center;color:purple;margin-top: 10px;"><%= Total %></h3>
                <p class="Box" style="text-align: center;color:purple;">Product Count</p></div>
            <div class="column" style = "width: 25%; height: 100px; border: 4px solid lightgray; background-color:white;">
                <h3 class="Box" style="text-align: center;color:purple;margin-top: 10px;">200</h3>
                <p class="Box" style="text-align: center;color:purple;">Mens Dress</p></div>
            <div class="column"  style = "width: 25%; height: 100px; border: 4px solid lightgray; background-color:white;">
                <h3 class="Box" style="text-align: center;color:purple;margin-top: 10px;">100</h3>
                <p class="Box" style="text-align: center;color:purple;">Female Dress</p></div>
            <div class="column" style = "width: 24.5%; height: 100px;border: 4px solid lightgray; background-color:white;">
                <h3 class="Box"style="text-align: center;color:purple;margin-top: 10px;">187</h3>
                <p class="Box" style="text-align: center;color:purple;">Other Dress</p></div>
            <div  class="row" style = "width: 101%; height: 300px;">
                <div class="column" id = "container3" style = "width: 40%; height: 300px; border: 4px solid lightgray; "></div>
                <div class="column" id = "containerline" style = "width: 60%; height: 300px; border: 3px solid lightgray; "></div>
                
            </div>
        </div>
        <div>
            
        </div>
        <div class="row" style = "width: 30.8%; height: 400px;border: 4px solid lightgray;background-color:white;margin-left: 6.5px;">
                <div class="row" style = "width: 100%; height: 250px;border: 4px solid lightgray;background-color:white;margin-left: 0px;">
                    <h5 class="header" style="text-align: center; width:100%; height: 10px; font: 'bold 16px Trebuchet MS, Verdana, sans-serif';">Sales By Division</h5>
                    <div class="column" style="width:33.3%; height: 200px;">
                      <img src="/resources/images/men.jpg" alt="Snow" style="width:90%; height: 150px;border: 2px solid black;margin-left: 5px;">
                      <p class="Box" style="text-align: center;padding:1em;"><b>3754.08$ Mens</b></p>
                    </div>
                    <div class="column" style="width:33.3%; height: 200px;">
                      <img src="/resources/images/women.jpg" alt="Forest" style="width:90%; height: 150px;border: 2px solid black;margin-left: 5px;">
                      <p class="Box" style="text-align: center;padding:1em;"><b>15794.76$ Female</b></p>
                    </div>
                    <div class="column" style="width:33.3%;height: 200px;">
                      <img src="/resources/images/other.png" alt="Mountains" style="width:90%; height: 150px;border: 2px solid black;margin-left: 5px;">
                      <p class="Box" style="text-align: center;padding:1em;"><b>3081.12$ Others</b></p>
                    </div>
                </div>
                <div class="row" style = "width: 100%; height: 150px;border: 4px solid lightgray;margin-left: 0px;">
                    <img src="/resources/images/jeansoffer.jpg"></div>
                 
        </div>
         <div  class="row" style ="width: 100%; height: 300px;">
            <div class="column" id = "container2" style = "width: 25%; height: 300px; border: 4px solid lightgray;"></div>
            <div class="column" id = "container" style = "width: 50%; height: 300px; border: 4px solid lightgray; "></div>
            <div class="column" style = "width: 25%; height: 300px; border: 4px solid lightgray;background-color:white;">
                <h5 class="header" style="text-align: center; color:black; ">Top 5 Product Orders</h5>
                
                    <table style="width:100%;">
                    <tr>
                        <th>Product Name</th>
                        <th>No Of Orders</th> 
                        <th>Total value</th>
                    </tr>
                    <%
                    	
                    	for(int i=0;i<revenueRank.size();i++){
                    		
                    %>
                   	<tr>
                   		<td><%=productName.get(i) %></td>
                   		<td><%=orderCount.get(i) %></td>
                   		<td><%=totalValue.get(i) %></td>
                   <%} %>
                    </table>
            </div>
      </div>
	
	
	</div><!-- row ends here -->
	
	
	
	
	
	
	
	
	
	
	<script language = "JavaScript">
         $(document).ready(function() {  
            var chart = {
               type: 'area'
               
            };
            var title = {
               text: 'Last 3Month Sale Revenue',
               style: {
                 color: '#000',
                     font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
                    }
            };
            var subtitle = {
               text: ''  
            };
            var xAxis = {
               categories: <%=dates%>,
               title: {
                  text: 'Date&Time'
               }
            };
            var yAxis = {
               min: 0,
               title: {
                  text: 'Revenue(K$)',
               },
               labels: {
                  overflow: 'justify'
               }
            };
            var tooltip = {
               valueSuffix: 'K US $'
            };
            var plotOptions = {
               bar: {
                  dataLabels: {
                     enabled: false
                  }
               }
            };
            var legend = {
               layout: 'vertical',
               align: 'right',
               verticalAlign: 'bottom',
               x: 0,
               y: 100,
               floating: true,
               borderWidth: 1,
               
               backgroundColor: (
                  (Highcharts.theme && Highcharts.theme.legendBackgroundColor) ||
                     '#FFFFFF'),
               shadow: true
            };
            var credits = {
               enabled: false
            };
            var series = [
               {
                  name: 'Total sell($)',
                  data: <%=value%>
               }
            ];
      
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
         });
      </script>
   
        
        <script language = "JavaScript">
        $(document).ready(function() {  
            var chart = {
                type: 'column'
            };
            var title = {
                text: 'Product Counts by Price',
                style: {
                 color: '#000',
                     font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
                    } 
            };
            var subtitle = {
                text: ''  
            };
            var xAxis = {
                categories: <%=category%>,
                crosshair: true,
		title: {
                    text: 'Product Price($)'
                }
            };
            var yAxis = {
                min: 0,
                title: {
                    text: 'Product (Count)'         
                }      
            };
            var tooltip = {
                headerFormat: '<span style = "font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style = "color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style = "padding:0"><b>{point.y:.1f} </b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            };
            var plotOptions = {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            };  
            var credits = {
                enabled: false
            };
            var series= [
                {
                    name: 'Product count',
                    data: <%=count%>
                }
            ];     
        
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

        });
        </script>
    
        
        <script language = "JavaScript">
           $(document).ready(function() {
        	   
        	  var pieChartData = new Array();
        	  var category_pieChart = <%=category%>;
        	  var value_pieChart = <%=count%>;
        	  for(let i =0;i< category_pieChart.length ;i++){
	        		let pieTemp ={};
	             	pieTemp.name= category_pieChart[i];
	             	pieTemp.y =value_pieChart[i];
	             	pieChartData.push(pieTemp);
        		  
        	  }
        	  
       		 
        	  
        	  
        	   
        	  
        	   
        	 
              var chart = {
                 plotBackgroundColor: null,
                 plotBorderWidth: null,
                 plotShadow: false
              };
              var title = {
                 text: 'Category shares in Product count',
                 style: {
                 color: '#000',
                     font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
                    }  
              };
              var tooltip = {
                 pointFormat: '{series.name}: <b>{point.percentage:.1f}</b>'
              };
              var plotOptions = {
                 pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    
                    dataLabels: {
                       enabled: true,
                       format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                       style: {
                          color: (Highcharts.theme && Highcharts.theme.contrastTextColor)||
                          'black'
                       }
                    }
                 }
              };
              var series = [{
                 type: 'pie',
                 showInLegend: false,
                 name: 'Category Share',
                 data:   pieChartData
              }];
              var credits = {
               enabled: false
            };
              var json = {};   
              json.chart = chart; 
              json.title = title;     
              json.tooltip = tooltip;  
              json.series = series;
              json.plotOptions = plotOptions;
              json.credits = credits;
              $('#container3').highcharts(json);  
           });
        </script>
        <script language = "JavaScript">
            $(document).ready(function() {
               var chart = {
                  plotBackgroundColor: null,
                  plotBorderWidth: null,
                  plotShadow: false
               };
               var title = {
                  text: 'Price shares in Product count',
                  style: {
                 color: '#000',
                     font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
                    }  
               };
               var tooltip = {
                  pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
               };
               var plotOptions = {
                  pie: {
                     allowPointSelect: true,
                     cursor: 'pointer',
                     
                     dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                           color: (Highcharts.theme && Highcharts.theme.contrastTextColor)||
                           'black'
                        }
                     }
                  }
               };
               var series = [{
                  type: 'pie',
                  showInLegend: true,
                  name: 'Product Share by Price',
                  data: ['21','56']
               }];
               var credits = {
               enabled: false
                };
               var json = {};   
               json.chart = chart; 
               json.title = title;     
               json.tooltip = tooltip;  
               json.series = series;
               json.plotOptions = plotOptions;
               json.credits = credits;
               $('#container4').highcharts(json);  
            });
         </script>
         <script language = "JavaScript">
            $(document).ready(function() {
               var title = {
                  text: 'Number of Shoppers : Male and Female during SALE',
                  style: {
                 color: '#000',
                     font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
                    }  
               };
               var subtitle = {
                  text: ''
               };
               var xAxis = {
                  categories: <%=sale%>,
                  crosshair: false,
                  title: {
                    text: 'SALE DAY'}
               };
               var yAxis = {
                  title: {
                     text: 'Sales Count'
                  },
                  
                  plotLines: [{
                     value: 0,
                     width: 1,
                     color: '#808080'
                  }]
               };   
               var tooltip = {
                  valueSuffix: ''
               }
               var legend = {
                  layout: 'vertical',
                  align: 'right',
                  verticalAlign: 'middle',
                  borderWidth: 0
               };
               var series =  [{
                     name: 'Male',
                     data: <%=maleCount%>
                  }, 
                  {
                     name: 'Female',
                     data: <%=femaleCount%>
                  }
               ];
               var credits = {
               enabled: false
                };
                
               var json = {};
               json.title = title;
               json.subtitle = subtitle;
               json.xAxis = xAxis;
               json.yAxis = yAxis;
               json.tooltip = tooltip;
               json.legend = legend;
               json.series = series;
               json.credits = credits;
               
               $('#containerline').highcharts(json);
            });
         </script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>