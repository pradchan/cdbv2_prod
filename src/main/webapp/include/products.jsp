<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<body>
	<% if(title.equals("Shop")){ %>
<div class="card rounded-0" style="margin-bottom: 20px;">
  <div style="display:flex;justify-content: center;flex-wrap: wrap;padding:5px;height:450px;">
    
      <a class="text-body" href="/product/<%= prodList.get(i).getPid() %>">
        <img  width="100%" height="250px" src="<%= product.get(i).getPicture() %>" style="border-radius: 5px;;" />
        <div style="text-align: center;">
          <h4 style="padding: 10px;" class="card-title"><%= product.get(i).getTitle() %></h4>
          <div style="display: flex;justify-content: center;flex-wrap: wrap;">
            <span class="badge badge-warning"><%= product.name %></span>
            <p class="card-text">Price: $<%= product.price %></p>
            <% if(title.equals("Shop")){ %>
              <p class="card-text" ><%= product.get(i).getDetails().substring(0, 20)+" ..." %></p>
            <% } else {%>
              <p class="card-text"><%=  product.get(i).getDetails() %></p>
            <% } %>
            <a href="/add-to-cart/<%= product.pid %>" class="card-link"><i class="fa fa-cart-plus" aria-hidden="true"></i> Add to cart</a>
            </div>
        </div>
      </a>
    </div>
  </div>
    <% } else {%>
      <div style="display:flex;justify-content: center;">
        <div style="width:30%;height:40%;border:1px solid black">
          <img class="img-fluid" height="100%" width="100%" src="<%= product.picture %>" />
          <h4 class="card-title"><%= product.title %></h4>
          <div>
            <span class="badge badge-warning"><%= product.name %></span>
            <p class="card-text">Price: $<%= product.price %></p>
            <% if(title.equals("Shop")){ %>
              <p class="card-text"><%= product.details.substring(0, 200)+' ...' %></p>
            <% } else {%>
              <p class="card-text"><%= product.details %></p>
            <% } %>
            <a href="/add-to-cart/<%= product.pid %>" class="card-link"><i class="fa fa-cart-plus" aria-hidden="true"></i> Add to cart</a>
            </div>
        </div>
      </div>
    <% } %>
   
	
	

</body>
</html>