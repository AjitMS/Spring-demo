package com.bridgeit.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.User;
import com.bridgeit.service.UserService;
import com.bridgeit.socialUtility.GoogleConnection;
import com.bridgeit.tokenAuthentication.TokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GoogleLoginController {

	Logger logger = Logger.getLogger(GoogleLoginController.class);

	@Autowired
	UserService userService;

	@Autowired
	TokenGenerator tokenService;

	@RequestMapping(value = "/gconnect", method = RequestMethod.GET)
	public void initialConnect(HttpServletResponse response) {
		String googleLoginPageUrl = GoogleConnection.generateLoginUrl();
		try {
			response.sendRedirect(googleLoginPageUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/glogin", method = RequestMethod.GET)
	public void googleLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException {
		if (request.getParameter("error") != null) {
			String error = request.getParameter("error");
			logger.info("Error in Google Connection are: " + error);
		} else {
			String code = request.getParameter("code");

			String googleAccessToken = GoogleConnection.getAccessToken(code);

			String profileData = GoogleConnection.getProfileData(googleAccessToken);

			ObjectMapper objectMapper = new ObjectMapper();
			Integer userId = null;
			try {
				String email = objectMapper.readTree(profileData).get("email").asText();
				User user = new User();
				user = userService.getUserByEmail(email, user);
				if (user == null) {
					// if user is null, user is not registered in database
					user = new User();

					user.setEmail(email);

					String firstName = objectMapper.readTree(profileData).get("given_name").asText();
					user.setFirstName(firstName);

					String lastName = objectMapper.readTree(profileData).get("family_name").asText();
					user.setLastName(lastName);

					user.setIsValid(true);

					userId = userService.registerUser(user);
					if (userId.compareTo(-1) == 0)
						logger.info("User cannot be registered");
					return;

				} else {
					tokenService.generateTokenAndPushIntoRedis(userId, "accesstoken");
					tokenService.generateTokenAndPushIntoRedis(userId, "refreshtoken");
					RequestDispatcher dispatcher = request.getRequestDispatcher("fbsuccess.jsp");
					request.setAttribute("user", user);
					dispatcher.forward(request, response);
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}

	}
}
