package com.bridgeit.tokenAuthentication;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("/tokenService")
public class TokenGenerator {

	@Autowired
	Token token = new Token();

	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	public Token generateToken(String userId) {

		UUID uuid = UUID.randomUUID();
		String randomUUID = uuid.toString().replaceAll("-", "");
		System.out.println("Random UUID is: " + randomUUID);
		token.setTokenId(randomUUID);
		/*ExampleController ex = new ExampleController();
		ex.setToken(userId, randomUUID);*/
		// saving same token for userId into Redis
		System.out.println("id: " + userId + "value: " + token.getTokenId());
		System.out.println("redis Template: " + redisTemplate);
		listOps.leftPush(userId, token.getTokenId());
		System.out.println("Token " + token.getTokenId() + " Set successfully for user: " + userId);
		return token;
	}

	public boolean verifyUserToken(String userId, String userTokenId) {
		//ExampleController ex = new ExampleController();
		String value = listOps.leftPop(userId);
		System.out.println("user token: "+userTokenId);
		System.out.println("Redis stored token: "+value);
		System.out.println("Get success");
		if (value.equals(userTokenId))
			return true;
		
		//if(ex.getToken(userId).equals(userTokenId))
		return false;
	}

}
		