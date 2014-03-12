$(document).ready(function(){
	
	//$( "#listview_collap" ).on( "collapsibleexpand", function( event, ui ) {
		 $("#list_temperature").val("");
		$.ajax({url:'getds18b20',
				dataType:'json' ,
				success:function(data){
						if(data.status == 200){
							for(var i =0;i < data.data.length;i++){
								var newTime = new Date(data.data[i].time); //就得到普通的时间了 
								var nowStr = newTime.format("yyyy-MM-dd hh:mm:ss"); 
								$("#list_temperature").append("<li ><a href=\"#\" class=\"ui-btn ui-btn-icon-right ui-icon-carat-r\" >" + data.data[i].temperature + "&nbsp;&nbsp;&nbsp;&nbsp;"+ nowStr + "</a></li>")
							}
						}else {
							$("#list_temperature").append("<li ><a href=\"#\" class=\"ui-btn ui-btn-icon-right ui-icon-carat-r\" style=\"color:read\">" + data.error + "</a></li>")
						}
					}
		});
		$("#list_temperature").listview( "refresh" );
				
	//} );
	
	Date.prototype.format =function(format)
    {
        var o = {
        "M+" : this.getMonth()+1, //month
		"d+" : this.getDate(),    //day
		"h+" : this.getHours(),   //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		"S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4- RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
        RegExp.$1.length==1? o[k] :
        ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }
 


	
});

$(document).on("pageinit","#home",function(){

  

});


