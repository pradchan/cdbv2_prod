<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

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
</head>
<%
	String st = (String) request.getAttribute("cartList");
%>
<body>
	<%@ include file="include/nav.jsp"%>
	<div style="text-align: center; padding: 1em">
		<h2>Enter Order Details</h2>
	</div>
	<div class="container">
		<form action="/order/placeOrder" method="post">
			<div class="row">

				<div class="col-md-6">

					<div class="form-group">
						<label for="name">Full name:</label> <input type="text"
							class="form-control" id="name" name="name" required>
					</div>
					<div class="form-group">
						<label for="email">Email:</label> <input type="email"
							class="form-control" id="email" name="email" required>
					</div>
					<div class="form-group">
						<label for="phone">Phone:</label> <input type="text"
							class="form-control" id="phone" name="phone" required
							onkeypress="return ensureNumberic(event)">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="address">Address:</label> <input type="text"
							class="form-control" id="address" name="address" required>
					</div>
					<div class="form-group">
						<label for="city">City:</label> <input type="text"
							class="form-control" id="city" name="city" required>
					</div>
					<div class="form-group">
						<label for="phone">Pincode:</label> <input type="text"
							class="form-control" id="pincode" name="pincode" required
							onkeypress="return validatePin(event)">
					</div>

				</div>

			</div>
			<div style="display: flex; justify-content: center; padding: 2em;">
				<button type="button" class="btn btn-primary"
					onclick="submitForm();">Submit</button>
			</div>
		</form>

	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
	<script src="/resources/js/main.js"></script>
	<script src="/resources/reload/reload.js"></script>
	<script>
		
		function submitForm() {
			if (document.forms[0].checkValidity()) {
				document.forms[0].submit();
				document.forms[0].reset();
			} else {
				var elements = document.getElementsByTagName("input");
				for (var i = 0; i < elements.length; i++) {
					if(!elements[i].checkValidity()){
						elements[i].style.borderBlockStartColor="red";
						
					}else{
						elements[i].style.borderBlockStartColor="green";
					}
						
				}

			}

		}
		
		
		function validatePin(e) {
			
			var keyValue = e.key;
			if (keyValue.match(/[0-9]/g)) {
				return true;
			}
			return false;
		}
		
		
		function ensureNumberic(e) {
			let value = document.getElementById("phone").value;
			if (value.length + 1 > 10) {
				return false;
			}
			var keyValue = e.key;
			if (keyValue.match(/[0-9]/g)) {
				return true;
			}
			return false;
		}
	</script>
</body>
</html>