package com.bridgeit.socialUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;

public class FBConnection {
	// get api to connect to FB
	// let user enter credentials through postman in JSON format
	// hit FB URL using id and redirect URI
	// submit the user credentials to FB API
	// if return value comes true
	// log in user and set new access for that user

	public static final String FB_APP_ID = "1129138110550336";
	public static final String FB_APP_SECRET = "859246d852d128e79c9fb533ea264994";
	public static final String REDIRECT_URI = "http://localhost:8080/ToDo";

	static String accessToken = "";

	// generates initial link to access
	public String getFBAuthUrl() {
		String fbLoginUrl = "";
		try {

			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id=" + FBConnection.FB_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8") + "&scope=email";

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fbLoginUrl is: " + fbLoginUrl);
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?" + "client_id=" + FBConnection.FB_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8") + "&client_secret="
					+ FB_APP_SECRET + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("fbGraphUrl is: " + fbGraphUrl);
		return fbGraphUrl;
	}

	public String getAccessToken(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook " + e);
			}
			
			accessToken = b.toString();
			JSONObject root = new JSONObject(accessToken);
			//JSONArray sportsArray = root.getJSONArray("sport");
			// now get the first element:
			//JSONObject firstSport = sportsArray.getJSONObject(0);
			// and so on
			accessToken = root.getString("access_token"); 
			System.out.println("access_token Is: "+accessToken);
			
			
			
			/*if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: " + accessToken);
			}*/
		}
		System.out.println("Access token is: " + accessToken);
		return accessToken;
	}
}
