package com.bridgeit.tokenAuthentication;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

import com.bridgeit.entity.Token;

@Service("/tokenService")
public class TokenGenerator {
	Logger logger = Logger.getLogger(TokenGenerator.class);
	Config config;
	RedissonClient redisson;
	RMapCache<String, Token> tokenMap;

	@PostConstruct
	public void initializeRedis() throws IOException {

		config = new Config();

		config.useSingleServer().setAddress("127.0.0.1:6379");

		config = Config.fromJSON("{\n" + "   \"singleServerConfig\":{\n" + "      \"idleConnectionTimeout\":10000,\n"
				+ "      \"pingTimeout\":1000,\n" + "      \"connectTimeout\":10000,\n" + "      \"timeout\":3000,\n"
				+ "      \"retryAttempts\":3,\n" + "      \"retryInterval\":1500,\n"
				+ "      \"reconnectionTimeout\":3000,\n" + "      \"failedAttempts\":3,\n"
				+ "      \"password\":null,\n" + "      \"subscriptionsPerConnection\":5,\n"
				+ "      \"clientName\":null,\n" + "      \"address\": \"redis://127.0.0.1:6379\",\n"
				+ "      \"subscriptionConnectionMinimumIdleSize\":1,\n"
				+ "      \"subscriptionConnectionPoolSize\":50,\n" + "      \"connectionMinimumIdleSize\":10,\n"
				+ "      \"connectionPoolSize\":64,\n" + "      \"database\":0,\n" + "      \"dnsMonitoring\":false,\n"
				+ "      \"dnsMonitoringInterval\":5000\n" + "   },\n" + "   \"threads\":0,\n"
				+ "   \"nettyThreads\":0,\n" + "   \"codec\":null,\n" + "   \"useLinuxNativeEpoll\":false\n" + "}");

		RedissonClient redisson = Redisson.create(config);

		tokenMap = redisson.getMapCache("TestMap");

	}

	@PreDestroy
	public void shutdownRedis() {
		redisson.shutdown();
	}

	public Token generateTokenAndPushIntoRedis(Integer userId, String tokenType) {
		UUID uuid = UUID.randomUUID();
		String randomUUID = uuid.toString().replaceAll("-", "");
		logger.debug("Random UUID is: " + randomUUID);
		Token token = new Token();
		token.setUserId(userId);
		token.setTokenType(tokenType);
		token.setTokenValue(randomUUID);

		switch (tokenType) {
		case "accesstoken":
			tokenMap.put(randomUUID, token, 24, TimeUnit.HOURS);
			logger.info("Storing access Token as: " + tokenMap.get(randomUUID));
			break;
		case "refreshtoken":
			tokenMap.put(randomUUID, token, 24, TimeUnit.HOURS);
			logger.info("Storing refresh Token as:" + tokenMap.get(randomUUID));
			break;
		case "forgottoken":
			tokenMap.put(randomUUID, token, 24, TimeUnit.HOURS);
			break;
		default:
			logger.info("Invalid Choice");
		}

		// saving same token for userId into REDIS
		// push into REDIS
		// no need to push into MySQL DB anymore

		logger.info(tokenType + randomUUID + " Set successfully for user: " + userId);

		return token;

	}

	public Integer verifyUserToken(String userTokenId) {

		// check if tokenUUID exists

		logger.info(" Redis token is: " + tokenMap.get(userTokenId));
		logger.info("User token is: " + userTokenId);
		Integer userId = null;
		// verify token value, token user, and token type

		if (tokenMap.containsKey(userTokenId)) {
			userId = tokenMap.get(userTokenId).getUserId();
			logger.info("token authentication success with id: "+userId);
			return userId;
		}
		logger.info(" authentication failed");
		return -1;

	}
}
