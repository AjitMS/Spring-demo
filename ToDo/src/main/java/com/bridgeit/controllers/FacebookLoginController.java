package com.bridgeit.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.User;
import com.bridgeit.service.UserService;
import com.bridgeit.socialUtility.FBConnection;
import com.bridgeit.socialUtility.FBGraph;
import com.bridgeit.tokenAuthentication.TokenGenerator;

@RestController
public class FacebookLoginController {

	@Autowired
	UserService userService;
	User user;
	@Autowired
	TokenGenerator generator;

	@Autowired
	FBConnection fbConnection;

	@Autowired
	TokenGenerator tokenService;

	@GetMapping("/fbconnect")
	public void initialConnect(HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println("Initial login");
		String fbLoginURL = fbConnection.getFBAuthUrl();
		System.out.println("FBLoginURL: " + fbLoginURL);
		response.sendRedirect(fbLoginURL);
	}

	@GetMapping("/fblogin")
	public void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String code;
		code = req.getParameter("code");
		System.out.println("Code is: " + code);
		fbConnection = new FBConnection();
		String fbAccessToken = fbConnection.getAccessToken(code);// Facebook's access token

		FBGraph fbGraph = new FBGraph(fbAccessToken);
		String graph = fbGraph.getFBGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		User user = new User();
		user.setFirstName(fbProfileData.get("name"));
		user.setIsValid(true);
		Integer userId = userService.registerUser(user);

		tokenService.generateTokenAndPushIntoRedis(userId, "accesstoken");
		tokenService.generateTokenAndPushIntoRedis(userId, "refreshtoken");

		// for debugging
		System.out.println("<h1>Facebook Login using Java</h1>");
		System.out.println("<h2>Application Facebook login</h2>");
		System.out.println("<div>Welcome " + fbProfileData.get("name"));
		System.out.println("<div>Your Id: " + fbProfileData.get("id"));
		System.out.println("<div>You are " + fbProfileData.get("gender"));

		System.out.println("Homepage");
		RequestDispatcher dispatcher = req.getRequestDispatcher("fbsuccess.jsp");
		req.setAttribute("user", user);
		dispatcher.forward(req, res);
	}
}
