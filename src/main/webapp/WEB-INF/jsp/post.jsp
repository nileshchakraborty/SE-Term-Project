<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/login-header.jsp"%>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/video.js/6.7.2/video.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/RecordRTC/5.4.6/RecordRTC.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/adapterjs/0.15.0/adapter.min.js"></script>
<script
	src="https://collab-project.github.io/videojs-record/dist/wavesurfer.min.js"></script>
<script
	src="https://collab-project.github.io/videojs-record/dist/wavesurfer.microphone.min.js"></script>
<script
	src="https://collab-project.github.io/videojs-record/dist/videojs.wavesurfer.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/videojs-record/2.1.2/videojs.record.min.js"></script>
<div id="image">

	<video id="myImage" class="video-js vjs-default-skin"></video>

	<script>
		var player = videojs("myImage", {
			controls : true,
			width : 320,
			height : 240,
			controlBar : {
				volumePanel : false,
				fullscreenToggle : false
			},
			plugins : {
				record : {
					image : true,
					debug : true
				}
			}
		}, function() {
			// print version information at startup
			videojs.log('Using video.js', videojs.VERSION,
				'with videojs-record', videojs.getPluginVersion('record'));
		});
	
		// error handling
		player.on('deviceError', function() {
			console.warn('device error:', player.deviceErrorCode);
		});
	
		// snapshot is available
		player.on('finishRecord', function() {
			// the blob object contains the image data that
			// can be downloaded by the user, stored on server etc.
			console.log('snapshot ready: ', player.recordedData);
			$('#takepicture').val(player.recordedData)
			
		});
		
		$(document).ready(function() {
			$("#saveButton").on("click", function() {
				$("#imageForm").submit();
			});
		});
	</script>

	<form id="imageForm" method="post" action="analysepost"
		enctype="multipart/form-data">
		<input type="hidden" name="takepicture" id="takepicture" />
	</form>

	<button id="saveButton">Save</button>
</div>
<%@ include file="includes/footer.jsp"%>
