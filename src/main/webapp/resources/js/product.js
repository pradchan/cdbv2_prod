function addItemToCart1(id){
	data = {id};
//	$.ajax({
//	  type: "POST",
//	  url: "/test/cart/addProduct",
//	  data:data,
//	  success: function(result){
//		  alert(result);
//	  },
//	  fail: function(err){
//		  console.log(err);
//	  }
//	});
	
//	$.post("/test/cart/addProduct",
//		   data,
//		    function(data,status){
//		      alert("Data: " + data + "\nStatus: " + status);
//		    });
	
	$.ajax({
	    method: 'POST',
	    url: '/test/cart/addProduct',
	    data: data, // or JSON.stringify ({name: 'jonas'}),
	    success: function(data) {location.reload();  },
	    contentType: "application/json", // what you are sending
	    dataType: 'json' //what you are  expacting to get back from server
	});
	
}

function addItemToCart(){
	
	document.forms[0].submit();
}