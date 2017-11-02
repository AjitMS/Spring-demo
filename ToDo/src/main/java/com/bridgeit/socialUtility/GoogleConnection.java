package com.bridgeit.socialUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleConnection {
	
	Logger logger = Logger.getLogger(GoogleConnection.class);

	
	private static final String CLIENT_ID = "953161329586-7b7o3dp1b55t7d0c86no9iifm3vfhvjh.apps.googleusercontent.com";
	private static final String REDIRECT_URI = "http://localhost:8080/ToDo/afterGoogleLogin";
	private static final String CLIENT_SECRET = "QzHjxOQOwv0eI8PHV2KEEQe_";
	private static String googleLoginUrl = "";

	static {
		try {
			googleLoginUrl = "https://accounts.google.com/o/oauth2/auth?client_id=" + CLIENT_ID + "&redirect_uri="
					+ URLEncoder.encode(REDIRECT_URI, "UTF-8") + "&response_type=code" + "&scope=profile email"
					+ "&approval_prompt=force" + "&access_type=offline";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String generateLoginUrl() {
		return googleLoginUrl;
	}

	public static String getAccessToken(String code) {
		String urlParameters = "code=" + code + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET
				+ "&redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code";

		try {
			URL url = new URL("https://accounts.google.com/o/oauth2/token");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String googleResponse = "";
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				googleResponse = googleResponse + line;
			}

			ObjectMapper objectMapper = new ObjectMapper();

			String googleAccessToken;
			try {
				googleAccessToken = objectMapper.readTree(googleResponse).get("access_token").asText();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return googleAccessToken;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getProfileData(String googleAccessToken) {
		try {
			URL urlInfo = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + googleAccessToken);
			URLConnection connection = urlInfo.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = "";
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				response = response + line;
			}
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
