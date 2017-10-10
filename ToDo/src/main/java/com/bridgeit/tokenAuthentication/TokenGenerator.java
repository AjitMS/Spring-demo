package com.bridgeit.tokenAuthentication;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;
import com.bridgeit.service.UserService;

@Service("/tokenService")
public class TokenGenerator {

	@Autowired
	Token token;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private UserService service;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	public Token generateToken(User user) {

		UUID uuid = UUID.randomUUID();
		String randomUUID = uuid.toString().replaceAll("-", "");
		System.out.println("Random UUID is: " + randomUUID);
		token.setTokenId(randomUUID);
		/*
		 * ExampleController ex = new ExampleController(); ex.setToken(userId,
		 * randomUUID);
		 */
		// saving same token for userId into Redis
		System.out.println("id: " + user.getId() + "value: " + token.getTokenId());
		System.out.println("redis Template: " + redisTemplate);
		// push into redis
		listOps.leftPush(user.getId(), token.getTokenId());

		// push into MySQL DB
		user.setToken(token);
		service.loginUser(user);
		
		System.out.println("Token " + token.getTokenId() + " Set successfully for user: " + user.getId());
		return token;
	}

	public boolean verifyUserToken(String userId, String userTokenId) {
		// ExampleController ex = new ExampleController();
		String value = listOps.leftPop(userId);
		System.out.println("user token: " + userTokenId);
		System.out.println("Redis stored token: " + value);
		System.out.println("Get success");
		if (value.equals(userTokenId))
			return true;

		// if(ex.getToken(userId).equals(userTokenId))
		return false;
	}

}
