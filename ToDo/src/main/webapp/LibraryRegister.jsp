<%@ page language="java" contentType="text/html"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Library Register</title>

<!-- Bootstrap References -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">

<link rel="stylesheet" type="text/css" href="CSS/register.css" />
</head>
<body>

	<div
		class="container justify-content-center col-xl-3 col-lg-6 col-md-8 col-sm-10"
		align="center" id="container">
		<div class="row">
			<div class="col">
				<h2 class="border">Create an account</h2>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col form-padding " align="left">

				<c:set var="message" scope="request" value="${message}"></c:set>

				<c:if test="${fn:contains(message, 'fullnameempty') }">
					<p class="danger">*Full name entry cannot be Empty</p>
				</c:if>

				<c:if test="${fn:contains(message, 'emailempty') }">
					<p class="danger">*Email field cannot be empty</p>
				</c:if>

				<c:if test="${fn:contains(message, 'genderempty') }">
					<p class="danger">*please select one of the gender</p>
				</c:if>

				<c:if test="${fn:contains(message, 'passwordempty') }">
					<p class="danger">*password field cannot empty</p>
				</c:if>

				<c:if test="${fn:contains(message, 'confpasswordempty') }">
					<p class="danger">*Confirm password field empty</p>
				</c:if>

				<c:if test="${fn:contains(message, 'emptyphone') }">
					<p class="danger">*phone number field empty</p>
				</c:if>

				<c:if test="${fn:contains(message, 'invalidphone') }">
					<p class="danger">*Invalid phone number</p>
				</c:if>

				<c:if test="${fn:contains(message, 'fullnamedigit') }">
					<p class="danger">*Only characters [A-Z, a-z] are allowed in
						full name field.</p>
				</c:if>

				<c:if test="${fn:contains(message, 'phonechar') }">
					<p class="danger">*please enter a valid phone number</p>
				</c:if>


				<c:if test="${fn:contains(message, 'passwordshort') }">
					<p class="danger">*Specified password is too short. please
						choose another.</p>
				</c:if>

				<c:if test="${fn:contains(message, 'passwordunmatch') }">
					<p class="danger">*Both the passwords do not match</p>
				</c:if>

				<c:if test="${fn:contains(message, 'alreadyregistered') }">
					<p class="danger">
						*Email/Phone is already Registered. Enter another email or phone OR <br>
						<a href="LibraryLogin.jsp">Login here!</a> <br>Or <i><a
							href="#">did you forgot password?</a></i>
					</p>
				</c:if>
				<form action="RegisterController" method="post" name="registration"
					id="myForm" onsubmit="return validate();">

					<label for="fullname">Full Name </label> <input type="text"
						class="is-valid form-control " id="fullname"
						placeholder="Enter full name" name="fullname">
					<div id="fullnamewarning" class="d-none invalid-feedback">Please
						enter a valid username</div>

					<br> <label for="email">Email Address </label> <input
						type="email" class="is-valid form-control" id="email"
						placeholder="Enter email" name="email">
					<div id="emailwarning" class="d-none invalid-feedback">Please
						enter a valid email</div>

					<br> <label for="phone">Phone number </label> <input
						type="text" class="is-valid form-control" id="phone"
						placeholder="Enter mobile number" name="phone">
					<div id="phonewarning" class="d-none invalid-feedback">Please
						enter a valid phone number</div>

					<br> <label for="password">Password</label> <input
						type="password" class="is-valid form-control" id="password"
						placeholder="Enter password" name="password">
					<div id="passwordwarning" class="d-none invalid-feedback">Please
						enter a valid password</div>

					<br> <label for="conf_password">Confirm Password</label> <input
						type="password" class="is-valid form-control" id="passwordwarning"
						placeholder="Repeat password" name="conf_password">
					<div id="conf_passwordwarning" class="d-none invalid-feedback">passwords
						do not match</div>

					<br> <label for="gender">Gender</label>
					<div class="row">
						<div class=" col-4 form-check  form-check-inline">
							<label class="custom-control custom-radio"> <input
								id="gender" name="gender" type="radio" value="male"
								class="custom-control-input"> <span
								class="custom-control-indicator"></span> <span
								class="custom-control-description">Male</span>
							</label>
						</div>

						<div class="col-4 form-check form-check-inline">
							<label class="custom-control custom-radio"> <input
								id="gender" name="gender" type="radio" value="female"
								class="custom-control-input"> <span
								class="custom-control-indicator"></span> <span
								class="custom-control-description">Female</span>
							</label>
						</div>
					</div>
					<div class="form-group">
						<p id="dotted-bound">
							By Creating an Account with us, you certify that you have read
							the <a href="#">Terms of Use</a> and <a href="#">privacy
								policy</a>
						</p>
					</div>
					<div class="row form-group">
						<div class="col">
							<button type="submit" class="btn btn-lg btn-block btn-success"
								id="button">Create an Account !</button>
							<br>
						</div>
					</div>
					<p>
						Already have an account?<a href="#"><strong> Sign in</strong></a>
					</p>
				</form>
			</div>
		</div>
	</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
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

	<script src = "Javascript/registervalidation.js"></script>
</body>
</html>