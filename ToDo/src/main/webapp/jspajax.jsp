<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">
<title>AJAX Test</title>
</head>
<body>

	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">New message</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					
					<div class="content">Output table</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Send message</button>
				</div>
			</div>
		</div>
	</div>


	<div>
		<button id="button1" class="btn btn-primary btn-lg btn-block"
			data-toggle="modal" data-target="#exampleModal"
			data-whatever="Commerce">click to bring JSON Values</button>
	</div>

	<div class="content">right here !</div>
</body>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"
	integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
	integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
	integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
	crossorigin="anonymous"></script>
<script>

		$.ajax({

			// The URL for the request
			url : "TestController",

			// The data to send (will be converted to a query string)
			data : {
				id : 123
			},

			// Whether this is a POST or GET request
			type : "GET",

			// The type of data we expect back
			dataType : "json",
		})
		// Code to run if the request succeeds (is done);
		// The response is passed to the function
		.done(function(json) {
			
			$.each(json, function(key, value){
				$('.content').html(value['bookName']+" "+value['bookAuthor']+" "+value['bookCategory']);
				console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
			})
			
			//$('.content').html(value[bookName]+" "+value[bookAuthor]+" "+value[bookCategory]);
		
		})
		// Code to run if the request fails; the raw request and
		// status codes are passed to the function
		.fail(function(xhr, status, errorThrown) {
			alert("Sorry, there was a problem!");
			console.log("Error: " + errorThrown);
			console.log("Status: " + status);
			console.dir(xhr);
		})
		// Code to run regardless of success or failure;
		/* .always(function(xhr, status) {
			alert("The request is complete!");
		}); */
</script>
</html>