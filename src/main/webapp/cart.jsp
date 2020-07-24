<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="converge.models.CartItem"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
<script language="javascript" src="/resources/js/cart.js"></script>
	<style>
	#loading {
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  position: fixed;
  display: block;
  opacity: 0.7;
  background-color: #fff;
  z-index: 99;
  text-align: center;
  vertical-align:middle;
}

#waitdiv {
  position: relative;
  top:50%;
  z-index: 100;
}
		
	</style>
</head>
<%
	List<CartItem> cartList = (ArrayList<CartItem>) request.getAttribute("cartList");
	double bill =0;
	
	
%>
<body>
	<%@ include file="include/nav.jsp"%>

<div id="loading" style="display:none;">
  <image id="waitdiv" src="/resources/images/wait.png">
</div>

	<div class="container">
		<form method="POST" action="/cart/update">
			<% if(cartList.size() > 0) { %>
			<div style="text-align: center; padding: 2em">
				<h1>Cart</h1>
			</div>
			<div style="margin-top: 2em;" class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th>Product</th>
							<th>Price</th>
							<th>Quantity</th>
							<th>Total Price</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<% for(int i=0;i<cartList.size();i++) { %>
						<input type="hidden" value="<%=cartList.get(i).getPid()%>" name="pid[]">
						
						<tr>
							<td><a class="text-body"
								href="/product/<%= cartList.get(i).getPid() %>"><img
									style="max-width: 100px; max-height: 50px;"
									src="<%= cartList.get(i).getPicture()%>" /></a></td>
							<td><a class="text-body"
								href="/product/<%=cartList.get(i).getPid() %>"><%= cartList.get(i).getTitle() %></a><br>
							<span class="badge badge-warning"><%= cartList.get(i).getTitle() %></span></td>
							<td>$<%= cartList.get(i).getPrice() %></td>
							<td><input type="number" style="width: 70px;" min="1"
								name="qnt[]" value="<%= cartList.get(i).getQuantity() %>" /></td>
							<td>$<%= Integer.parseInt(cartList.get(i).getPrice())*cartList.get(i).getQuantity() %></td>
							<td>
							<input type="button" onclick="deleteItem(<%= cartList.get(i).getPid() %>)" value="delete">
							</td>
						</tr>
						<%
							bill += Integer.parseInt(cartList.get(i).getPrice()) * cartList.get(i).getQuantity();
						}
						%>
					</tbody>
				</table>
			</div>

			<div>
				<h4 style="float: right;">
					Total Bill : $<%=bill%>
				</h4>
			</div>
			<div class="clearfix"></div>
			<!-- input type="button" onclick="javascript:clearCart();" class="btn btn-danger float-left" value="Clear Cart">-->
			<a  onclick="clearCart()" class="btn btn-danger float-left" style="color:white">Clear
				cart</a> <!-- a href="/cart/checkout" class="btn btn-primary float-right">Checkout</a-->
			<input type="button" onclick="checkout()" value="checkout" class="btn btn-primary float-right">

			<button type="submit" class="btn btn-success float-right" style="margin-right: 10px;">Update</button>
			<div class="clearfix"></div>
			<%
				} else {
			%>
			<div
				style="display: flex; justify-content: center; align-content: center; padding: 5%">
				<h4>
					Your cart is empty. <a href="/products">Continue shopping</a>
				</h4>
			</div>
			<%
				}
			%>
		</form>
	</div>
	


	<script>
	
	function clearCart(){
		$('#loading').show();
		$.ajax({
			method:'GET',
			url:"/cart/empty-cart",
			success:function(data){
				document.open();
				document.write(data);
				document.close();
				
			},
			error:function(err){
				console.log("error occured from server");
			}
			
		}).always(function(jqXHR, textStatus, errorThrown){
			$('#loading').hide();
		})
	}
		function deleteItem(pid){
			$('#loading').show();
			$.ajax({
				method:'GET',
				url:"/cart/remove-from-cart/"+pid,
				success:function(data){
					document.open();
					document.write(data);
					document.close();
					
				},
				error:function(err){
					console.log("error occured from server");
				}
				
			}).always(function(jqXHR, textStatus, errorThrown){
				$('#loading').hide();
			})
		}
	
		function checkout(){
			$('#loading').show();
			$.ajax({
			    method: 'POST',
			    url: '/cart/checkout',
			    success: function(data){
			    	document.open();
			        document.write(data);
			        document.close();
			    },
			    error:function(err){
					console.log("error occured from server");
				}
			}).always(function(jqXHR, textStatus, errorThrown){
				$('#loading').hide();
			})
		}
		
	</script>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
	<script src="/resources/js/main.js"></script>

</body>
</html>