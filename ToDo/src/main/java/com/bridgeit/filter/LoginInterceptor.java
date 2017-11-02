package com.bridgeit.filter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgeit.tokenAuthentication.TokenGenerator;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Autowired
	TokenGenerator tokenService;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		logger.info("Prehandle is called. Request is: " + req + " . Response is: " + res + " . handler is: " + handler);
		String accessToken = req.getHeader("accesstoken");
		String refreshToken = req.getHeader("refreshtoken");
		Integer rUserId;
		// first validate accessToken
		if (!(tokenService.verifyUserToken(accessToken).compareTo(-1) == 0)) {
			// if accessToken matches
			// out.write("Access Token Validated");
			logger.info("Access Token Validated");
			return true;
		} else
		// if accessToken fails, validate refreshToken
		{
			logger.info("Access Token Validation failed. validating refresh token");
			rUserId = tokenService.verifyUserToken(refreshToken);
			if (!(rUserId.compareTo(-1) == 0))
			// if refreshToken correct, generate new access token
			{
				// out.print("only refresh tokens valid. new Access token le " + message);
				logger.info("Refresh Token Validated ! generating new Access token");
				return true;
			} else
				logger.info("Both token failed to validate");
			// if refresh token also fails
			PrintWriter out = res.getWriter();
			out.print("both token failed");
			res.setStatus(HttpStatus.BAD_REQUEST.value());
			out.close();
			return false;
		}

	}

}
