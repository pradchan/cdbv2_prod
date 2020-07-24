<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>


<% 

List<String> mapData = (ArrayList<String>)request.getAttribute("result");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://d19vzq90twjlae.cloudfront.net/leaflet-0.7/leaflet.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Favicon -->
<link rel="shortcut icon"
	href="https://via.placeholder.com/16/007bff/007bff" type="image/x-icon">
	<script src="https://d19vzq90twjlae.cloudfront.net/leaflet-0.7/leaflet.js"></script>
</head>
<body>
	<%@ include file="include/nav.jsp"%>


	<div style="display: flex; justify-content: center; align-content: center; padding: 10px">
		<form action="/locator">
			<label for="area">Choose an area:</label> 
			
			 <select  style="width: 370px" name="area">
			  <% 
			  String selectedArea = ((String) request.getAttribute("selectedArea")).trim();
			  List<String> options = (ArrayList<String>)request.getAttribute("options");
			 
			  //String[] options = new String[]{"MICHIGAN","NEW YORK"};
			  for(int i =0;i< options.size(); i++){%>
			  	
			  		<% if( options.get(i).trim().equals(selectedArea)) { %>
            		<option  value='<%=options.get(i) %>' selected="true"><%= options.get(i)%></option>
           			 <% } else {%>
              			<option  value='<%=options.get(i) %>'><%= options.get(i)%></option>
            		<% } %>
			  
			  		
			  <%}%>		 
			  </select>
			 <input type="submit">
		</form>
	</div>
	<div style="display: flex; justify-content: center; align-content: center; padding: 10px">
		<div id="map" style="width: 100%; height: 550px"></div>
	</div>
	<script>
	var getData = <%= mapData %>; 
    console.log("Spaitial data");
    console.log(getData)
     var map = L.map('map').setView([getData[0].CENTER_LAT,getData[0].CENTER_LONG], 6);
     mapLink = 
         '<a href="http://openmaptiles.org">OpenMapTiles OpenStreetMap</a>';
     L.tileLayer(
         'http://elocation.oracle.com/mapviewer/mcserver/ELOCATION_MERCATOR/osm_bright/{z}/{y}/{x}.png', {
         attribution: '&copy; ' + mapLink + ' Contributors',
         maxZoom: 18,
         }).addTo(map);

		for (var i = 0; i < getData.length; i++) {
			marker = new L.marker([getData[i].CENTER_LAT,getData[i].CENTER_LONG])
				.bindPopup(`<p><strong>Store Name :</strong> ${getData[i].STORE_NAME}</p><p><strong>Store ID :</strong> ${getData[i].STORE_ID}</p><p><strong>Postal Code :</strong> ${getData[i].POSTAL_CODE}</p>`)
				.addTo(map);
		}   
    </script>

</body>
</html>