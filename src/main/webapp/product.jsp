<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*"%>
<%@ page import="converge.models.Product"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

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
<style>
.productContainer div {
	margin: 5px 0px 5px 0px;
}
.shop__thumb{
	transition: transform .2s;
}
.shop__thumb:hover{
	-ms-transform: scale(1.1); /* IE 9 */
  -webkit-transform: scale(1.1); /* Safari 3-8 */
  transform: scale(1.1); 
}

</style>
<script>
	function submitSearch(){
		
		var elmString = document.querySelector("#searchText").value;
		if(elmString == null || elmString.length <0){
			return;
		}
		document.forms[0].submit();
	}


</script>



</head>
<%
	List<Product> prodList = (ArrayList<Product>) request.getAttribute("productList");
String title = "Shop";//(String) request.getAttribute("type");

int totalCount = (int) request.getAttribute("actualCount");

//List<String> cartPids = (ArrayList<String>) request.getAttribute("cartPids");

String query ="";

if(request.getAttribute("searchText")!=null){
	 query = (String) request.getAttribute("searchText");
}

%>

<body>
	
	<%@ include file="include/nav.jsp"%>



	<div class="container-fluid">
		
		<div class="row" style="padding-top:20px">
			<div class="col-sm-4 col-md-3" style="padding-top: 20px;padding-left:50px;">
				<h2 style="margin-bottom:10px">Products(<%= totalCount %>)</h2>
				<form action="/products" method="get" id="querySearchForm">
					<div class="well">
						<div class="row">
							<div class="col-sm-12">
								<div class="input-group">
									<input type="text" class="form-control"
										placeholder="Search products..." name="searchText" id="searchText" value="<%=query%>"> <span
										class="input-group-btn">
										<button class="btn btn-primary" type="button" onclick="submitSearch();">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</form>

				<!-- Filter -->
				<!-- form class="shop__filter">
					
					<br/>
					
					<div class="filterMessage" style="text-align:center; font-weight:600">
						Filter Result
					</div>
					<hr/>
					<h4 class="headline">
						<span>Price</span>
					</h4>
					<div class="radio">
						<input type="radio" name="shop-filter__price"
							id="shop-filter-price_1" value="" checked=""> <label
							for="shop-filter-price_1">Under $25</label>
					</div>
					<div class="radio">
						<input type="radio" name="shop-filter__price"
							id="shop-filter-price_2" value=""> <label
							for="shop-filter-price_2">$25 to $50</label>
					</div>
					<div class="radio">
						<input type="radio" name="shop-filter__price"
							id="shop-filter-price_3" value=""> <label
							for="shop-filter-price_3">$50 to $100</label>
					</div>
					<div class="radio">
						<input type="radio" name="shop-filter__price"
							id="shop-filter-price_4" value="specify"> <label
							for="shop-filter-price_4"> > $100</label>
					</div>


				</form-->
			</div>

			<div class="col-sm-8 col-md-9">
				<!-- Filters -->
				<div id="queryViewer">
					
				</div>
				<hr/>
				<div class="row productContainer">

					<%
						for (int i = 0; i < prodList.size(); i++) {
					%>

					<div class="col-sm-6 col-md-4" style="margin: 20px 0px 20px 0px">
						<div class="shop__thumb" style="text-align:center;">
							<a href="/product/<%=prodList.get(i).getPid()%>">
								<div class="shop-thumb__img" style="overflow: hidden;">
									<img src="<%=prodList.get(i).getPicture()%>"
										class="img-responsive" alt="..." height="350px" width="264px">
								</div>
								<h5 class="shop-thumb__title" style="text-align: center;"><%=prodList.get(i).getTitle()%></h5>
								<div class="shop-thumb__price">
									<span class="shop-thumb-price_old">$<%=prodList.get(i).getPrice()%></span>
								</div>
							</a>
						</div>
					</div>

					<%
						}
					%>
					<%
						int noOfPages = (int) request.getAttribute("totalPageCount");
					int currentPage = (int) request.getAttribute("currentPage");
					%>


					<!-- / .row -->

				</div>
				<!-- / .col-sm-8 -->
			</div>
			<!-- / .row -->




			<!-- Pagination -->
			<div class="row" style="width: 100%;">
				<div class="col-sm-12 col-lg-12" style="float: right;">

					<ul class="pagination pull-right">
						<%if(currentPage>1){ %>
						<li class="disabled"><a href="/products?page=<%=currentPage -1%>&searchText=<%=query %>">«</a></li>
						<%} %>
						<li><%=currentPage%></li>
						<%
							if (currentPage < noOfPages) {
						%><li><a href="/products?page=<%=currentPage + 1%>&searchText=<%=query %>">
								Next </a></li>
						<%
							}
						%>
						<li><i>total page count <%=noOfPages%></i></li>
					</ul>

				</div>
			</div>





		</div>
</body>
</html>