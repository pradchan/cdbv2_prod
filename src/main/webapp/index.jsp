<html>
<head>
	<link  rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
	<script  src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	 <link rel="stylesheet" type="text/css" media="screen" href="/resources/css/main.css" />
	<meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <!-- Favicon -->
  <link rel="shortcut icon" href="https://via.placeholder.com/16/007bff/007bff" type="image/x-icon">
</head>
<body>
  <%@ include file="include/nav.jsp" %>
	<div class="slideshow-container">

  <!-- Full-width images with number and caption text -->
  <div class="mySlides">
    <div style="display: flex;justify-content: center;">
      <div class="numbertext" >1 / 3</div>
      <img src="/resources/images/Transferyourcloset.jpg" style="width:80%" style="height: 80%">
      <!--<div class="text">Caption One</div> -->
    </div>
  </div>

  <div class="mySlides">
    <div style="display: flex;justify-content: center;">
      <div class="numbertext" >2 / 3</div>
      <img src="/resources/images/OMEGA-COLLECTION.jpg" style="width:80%" style="height: 80%">
      <!-- <div class="text">Caption One</div> -->
    </div>
  </div>

  <div class="mySlides">
    <div style="display: flex;justify-content: center;">
    <div class="numbertext">3 / 3</div>
    <img src="/resources/images/dresscolorchnage.jpg"  style="width:80%" style="height: 40%">
    <!-- <div class="text">Caption Three</div> -->
  </div>
  </div>

  <!-- Next and previous buttons -->
  <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
  <a class="next" onclick="plusSlides(1)">&#10095;</a>
</div>
<br>

<!-- The dots/circles -->
<div style="text-align:center">
  <span class="dot" onclick="currentSlide(1)"></span>
  <span class="dot" onclick="currentSlide(2)"></span>
  <span class="dot" onclick="currentSlide(3)"></span>
</div>
    
  <div class="container">
    <div class="row">
      <div class="col-sm-4" >
        <h2>Shopping </h2>
        <a href="/products">Click here to start shopping</a>
        <p></p>
        <img src="/resources/images/1.JPG" style="width:30%" style="height: 30%">
      </div>
      <div class="col-sm-4">
        <h3>New Updates</h3>
        <p>Oracle started E-commerce & Omni-channel in Middle East & 
          India in the year 2016 to make online shopping simple and 
          customer-friendly, along with apps for iOS and Android devices. 
          With a higher percentage of customers preferring to shop online nowadays, 
          Oracle intends to improve its online and mobile presence by 
          launching various Omni-channel features in FY 20–21.</p>
      </div>
      <div class="col-sm-4">
        <h3>Contact Details</h3>        
        <p>Oracle Tech Hub, Marathahalli - Sarjapur Outer Ring Rd, Kadubeesanahalli, Bengaluru, Karnataka 560103</p>
        <p>Phone-No - +91 XXXXXXXXXX</p>
        <a href="https://www.oracle.com/in/corporate/contact/">Click here for more Details</a>
      </div>
    </div>
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
  <script src="/resources/js/main.js"></script>
  <!-- script src="reload/reload.js"></script> -->
</body>
</html>
