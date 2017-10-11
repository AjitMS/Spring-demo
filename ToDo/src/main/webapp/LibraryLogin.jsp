<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Library Login</title>

<!-- Bootstrap References -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">

<!-- JQuery References  -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
	integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
	integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
	crossorigin="anonymous"></script>

<link rel="stylesheet" type="text/css" href="src/main/CSS/signin.css" />

</head>
<body>
	<!-- Main Script  -->
	<div class="container justify-content-center col-6 supercontainer"
		align="center">

		<div class="row">
			<div class="col classpadding">
				<h3>Sign In</h3>
				<hr class="hrmargin" />
			</div>
		</div>

		<div class="row">
			<div class="col-md-6 ">
				<div class="row">
					<div class="col">
						<h4 id="faded">Use other accounts</h4>
					</div>
				</div>	
				<div class="row  hidden-md-down">
					<div class="col">
						<p>you can also sign in using your</p>
						<p>Facebook Account or Google Account</p>
					</div>
				</div>
				<div class="row buttonpadding">
					<div class="col-md-12">
						<button type="submit" class="btn btn-block facebook btn-primary">Log
							in with Facebook</button>
					</div>
				</div>

				<div class="row buttonpadding">
					<div class="col-md-12">
						<button type="submit" class="btn btn-block btn-primary">Log
							in with Google</button>
					</div>
				</div>
			</div>

			<div class="container col-md-6 sideborder">
				<div class="row">
					<div class="col">
						<h4 id="faded">Use your accounts</h4>
						<br />
					</div>
				</div>

				<div id="authentication-message" class="row alertpad hidden">
					<div class=" col alert alert-danger alert-dismissible fade show"
						role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<strong>Login Failed !</strong> Email or Password is incorrect
					</div>
				</div>

				<div id="loginagain-message" class="row hidden alertpad">
					<div
						class=" col alert alert-warning alert-dismissible fade show hidden-xs-up"
						role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<strong>Access Failed !</strong> You must Log in first
					</div>
				</div>

				<div id="logout-message" class="row hidden alertpad">
					<div
						class=" col alert alert-success alert-dismissible fade show hidden-xs-up"
						role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
							
						</button>
						<strong>Logout Successful</strong>You've Logged out successfully!
					</div>
				</div>
				<div class="row">
					<div class="col">
						<form action="LoginController" method="post" class="formpadding">

							<input type="email" class="form-control" class="form-control"
								id="email" placeholder="Enter email" name="email"> <input
								type="password" class="form-control" id="password"
								placeholder="Enter password" name="password">
							<div class="row ">
								<div class="col-12">
									<button type="submit" class="btn btn-block btn-lg btn-success">Submit</button>
									<br>
								</div>
							</div>
							<div id="pass">
								<a href="#">Forgot password?</a>
							</div>
							<div id="register">
								<a href="LibraryRegister.jsp">Register!</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<c:choose>
		<c:when test="${message=='loginerror'}">
			<script>
				$('#authentication-message').toggleClass('hidden');
			</script>
		</c:when>

		<c:when test="${message=='logout'}">
			<script>
				$('#logout-message').toggleClass('hidden');
			</script>
		</c:when>

		<c:when test="${message=='loginagain'}">
			<script>
				$('#loginagain-message').toggleClass('hidden');
			</script>
		</c:when>
	</c:choose>
</body>
</html>
