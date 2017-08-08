/**
 * 
 */
package com.gp.cabbooking.services.impl;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.cabbooking.model.SimpleCabBookingHistoryModel;
import com.gp.cabbooking.model.SimpleCabBookingHistoryModels;
import com.gp.cabbooking.model.SimpleCabDetailsModels;
import com.gp.cabbooking.services.IUserCabBookingHistoryService;
import com.gp.cabbooking.utils.DateTimeUtils;
import com.gp.common.rest.service.SecureRestService;

/**
 * Service for implementing <code>IUserCabBookingHistoryService</code>
 * 
 * @author ganeshp
 *
 */
@Service
public class UserCabBookingHistoryService extends SecureRestService implements IUserCabBookingHistoryService {

	/**
	 * 
	 */
	public UserCabBookingHistoryService() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gp.cabbooking.services.IUserCabBookingHistoryService#
	 * getCabBookingHistory(java.lang.Long, java.time.LocalDate)
	 */
	@Override
	public SimpleCabBookingHistoryModel[] getCabBookingHistory(Long userId, LocalDate onOrAfterThisDate) {
		// SIMPLE_DATE_FORMAT.format(searchModel.getPickupDate())
		// http://localhost:9080/api/cabhistory/locationcabcapacityusermap/search/getCabBookingHistoryAfterBookingDate?fromDate=2016-07-27&userid=1&projection=locationcabuserdetails
		final String endPointURL = "http://localhost:9080/api/cabhistory/locationcabcapacityusermap/search/getCabBookingHistoryAfterBookingDate?projection=locationcabuserdetails&fromDate="
				+ DateTimeUtils.formatISODate(onOrAfterThisDate) + "&userid=" + userId;
		return getCabBookingHistory(endPointURL);
	}
	
	private SimpleCabBookingHistoryModel[] getCabBookingHistory(String endPointURL){
		RestTemplate restTemplate = new RestTemplate();
		String jsonResponse = restTemplate.getForObject(endPointURL, String.class);
		SimpleCabBookingHistoryModel[] cabBookingHistoryArray=null;
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = objectMapper.readTree(jsonResponse.getBytes());
			JsonNode embedded = rootNode.path("_embedded");
			cabBookingHistoryArray = objectMapper.readValue(embedded.toString(), SimpleCabBookingHistoryModels.class).getSimpleCabBookingHistoryModelArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cabBookingHistoryArray;
	}

}
