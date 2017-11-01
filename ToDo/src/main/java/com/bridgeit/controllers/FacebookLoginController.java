package com.bridgeit.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

	Logger logger = Logger.getLogger(FacebookLoginController.class);
	@Autowired
	UserService userService;
	User user;

	@Autowired
	FBConnection fbConnection;

	@Autowired
	TokenGenerator tokenService;

	@GetMapping("/fbconnect")

	public void initialConnect(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("Initial login");
		String fbLoginURL = fbConnection.getFBAuthUrl();
		logger.info("FBLoginURL: " +fbLoginURL);
		response.sendRedirect(fbLoginURL);
	}

	@GetMapping("/fblogin")
	public void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String code;
		code = req.getParameter("code");
		logger.info("Code is: "+ code);
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
		logger.info("Fb Profile Data: " +fbProfileData);

		logger.info("Homepage");
		RequestDispatcher dispatcher = req.getRequestDispatcher("fbsuccess.jsp");
		req.setAttribute("user", user);
		dispatcher.forward(req, res);
	}
}
