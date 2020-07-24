var globalurl = "http://localhost:7001/";
    //var globalProductId = null;
    var xmlIdsDropdown = new Array();
    $(document).ready(function(){
  	  refreshDropDown();
    });
    
    function refreshDropDown(){
  	  var selectList  = document.getElementById("selectXmlDocumentId");
  	  $("#selectXmlDocumentId").empty();
  	  $.ajax({
            method: "GET",
            url: "/xml/ids",
            success: function (data) {
          	  console.log("ids");
          	  
          	  xmlIdsDropdown = data;
          		console.log(xmlIdsDropdown);
          	 var optionString="";
          	
          	for(let i=0;i<data.length;i++){
          		//optionString+="<option value=\'"+data[i]+"\'>"+data[i]+"</option>";
          		var option = document.createElement("option");
          	    option.value = data[i];
          	    option.text = data[i];
          	    selectList.appendChild(option);
     
          	}
      
            },
            error: function (err) {
              console.log("error occured from server");
            }
          });
    }
    
    function hideAll(){
          var containers = ["#xmlcontainer","#jsoncontainer","#spatialcontainer","#crosscontainer"];
          for(var i=0;i<containers.length;i++){
              $(containers[i]).hide();
          }
          
    }
    function callView() {
      var item = document.getElementById("selectID");
    
      hideAll();
      if (item.value == 1) {
        //$("#xmlcontainer").hide();
        $("#jsoncontainer").show();
      }
      if (item.value == 2) {
       // $("#jsoncontainer").hide();
        $("#xmlcontainer").show();
      }
     if (item.value == 3) {
       // $("#jsoncontainer").hide();
       
        $("#spatialcontainer").show();
        getAllCity();
      }
       if (item.value == 4) {
       // $("#jsoncontainer").hide();
        $("#crosscontainer").show();
      }
    }

    function ensureNumberic(e) {
      let value = document.getElementById("idField").value;
      if (value.length + 1 > 3) {
        return false;
      }
      var keyValue = e.key;
      if (keyValue.match(/[0-9]/g)) {
        return true;
      }
      return false;
    }

    // JSON Functions-------------------------------------
    
    function getJsonById() {
  	 let id = document.getElementById("idField");
  	 if(!id.value.length >0){
  		 alert("Please enter a valid id");
  		 return;
  	 }
      $.ajax({
        method: "GET",
        url: "/json/product/"+id.value,
        dataType:'json',
        success: function (data) {
      	 let item = document.querySelector("#jsonBodyContainer");
      	 item.innerHTML = JSON.stringify(data);
        },
        error: function (xhr,err) {
      	  let item = document.querySelector("#jsonBodyContainer");
            if(xhr.status == 200){
            	
           	 	item.innerHTML = "There exists no any data for given pid";
            }else{
            	item.innerHTML = "Server error";
            }
            
        }
      });
    }
    
    function updateJsonById(){
  	  ///product/update
  	  let item = document.querySelector("#jsonBodyContainer");
  	  let obj;
  	  try{
  		  obj= JSON.parse(item.innerText);
  		  
  	  }catch(e){
  		  alert("Not a proper json");
  		  return;
  	  }
  	 
  	  if(obj.pid<0){
  		  alert("PID NOT VALID");
  		  return;
  	  }
  	  let dataToSend = JSON.stringify(obj);
  	  //console.log(obj);
  	  $.ajax({
            method: "POST",
            url: "/json/product/update",
            dataType:'text',
            contentType: 'application/json',
            data:JSON.stringify(obj),
            success: function (data) {
          	 let item = document.querySelector("#jsonBodyContainer");
          	 item.innerHTML = data;
            },
            error: function (xhr,err) {
          	  console.log("error occured from server");
            }
          });
  
    }
    
    function deleteJsonById(){
  	  ///product/update
  	  let item = document.querySelector("#jsonBodyContainer");
  	  let obj = JSON.parse(item.innerText);
  	  if(obj.pid<0){
  		  alert("PID NOT VALID");
  		  return;
  	  }
  	  let dataToSend = JSON.stringify(obj);
  	  console.log(obj);
  	  $.ajax({
            method: "POST",
            url: "/json/product/delete",
            contentType: 'application/json',
            dataType:'text',
            data:JSON.stringify(obj),
            success: function (data) {
          	 let item = document.querySelector("#jsonBodyContainer");
          	 item.innerHTML = "Product deleted successfully"
            },
            error: function (err) {
              console.log("error occured from server");
            }
          });
  
    }
    
    
    function insertJson(){
  	  ///product/update
  	  let item = document.querySelector("#jsonBodyContainer");
  	  let obj;
  	  try{
  		  obj = JSON.parse(item.innerText);
  	  }catch(e){
  		  alert("Not a valid JSON");
  		  
  	  }
  	  
  	  if(obj.pid<0){
  		  alert("PID NOT VALID");
  		  return;
  	  }
  	  let dataToSend = JSON.stringify(obj);
  	  console.log(obj);
  	  $.ajax({
            method: "POST",
            url: "/json/product/insert",
            contentType: 'application/json',
            dataType:'text',
            data:JSON.stringify(obj),
            success: function (data) {
          	 let item = document.querySelector("#jsonBodyContainer");
          	 item.innerHTML = data;
            },
            error: function (err) {
              console.log("error occured from server");
            }
          });
  
    }
    
    //----------------XML Functions
    function getXmlData(){
  	  var item =document.getElementById("selectXmlDocumentId");
  	  $.ajax({
            method: "GET",
            url: "/xml/read/"+item.value,
            dataType:'xml',
            success: function (data) {
          	 console.log(data);
          	 let item = document.querySelector("#xmlBodyContainer");
          	 let parsedData = new XMLSerializer().serializeToString(data);
          	 item.innerText = parsedData;
            },
            error: function (err) {
              console.log("error occured from server");
            }
          });
    }
    function updateXmlData(){
    	let id = document.getElementById("selectXmlDocumentId").value;
    	let query = document.getElementById("queryXml").value+"/text()";
    	let value =document.getElementById("valueXml").value;
    	if(id.length <1 || query.length<1 || value.length<1){
    		alert("Missing fields");
    		return;
    	}
    	var data = {query:query,id:id,value:value};
    	$.ajax({
      		method:"POST",
      		url:"/xml/update",
      		contentType: "application/json",
      	    dataType: "text",
      		data:JSON.stringify(data),
      	  	success: function(data){
      	  		let item = document.querySelector("#xmlBodyContainer");
             	 	item.innerHTML = data;
             	 	refreshDropDown();
      	  	},
      	  	error:function(err){
      	  		console.log("error occured while inserting");
      	  	}
      	  	
      	  });
    	
    }
    
    function deleteXmlData(){
    	var item =document.getElementById("selectXmlDocumentId").value;
    	let data = {id:item};
    	$.ajax({
    		method:"POST",
    		url:"/xml/delete",
    		contentType: "application/json",
      	    dataType: "text",
      	  	data:JSON.stringify(data),
    	  	success: function(data){
    	  		let item = document.querySelector("#xmlBodyContainer");
           	 	item.innerHTML = data;
           	 	refreshDropDown();
    	  	},
    	  	error:function(err){
    	  		console.log(err);
    	  		let item = document.querySelector("#xmlBodyContainer");
           	 	item.innerHTML = "<p>error occured while Deleting</p>";
    	  	}
    	});
    }
    
    function insertXmlData(){
    	
  	  var t = document.getElementById("xmlBodyContainer");
  	  let data = t.innerText.trim();
  	  let parser = new DOMParser();
  	  let xmldoc = parser.parseFromString(data,"text/xml");
  	  console.log("before sending");
  	  console.log(data);
  	  
  	  try{
  		/*  console.log(xmldoc.documentElement.cloneNode(false));
  		if(xmldoc.documentElement.cloneNode(false) !="<order></order>"){
  			alert("Root element to be order");
  			return;
  		} */
  		if(xmldoc.getElementsByTagName("order")[0].parentNode.parentNode !=null){
  			alert("Order tag is not the parent tag");
  			return;
  		}
  		var nodeList = (xmldoc.getElementsByTagName("order")[0].childNodes);
  		var ar = new Array();
  		nodeList.forEach(x=>{
  			ar.push(x.nodeName);
  		});
  		indexOfId = ar.indexOf("id");
  		console.log(xmldoc.getElementsByTagName("order")[0].childNodes[indexOfId].nodeName);
  		 if(xmldoc.getElementsByTagName("order")[0].childNodes[indexOfId].nodeName !="id"){
  			alert("Id should be immediate child of order node");
  			return;
  		} 
  			
  		let id=  xmldoc.getElementsByTagName("id")[0].childNodes[0].nodeValue;
  		if(xmlIdsDropdown.includes(parseInt(id))){
  			alert("Entry with the id exists, please change the id and try again");
  			return;
  		}
  		
  		if(isNaN(parseInt(id))){
  			alert("Id not a number");
  			return;
  		}
  	  }catch(e){
  		  alert("Please have Order tag as root and Id as its child");
  		  return;
  	  }
  	 $.ajax({
  		method:"POST",
  		url:"/xml/insert",
  		contentType: "text/xml",
  	    dataType: "text",
  		data:data,
  	  	success: function(data){
  	  		let item = document.querySelector("#xmlBodyContainer");
         	 	item.innerHTML = data;
         	 	refreshDropDown();
  	  	},
  	  	error:function(err){
  	  		console.log("error occured while inserting");
  	  	}
  	  	
  	  });
    }
	function clearScreen(){
		let item = document.querySelector("#xmlBodyContainer");
		item.innerText ="";
	}
	
	//-- Common functions
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
        

      //Spatial functions
      
       var dataObject = [{id:'1',name:'test',long:'-74.006058',lat:'38.91254'},{id:'2',name:'test_2',lat:'-75.006058',long:'39.91254'}];
      function getAllCity(){
       var selectList  = document.getElementById("selectSpatialCityId");
  	  $("#selectSpatialCityId").empty();
         $.ajax({
        method: "GET",
        url: "/spatial/allCities",
        dataType:'json',
        success: function (data) {
            dataObject =data;
            
            for(let i=0;i<data.length;i++){
                var option = document.createElement("option");
                option.value = data[i].id;
                option.text = data[i].name;
                selectList.appendChild(option);
                
            }
             
            showMap();
        },
        error: function (xhr,err) {
      	  console.log(err);
          return [];
            
        }
      });   
      }
      
     
      function unpinCity(){
      var item =document.getElementById("selectSpatialCityId");
      var obj = {id:item.value};
      $.ajax({
            method: "POST",
            url: "/spatial/delete",
            contentType: 'application/json',
            dataType:'text',
            data:JSON.stringify(obj),
            success: function (data) {
                alert(data);
          	 getAllCity();
            },
            error: function (err) {
              console.log("error occured from server");
            }
          });
      
      }
      
      function insertNewCity(){
          var cityElm = document.getElementById("spatialCity").value;
          var cityLong = document.getElementById("spatialLong").value;
          var cityLat = document.getElementById("spatialLat").value;
        
          if(cityElm.length<1 || cityLong.length <1 || cityLat.length<1){
            alert("missing info");
            return;
          }
          
          var obj = {name:cityElm,long:cityLong,lat:cityLat};
          console.log("From browser bfore insert");
          console.log(obj);
          $.ajax({
            method: "POST",
            url: "/spatial/insert",
            contentType: 'application/json',
            dataType:'text',
            data:JSON.stringify(obj),
            success: function (data) {
                alert("Inserted");
                getAllCity();
            },
            error: function (err) {
              console.log("error occured from server");
            }
          });
          
          
      }
      
      function showMap()
      {      
          var baseURL  = "http://maps.oracle.com/mapviewer";
         
          var getData = dataObject;
          console.log(getData)
          if(getData.length>0){
           var mapCenterLon = getData[0].long;
          var mapCenterLat = getData[0].lat;
           var mapZoom      =  3;  
          var mpoint = new OM.geometry.Point(mapCenterLon,mapCenterLat,8307);
          }
         
         
          
          var map = new OM.Map(
          document.getElementById('map'),
          {
              mapviewerURL: baseURL
          }) ;       
          var tileLayer = new OM.layer.TileLayer( "baseMap", 
          {
              dataSource:"elocation_mercator", 
              tileLayer:"world_map", 
              tileServerURL:baseURL+"/mcserver"
          });
          
          map.addLayer(tileLayer) ;

          layer = new OM.layer.MarkerLayer("markerlayer4");

          // set all properties after a map marker is added, srid is default (8307)
        var insertMapMarker1 = function(id, cx, cy,  label, draggable) {
                var mm = new OM.MapMarker();
                layer.addMapMarker(mm);   // add a map marker into marker layer
                mm.setPosition(cx, cy);
               // mm.setMarkerText(text);  // it will also set the marker text. 
                mm.setLabel(label);  // it will also set the marker text. 
                mm.setID(id);
               
                var renderStyle = new OM.style.Marker({
                      width: 50,
                      height: 50,
                      src: "/resources/images/pin.png",
                      xOffset: 0,
                      yOffset: 0
                    });
                var textStyle = new OM.style.Text({
                    styleName:"txt",
                    fill:"#00a000",
                    fontStyle: OM.Text.FONTSTYLE_NORMAL,
                    fontFamily:"Arial",
                    fontSize:0
                });
                var labelStyle = new OM.style.Text({
                    //styleName: '',
                    fill: "#00a000",
                    fontStyle: OM.style.Text.FONTSTYLE_ITALIC,
                    fontSize: 0,
                    sticky: false
                });
                mm.setRenderingStyle(renderStyle);                
                mm.setMarkerTextStyle(textStyle);                
                mm.setLabelingStyle(labelStyle);  
        };
        // an obj with all properties to instantiate a map marker
        var insertMapMarker4 = function(id, cx, cy, srid, text, label) {
                var myobj = {id: id,
                             markerText: text,
                             label: label,
                             dragStart: dragStart,
                             dragEnd: dragEnd,
                             dragging: dragging,
                             position: {'x': cx, 'y': cy,'srid': srid}};
                var mm = new OM.MapMarker(myobj);
                layer.addMapMarker(mm);   // add a map marker into marker layer
        };
        
        for (var i = 0; i < getData.length; i++) {
        insertMapMarker1(getData[i].id, getData[i].long, getData[i].lat,getData[i].name, false);  // id, cx, cy, srid, label, draggable
        //insertMapMarker4(getData[i].STORE_ID, getData[i].CENTER_LONG, getData[i].CENTER_LAT, 8307, 'M'); 
      }
        var defaultLabelSty = new OM.style.Text({
            //styleName: 'defaultLabelSty', 
            fill: "#ffffff",
            fontStyle: OM.style.Text.FONTSTYLE_ITALIC,
            fontSize: 12,
            sticky: false
        });
        layer.setLabelingStyle(defaultLabelSty);  // you need set a map marker's labeling style from a map marer instance
        map.addLayer(layer);
        layer.setToolTipCustomizer(featureTip);
        layer.setLabelsVisible(true);


          navigationPanelBar=new OM.control.NavigationPanelBar();
          map.addMapDecoration(navigationPanelBar);
          map.setMapCenter(mpoint);
          
          map.setMapZoomLevel(mapZoom) ;
          map.init() ;
          
        }   
        
    function featureTip(f)
        {
        return "STORE ID: "+f.id+"\nSTORE NAME: "+f.label
    }
