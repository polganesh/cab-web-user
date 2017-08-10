/**
 * 
 */
package com.gp.common.web.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

	private static final String USER_SECURITY_MICRO_SERVICE_NAME="USERSECURITY";
	private static final String BASE_PATH="/api/usersecurity";
	private static final String FIND_BY_USERNAME_OPERATION="/userdetails/search/findByUsername";
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
		String serviceURL = super.getServiceDetails(USER_SECURITY_MICRO_SERVICE_NAME) + BASE_PATH
				+ FIND_BY_USERNAME_OPERATION;
		String queryString = "?projection=user&username=" + userName;
		String endPointURL = serviceURL + queryString;
		UserModel model = null;
		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);
		restTemplate.setRequestFactory(requestFactory);
		try {
			ResponseEntity<UserModel> responseEntity = restTemplate.exchange(endPointURL, HttpMethod.GET,
					super.getUserHttpEntity(), UserModel.class);
			model = responseEntity.getBody();
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
			// indicates this is not valid username
			if (status.value() == 404) {
				return null;
			}else {
				throw exception;
			}
		}
		return model;
	}

}
