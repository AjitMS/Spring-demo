package com.bridgeit.controllers;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/example")
public class ExampleController {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	@GetMapping("/example")
	public void setToken(String userId, String tokenId) {
		System.out.println("redis Template: " + redisTemplate);
		listOps = redisTemplate.opsForList();
		listOps.leftPush(userId, tokenId);
		System.out.println("Set success");
	}

	@GetMapping("/gettoken")
	public String getToken(String userId) {
		listOps = redisTemplate.opsForList();
		System.out.println("Get success");
		return listOps.leftPop(userId);
	}

}
