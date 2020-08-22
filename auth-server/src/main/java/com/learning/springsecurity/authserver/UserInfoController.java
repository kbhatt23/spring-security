package com.learning.springsecurity.authserver;

import java.security.Principal;
import java.util.HashMap;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserInfoController {
	//client will call this api to get user-info and create princial object using OAuthAuthenticationProvider class
	  @GetMapping(value="/userinfo")
	    public HashMap<String, Object> user( Principal principal) {
	       
		  System.err.println("UserInfoController.user()"); 
		  
	        	HashMap<String, Object>  userInfoMap= new HashMap<>();
	        	userInfoMap.put("username", principal.getName());
	        	userInfoMap.put("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
	        	
	          return userInfoMap;
	     
	    }
	
	/*
	  @GetMapping("/userinfo")
	public MyAppUser userInfo(Principal principal) {
		
	
		
		
		System.out.println("UserInfoController.userInfo()");
		return new MyAppUser(principal.getName());
		
	}
	
	*/

}
