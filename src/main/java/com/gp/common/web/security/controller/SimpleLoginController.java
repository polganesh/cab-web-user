package com.gp.common.web.security.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.common.web.security.model.UserModel;
import com.gp.common.web.security.service.IUserAuthenticationService;

@RestController
public class SimpleLoginController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private IUserAuthenticationService authenticationService;

	public SimpleLoginController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST, produces = {
	"application/json" }, headers = "Accept=application/json")
	public @ResponseBody UserModel  authenticateUser(@RequestBody Map<String, String> userDetails) throws Exception {
		String userName=userDetails.get("username");
		String password=userDetails.get("password");
		UserModel userModel=authenticationService.authenticateUser(userName, password);
		if(userModel!=null){
			//indicates session should not time out
			session.setMaxInactiveInterval(-1);
			session.setAttribute("user", userModel);
		}
		return userModel;
	}
	

}
