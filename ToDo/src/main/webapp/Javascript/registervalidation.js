/* console.log("M....");
			
				$("#registration").submit(function(event) {
				console.log($("#fullname").val());
				}); */

function validate() {
	console.log("Entered validation");
	var fullname = document.registration.fullname;
	var email = document.registration.email;
	var phone = document.registration.phone;
	var password = document.registration.password;
	var conf_password = document.registration.conf_password;
	var errorString = "empty";
	/*
	 * if ($("#fullname").val() == "" || isNumeric($("#fullname").val())) {
	 * console.log("Entered fullname");
	 * $("#fullnamewarning").toggleClass("d-none", "addOrRemove"); return false; }
	 */

	if (fullname.value == "" || fullname.value == "\D") {
		console.log("Entered fullname");
		$("#fullname").toggleClass("is-invalid");
		$("#fullnamewarning").toggleClass("d-none");
		errorString += "fullname \n";
	}

	// check if it contains '@'
	if (email.value.indexOf("@", 0) < 0 || email.value.indexOf("com", 0) < 0
			|| email.value == "") {
		console.log("Entered email");
		$("#email").toggleClass("is-invalid");
		$("#emailwarning").toggleClass("d-none", "addOrRemove");
		errorString += "email\n";
	}

	// check if it contains '@'
	if (phone.value.length != 10 || phone.value == "") {
		console.log("Entered phone");
		$("#phone").toggleClass("is-invalid");
		$("#phonewarning").toggleClass("d-none", "addOrRemove");
		errorString += "phone\n"
	}

	if (password.value.length < 4) {
		console.log("Entered password");
		$("#password").toggleClass("is-invalid");
		$("#passwordwarning").toggleClass("d-none", "addOrRemove");
		errorString += "password\n"
	}

	if (password.value != conf_password.value) {
		console.log("Entered password");
		$("#conf_password").toggleClass("is-invalid");
		$("#conf_passwordwarning").toggleClass("d-none", "addOrRemove");
		errorString += "conf_password\n"
	}
	if (errorString != "empty") {
		window.alert("Errors are: " + errorString);
		return false;
	} else
		return true;
}