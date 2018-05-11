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

<div id="image"><img src="${post.imageUrl}"/></div>
<div id="audio">
	<audio id="myAudio" class="video-js vjs-default-skin"></audio>

	<script>
		var obj = null;
		var player = videojs("myAudio", {
			controls : true,
			width : 600,
			height : 300,
			plugins : {
				wavesurfer : {
					src : "live",
					waveColor : "#36393b",
					progressColor : "black",
					debug : true,
					cursorWidth : 1,
					msDisplayMax : 20,
					hideScrollbar : true
				},
				record : {
					audio : true,
					video : false,
					maxLength : 20,
					debug : true
				}
			}
		}, function() {
			// print version information at startup
			videojs.log('Using video.js', videojs.VERSION,
				'with videojs-record', videojs.getPluginVersion('record'),
				'+ videojs-wavesurfer', videojs.getPluginVersion('wavesurfer'),
				'and recordrtc', RecordRTC.version);
		});
	
		// error handling
		player.on('deviceError', function() {
			console.log('device error:', player.deviceErrorCode);
		});
	
		// user clicked the record button and started recording
		player.on('startRecord', function() {
			console.log('started recording!');
		});
	
		// user completed recording and stream is available
		player.on('finishRecord', function() {
			// the blob object contains the recorded data that
			// can be downloaded by the user, stored on server etc.
	
			console.log('finished recording: ', player.recordedData);
			var reader = new FileReader();
			var base64data;
			reader.readAsDataURL(player.recordedData);
			reader.onloadend = function() {
				base64data = reader.result;
				console.log("----------->" + base64data);
				$("#record").val(base64data);
			}
		});
	
		$(document).ready(function() {
			$("#saveaudioButton").on("click", function() {
				$("#audioForm").submit();
			});
		});
	</script>


	<form id="audioForm" method="post" action="analyseaudiopost"
		enctype="multipart/form-data">
		<input type="hidden" name="record" id="record" />
	</form>

	<button id="saveaudioButton">Save</button>
</div>
<%@ include file="includes/footer.jsp"%>