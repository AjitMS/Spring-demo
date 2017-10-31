package com.bridgeit.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;
import com.bridgeit.entity.UserLoginPair;
import com.bridgeit.service.UserService;
import com.bridgeit.socialUtility.FBConnection;
import com.bridgeit.socialUtility.FBGraph;
import com.bridgeit.tokenAuthentication.TokenGenerator;

@RestController("/")
public class UserController {

	@Autowired
	UserService userService;
	User user;
	@Autowired
	TokenGenerator generator;

	@GetMapping("/")
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String code;
		code = req.getParameter("code");
		System.out.println("Code is: " + code);
		ServletOutputStream out = res.getOutputStream();
		out.println(
				"<a href=\"http://www.facebook.com/dialog/oauth?client_id=1129138110550336&redirect_uri=http://localhost:8080/ToDo&scope=email\n"
						+ "\">Facebook Login using Java </a>");
		if (code != null || "".equals(code)) {

			FBConnection fbConnection = new FBConnection();
			String accessToken = fbConnection.getAccessToken(code);

			FBGraph fbGraph = new FBGraph(accessToken);
			String graph = fbGraph.getFBGraph();
			Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

			out.println("<h1>Facebook Login using Java</h1>");
			out.println("<h2>Application Facebook login</h2>");
			out.println("<div>Welcome " + fbProfileData.get("name"));
			out.println("<div>Your Id: " + fbProfileData.get("id"));
			out.println("<div>You are " + fbProfileData.get("gender"));

			// for debugging
			System.out.println("<h1>Facebook Login using Java</h1>");
			System.out.println("<h2>Application Facebook login</h2>");
			System.out.println("<div>Welcome " + fbProfileData.get("name"));
			System.out.println("<div>Your Id: " + fbProfileData.get("id"));
			System.out.println("<div>You are " + fbProfileData.get("gender"));
		}
		System.out.println("Homepage");
	}

	@PostMapping("/login")
	public ResponseEntity<List<Token>> loginUser(@RequestBody UserLoginPair loginPair, HttpServletRequest request) {
		System.out.println("Into Login");
		System.out.println("loign pair is: " + loginPair);
		String email = loginPair.getEmail();
		String password = loginPair.getPassword();
		// grab entire user by email if proper credentials
		if (userService.loginUser(email, password)) {
			System.out.println("login success");
			user = userService.getUserByEmail(email, user);

			// TokenGenerator generator = new TokenGenerator();

			// stop making objects. instead use @Autowired
			// make sure user prototype type of @Autowired instead of singleton
			// mistakenly @Autowired token and it returned the same token always for both
			// refresh and access

			Token accessToken = generator.generateTokenAndPushIntoRedis(user.getId(), "accesstoken");
			Token refreshToken = generator.generateTokenAndPushIntoRedis(user.getId(), "refreshtoken");
			List<Token> tokenList = new ArrayList<>();
			tokenList.add(accessToken);
			tokenList.add(refreshToken);
			// generate token for specific user id and store it in REDIS

			System.out.println("ACCESS TOKEN: " + accessToken);
			System.out.println("REFRESH TOKEN: " + refreshToken);
			System.out.println("Token List " + tokenList);

			// send token link to user email
			userService.sendLoginVerificationToken(user, accessToken, request);
			System.out.println("Email has been sen to " + user.getEmail() + " .please check");
			return new ResponseEntity<List<Token>>(tokenList, HttpStatus.OK);
		}
		System.out.println("Login failed");
		return new ResponseEntity<List<Token>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/login/{userId}/{tokenId}")
	public ResponseEntity<String> verifyUserToken(@PathVariable("userId") Integer userId,
			@PathVariable("tokenId") String userTokenId) {

		// first validate access token is intact, if yes login

		if (generator.verifyUserToken(userId, userTokenId, "accessToken")) {
			System.out.println("Congratulations ! Access Token validation sucess");
			return new ResponseEntity<String>("Token authenticated ! Redirecting...", HttpStatus.ACCEPTED);
		} else {
			// else validate refresh token
			System.out.println("Access token validation failed, starting refresh token validation");
			if (generator.verifyUserToken(userId, userTokenId, "refreshToken")) {

				// and generate another new access token and login
				Token newToken = generator.generateTokenAndPushIntoRedis(userId, "accessToken");

				System.out.println("New access token is generated as: " + newToken + " for user " + user.getId());
				return new ResponseEntity<String>("Token authenticated ! Redirecting...", HttpStatus.ACCEPTED);
			}
			// if refresh token fails, login fails
			else
				System.out.println("Refresh token validation failed");
			return new ResponseEntity<String>("Token not authenticated !", HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping("/login/forgotpassword")
	public ResponseEntity<String> forgotPassword(@RequestBody User user, HttpServletRequest request) {
		try {
			user = userService.getUserByEmail(user.getEmail(), user);
			System.out.println("email is: " + user.getEmail());
			System.out.println("user is: " + user);
		} catch (Exception E) {
			E.printStackTrace();
		}
		if (user == null) {
			System.out.println("No such email registered");
			return new ResponseEntity<String>("No such email registered", HttpStatus.NO_CONTENT);
		}
		// generating user token for forgot password
		// generator is autowired
		// generating a token for forgot password
		String tokenType = "acesstoken";
		Token token = generator.generateTokenAndPushIntoRedis(user.getId(), tokenType);
		userService.sendResetPasswordMail(user, request, token);

		return new ResponseEntity<String>("reset password link has been sent to " + user.getEmail(),
				HttpStatus.ACCEPTED);
	}

	@GetMapping("/login/resetpasswordtoken/{userId}/{userTokenId}")
	public ResponseEntity<String> validateResetPasswordToken(@PathVariable("userId") Integer userId,
			@PathVariable("userTokenId") String userTokenId) {
		if (generator.verifyUserToken(userId, userTokenId, "forgotToken"))
			return new ResponseEntity<String>("Redirecting to the password resetting page..", HttpStatus.OK);
		return new ResponseEntity<String>("Invalid link or incorrect token. password resetting failed. try again",
				HttpStatus.NO_CONTENT);
	}

	@PostMapping("/login/resetpassword")
	public ResponseEntity<String> resetPassword(@RequestBody User user) {

		if (user.getPassword().equals(user.getConfirmPassword())) {
			userService.resetPassword(user.getEmail(), user.getPassword());
			return new ResponseEntity<String>("Success ! proceding to Log In...", HttpStatus.OK);

		}
		return new ResponseEntity<>("passwords do not match", HttpStatus.NO_CONTENT);
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid User user, BindingResult bindingResult,
			String registeredVia) {
		System.out.println("WOOHOO !");
		if (bindingResult.hasErrors()) {
			System.out.println("Errors are: " + bindingResult);
			System.out.println("User details: " + user);
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		System.out.println("User details: " + user);

		// saving user if not exists
		if (!userService.userExists(user)) {
			userService.registerUser(user);
			System.out.println("Register Success");
		} else {
			return new ResponseEntity<String>("User Exists, please login. or forgot password ?",
					HttpStatus.NOT_ACCEPTABLE);
		}

		// Email verification
		if (!registeredVia.equalsIgnoreCase("facebook") || registeredVia.equalsIgnoreCase("google"))
			userService.sendRegistrationVerificationLink(user.getId(), user.getEmail());

		String greeting = "Thank you! \n A verification email has been sent to " + user.getEmail()
				+ ". confirm registration by accessing link in the mail";

		return new ResponseEntity<String>(greeting, HttpStatus.OK);
	}

	@GetMapping("/register/verifyuser/{id}")
	public ResponseEntity<String> verifyRegisteredUser(@PathVariable("id") Integer id) {
		userService.validateRegisteredUser(id);
		System.out.println("User verified successfully !");
		return new ResponseEntity<String>("Verified Successfully ! Redirecting to homepage...", HttpStatus.ACCEPTED);

	}
}