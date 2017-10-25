package com.bridgeit.tokenAuthentication;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;

@Service("/tokenService")
public class TokenGenerator {

	@Autowired
	Token token;

	@Autowired

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOps;

	public Token generateToken(User user, String tokenType) {
		UUID uuid = UUID.randomUUID();
		String randomUUID = uuid.toString().replaceAll("-", "");
		System.out.println("Random UUID is: " + randomUUID);
		token.setTokenValue(randomUUID);
		token.setUserId(user.getId());
		token.setTokenType(tokenType);
		return token;

	}

	Logger logger = LoggerFactory.getLogger(TokenGenerator.class);
	RMapCache<String, Token> map;

	public void pushIntoRedis(User user, Token myToken) {

		Config config = new Config();

		config.useSingleServer().setAddress("127.0.0.1:6379");

		// LocalCachedMapOptions localCachedMapOptions =
		// LocalCachedMapOptions.defaults()
		// .evictionPolicy(EvictionPolicy.LFU);

		RedissonClient redisson = Redisson.create(config);

		try {

			map = redisson.getMapCache("TestMap");

			switch (myToken.getTokenType()) {
			case "accesstoken":
				map.put(token.getTokenValue(), token, 15, TimeUnit.MINUTES);
				break;
			case "refreshtoken":
				map.put(token.getTokenValue(), token, 24, TimeUnit.HOURS);
				break;
			case "forgottoken":
				map.put(token.getTokenValue(), token, 24, TimeUnit.HOURS);
				break;
			default:
				logger.info("Invalid Choice");
			}

		}

		finally {

			// redisson.shutdown();
		}

		// saving same token for userId into Redis
		// push into redis
		// no need to push into MySQL DB anymore

		System.out.println("Token " + token.getTokenValue() + " Set successfully for user: " + user.getId());
	}

	public boolean verifyUserToken(Integer userId, String userTokenId) {
		String value = map.get(userId).getTokenValue();
		System.out.println("user token: " + userTokenId);
		System.out.println("Redis stored token: " + value);
		if (value.equals(userTokenId)) {
			System.out.println("Token Authentication Success");
			return true;
		}
		System.out.println("Token Authentication failed");
		return false;
	}

}
