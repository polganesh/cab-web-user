/**
 * 
 */
package com.gp.common.web.security.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.gp.common.rest.service.SecureRestService;
import com.gp.common.web.security.model.UserModel;

/**
 * @author ganeshp
 *
 */
@Service
public class UserAuthenticationService extends SecureRestService implements IUserAuthenticationService {

	/**
	 * 
	 */
	public UserAuthenticationService() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gp.common.web.security.service.IUserAuthenticationService#
	 * authenticateUser(java.lang.String, java.lang.String)
	 */
	@Override
	public UserModel authenticateUser(String userName, String password) {
		RestTemplate template = new RestTemplate();
		String endPoint = "http://localhost:9090/api/usersecurity/userdetails/search/findByUsername?projection=user&username="
				+ userName;
		UserModel model = null;
		try {
			model = template.getForObject(endPoint, UserModel.class);
			if (model != null) {
				if (userName.equals(model.getUsername()) && password.equals(model.getPassword())
						&& model.getIsdeleted() == 0) {
					model.setPassword("");
					return model;
				} else {
					return null;
				}
			}
		} catch (HttpClientErrorException exception) {
			HttpStatus status = exception.getStatusCode();
			if (!status.is4xxClientError()) {
				throw exception;
			}

		}
		return model;
	}

}
