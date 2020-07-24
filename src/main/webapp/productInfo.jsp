<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*"%>
<%@ page import="converge.models.Product"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" media="screen"
	href="/resources/css/main.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Favicon -->
<link rel="shortcut icon"
	href="https://via.placeholder.com/16/007bff/007bff" type="image/x-icon">
<script language="javascript" src="/resources/js/product.js"></script>
</head>
<%
	Product prod = (Product) request.getAttribute("product");
//boolean flag = (boolean) request.getAttribute("flag");
%>
<body>
	<%@ include file="include/nav.jsp"%>
	<form action="/cart/addProduct" method="post" name="productInfoForm">
		<input type="hidden" name="pid" value="<%= prod.getPid() %>"> 
		<input type="hidden" name="category" value="<%= prod.getCategory() %>"> 
		<input type="hidden" name="title" value="<%= prod.getTitle() %>"> 
		<input type="hidden" name="details" value="<%=prod.getDetails() %>"> 
		<input type="hidden" name="price" value="<%=prod.getPrice() %>"> 
		<input type="hidden" name="picture" value="<%=prod.getPicture() %>"> 
		<input type="hidden" name="quantity" value="1">
	</form>
	

	<div class="container">
		<div class="row" style="margin-top: 20px;">
			<div class="col-md-6 shop__thumb" style="text-align: center;">
				<img src="<%=prod.getPicture()%>" class="img-responsive" alt="..."
					height="400px" width="350px">
			</div>
			<div class="col-md-6">
				<h1><%=prod.getTitle()%></h1>
				<h5><%=prod.getPrice()%>
					$
				</h5>
				<p><%=prod.getDetails()%></p>
				<%
					//if (flag) {
				%>
				<!-- a href="/cart/cartitems"><span><button>Go To Cart</button></span></a> -->
				<%
					//} else {
				%>
				<button onclick="javascript:addItemToCart(<%=prod.getPid()%>)">
					<span><i class="fa fa-cart-plus" aria-hidden="true">Add
							to cart</i> </span>
				</button>
				<%
					//}
				%>
			</div>

		</div>
	</div>
	</div>

</body>
</html>