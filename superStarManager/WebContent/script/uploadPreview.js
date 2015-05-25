
function previewImage(file,picid,divid,width,height){
	var MAXWIDTH  = 200;  
	var MAXHEIGHT = 200;  
	if(width==null){
		width =MAXWIDTH ;
	}
	if(height==null){
		height=MAXHEIGHT;
	}
	  var div = document.getElementById(divid);  
	  if (file.files && file.files[0]) {  
		    div.innerHTML = '<img id='+picid+'>';  
		    var img = document.getElementById(picid);  
		    img.onload = function(){  
		      //var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);  
		      img.width = width;  
		      img.height = height;  
		      //img.style.marginLeft = rect.left+'px';  
		      //img.style.marginTop = rect.top+'px';  
		    }  
		    var reader = new FileReader();  
		    reader.onload = function(evt){img.src = evt.target.result;}  
		    reader.readAsDataURL(file.files[0]);  
	  }else{  
		    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';  
		    file.select();  
		    var src = document.selection.createRange().text;  
		    div.innerHTML = '<img id='+picid+'>';  
		    var img = document.getElementById(picid);  
		    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;  
		    /*var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);  
		    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);*/  
		    div.innerHTML = "<div id=divhead style='width:"+width+"px;height:"+height+"px;"+sFilter+src+"\"'></div>";  
	  }  
	}  
	function clacImgZoomParam( maxWidth, maxHeight, width, height ){  
	    var param = {top:0, left:0, width:width, height:height};  
	    if( width>maxWidth || height>maxHeight ){  
	        rateWidth = width / maxWidth;  
	        rateHeight = height / maxHeight;  
	          
	        if( rateWidth > rateHeight ){  
	            param.width =  maxWidth;  
	            param.height = Math.round(height / rateWidth);  
	        }else{  
	            param.width = Math.round(width / rateHeight);  
	            param.height = maxHeight;  
	        }  
	    }  
	      
	    param.left = Math.round((maxWidth - param.width) / 2);  
	    param.top = Math.round((maxHeight - param.height) / 2);  
	    return param;  
	}  