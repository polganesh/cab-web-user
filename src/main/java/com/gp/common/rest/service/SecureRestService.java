package com.gp.common.rest.service;

import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * Simple class to communicate with Secure REST API
 * 
 * @author ganeshp
 *
 */
public class SecureRestService extends SimpleRESTService {

	public HttpEntity<String> getUserHttpEntity() {
		byte[] userCredentialsBase64Bytes = Base64.getEncoder().encode("ganesh:myauthenticationTest".getBytes());
		// add this credentials to header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + new String(userCredentialsBase64Bytes));
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		return httpEntity;
	}

}
