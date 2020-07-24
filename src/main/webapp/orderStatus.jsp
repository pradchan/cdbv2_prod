<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="converge.models.Order" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" media="screen" href="/resources/css/main.css" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <!-- Favicon -->
  <link rel="shortcut icon" href="https://via.placeholder.com/16/007bff/007bff" type="image/x-icon">
</head>
<%
Order od = (Order) request.getAttribute("order");
%>
<body>
<%@ include file="include/nav.jsp"%>


<div class="container">
    
      <div style="text-align: center;padding:1em">
        <h1>Order Details</h1>
      </div>
      <div style="margin-top:2em;"class="table-responsive">
        <h3>Status : <%= od.getStatus() %></h3>
        <table class="table">
          <thead>
            <tr>
              <th>#</th>
              <th>Product</th>
              <th>Price</th>
              <th>Quantity</th>
              <th>Total Price</th>
            </tr>
          </thead>
          <tbody>
          <% for(int i=0;i<od.getCartList().size();i++){ %>
            <tr>
              <td><a class="text-body" href="/product/<%= od.getCartList().get(i).getPid() %>"><img style="max-width: 100px; max-height: 50px;" src="<%=od.getCartList().get(i).getPicture() %>" /></a></td>
              <td><a class="text-body" href="/product/<%= od.getCartList().get(i).getPid() %>"><%= od.getCartList().get(i).getTitle() %></a><br><span class="badge badge-warning"><%= od.getCartList().get(i).getTitle() %></span></td>
              <td>$<%= od.getCartList().get(i).getPrice() %></td>
              <td><%= od.getCartList().get(i).getQuantity() %></td>
              <td>$<%= Integer.parseInt(od.getCartList().get(i).getPrice())*od.getCartList().get(i).getQuantity() %></td>
            </tr>
          <% } %>
          </tbody>
        </table>
      </div>
     
      <div>
        <h4 style="float: right;">Total Bill : $<%= od.getTotalOrderCost() %> </h4>
      </div>
      <div class="clearfix"></div>
      
      <div class="clearfix">  </div>
   
    <h3>Customer Details :</h3>
    <ul style="list-style-type: none;">
        <li>Name : <%= od.getOrderedBy() %></li>
        <li>Email : <%= od.getEmail() %></li>
        <li>Address : <%= od.getAddress() %></li>
        <li>City : <%= od.getCity() %></li>
        <li>Contact Number : <%=od.getPhone() %></li>
        <li>Pincode : <%= od.getPincode() %></li>
    </ul>
    <div>
        <a href="/products"style="float: right;">Continue Shopping.. </a>
      </div>
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
  <script src="/resources/js/main.js"></script>


</body>
</html>