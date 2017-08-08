package com.gp.common.web.security.service;

import com.gp.common.web.security.model.UserModel;

public interface IUserAuthenticationService {
	
	UserModel authenticateUser(String userName,String password);

}
