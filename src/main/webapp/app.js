$(document).ready(function(){
	
	
	$( "#listview_collap" ).on( "collapsibleexpand", function( event, ui ) {
		
		
		$("#list_temperature").load('getds18b20');
		$("#list_temperature").listview( "refresh" );
				
	} );
	
	
  
});
