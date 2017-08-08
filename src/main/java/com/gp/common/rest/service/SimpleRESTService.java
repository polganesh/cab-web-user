package com.gp.common.rest.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * This class provide generic methods which can be used by any REST service over
 * HTTP
 * 
 * @author ganeshp
 *
 */
public class SimpleRESTService {

	/**
	 * Useful method for sending REST data for partially updating record. it
	 * will be helpful when client needs to send only data which needs to be
	 * updated.
	 * 
	 * @param url
	 * @param entity
	 */
	protected void performPatchRequest(String url, HttpEntity<?> entity) {
		this.performRequest(url, entity, HttpMethod.PATCH);
	}

	/**
	 * Useful method for sending REST data over HTTP for various HTTP methods
	 * 
	 * @param url
	 * @param entity
	 * @param method
	 */
	protected void performRequest(String url, HttpEntity<?> entity, HttpMethod method) {
		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);
		restTemplate.setRequestFactory(requestFactory);
		ResponseEntity<Void> responseEntity = restTemplate.exchange(url, method, entity, Void.class);
	}

}
