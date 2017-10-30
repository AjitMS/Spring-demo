package com.bridgeit.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.socialUtility.FBConnection;
import com.bridgeit.socialUtility.FBGraph;

@RestController("/fblogin")
public class FacebookSocialController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String code = "";
	
	@GetMapping("/fblogin")
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		code = req.getParameter("code");
		System.out.println("Code is: " + code);
	/*	if (code == null || code.equals("")) {
			throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
		}*/
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);

		FBGraph fbGraph = new FBGraph(accessToken);
		String graph = fbGraph.getFBGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		ServletOutputStream out = res.getOutputStream();
		out.println("<h1>Facebook Login using Java</h1>");
		out.println("<h2>Application Facebook login</h2>");
		out.println("<div>Welcome " + fbProfileData.get("first_name"));
		out.println("<div>Your Email: " + fbProfileData.get("email"));
		out.println("<div>You are " + fbProfileData.get("gender"));
		
		//for debugging
		System.out.println("<h1>Facebook Login using Java</h1>");
		System.out.println("<h2>Application Facebook login</h2>");
		System.out.println("<div>Welcome " + fbProfileData.get("first_name"));
		System.out.println("<div>Your Email: " + fbProfileData.get("email"));
		System.out.println("<div>You are " + fbProfileData.get("gender"));
	}

}