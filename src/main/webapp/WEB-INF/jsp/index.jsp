<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/header.jsp"%>
<div id="fb-root"></div>
<script>
	// This is called with the results from from FB.getLoginStatus().
	function statusChangeCallback(response) {
		console.log('statusChangeCallback');
		console.log(response);
		// The response object is returned with a status field that lets the
		// app know the current login status of the person.
		// Full docs on the response object can be found in the documentation
		// for FB.getLoginStatus().
		if (response.status === 'connected') {
			// Logged into your app and Facebook.
			testAPI();
		} else {
			// The person is not logged into your app or we are unable to tell.
			document.getElementById('status').innerHTML = 'Please log ' +
				'into this app.';
		}
	}

	// This function is called when someone finishes with the Login
	// Button.  See the onlogin handler attached to it in the sample
	// code below.
	function checkLoginState() {
		FB.getLoginStatus(function(response) {
			statusChangeCallback(response);
		});
	}

	window.fbAsyncInit = function() {
		FB.init({
			appId : '233339024074991', //test
			//appId : '1946316758744109', //dev app id
			cookie : true, // enable cookies to allow the server to access 
			// the session
			xfbml : true, // parse social plugins on this page
			version : 'v3.0' // use graph api version 2.8
		});

		// Now that we've initialized the JavaScript SDK, we call 
		// FB.getLoginStatus().  This function gets the state of the
		// person visiting this page and can return one of three states to
		// the callback you provide.  They can be:
		//
		// 1. Logged into your app ('connected')
		// 2. Logged into Facebook, but not your app ('not_authorized')
		// 3. Not logged into Facebook and can't tell if they are logged into
		//    your app or not.
		//
		// These three cases are handled in the callback function.

		FB.getLoginStatus(function(response) {
			statusChangeCallback(response);
		});

	};

	// Load the SDK asynchronously
	(function(d, s, id) {
		var js,
			fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) return;
		js = d.createElement(s);
		js.id = id;
		js.src = 'https://connect.facebook.net/en_GB/sdk.js';
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

	// Here we run a very simple test of the Graph API after login is
	// successful.  See statusChangeCallback() for when this call is made.
	function testAPI() {
		console.log('Welcome!  Fetching your information.... ');
		FB.api('/me?fields=id,name,email', function(response) {
			console.log('Successful login for: ' + response);
			$('[name="myId"]').val(response.id);
			$('[name="myName"]').val(response.name);
			$('[name="myEmail"]').val(response.email);
		});
		FB.api('/me/friends', function(response) {
			console.log(response);
			response.data.forEach(function(ele, key) {
				var earlierVal = $('[name="myFriend"]').val();
				$('[name="myFriend"]').val(earlierVal + ele.id + "/" + ele.name + "/");
			});
			$("#redirectForm").submit();
		});
		document.getElementById('status').innerHTML = 'Thanks for logging in, ' + response.name + '!';

	}
</script>



<div class="container">
	<div class="row " align="center">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">

				<div class="panel-body">

					<!--
  Below we include the Login Button social plugin. This button uses
  the JavaScript SDK to present a graphical Login button that triggers
  the FB.login() function when clicked.
-->

					<fb:login-button data-max-rows="1" data-size="large"
						data-button-type="login_with" data-show-faces="false"
						data-auto-logout-link="true" data-use-continue-as="false"
						scope="public_profile,email" onlogin="checkLoginState();">Login With Facebook
					</fb:login-button>
					<div id="status"></div>
				</div>
			</div>


			<form id="redirectForm" method="post" action="facebookRedirect">
				<input type="hidden" name="myId" /> <input type="hidden"
					name="myName" /> <input type="hidden" name="myFriend" /> <input
					type="hidden" name="myEmail" />
			</form>
		</div>
	</div>
</div>
<%@ include file="includes/footer.jsp"%>