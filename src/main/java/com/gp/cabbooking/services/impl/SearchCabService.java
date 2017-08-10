/**
 * 
 */
package com.gp.cabbooking.services.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.cabbooking.comparator.TimeStringComparator;
import com.gp.cabbooking.model.SearchCabResult;
import com.gp.cabbooking.model.SearchModel;
import com.gp.cabbooking.model.SimpleCabDetailsModel;
import com.gp.cabbooking.model.SimpleCabDetailsModels;
import com.gp.cabbooking.services.ISearchCabService;
import com.gp.common.rest.service.SecureRestService;
import com.gp.common.rest.service.SimpleRESTService;

/**
 * Class provide implementations for methods defined in <code>ICabService</code>
 * 
 * @author ganeshp
 *
 */
@Service
public class SearchCabService extends SimpleRESTService implements ISearchCabService {

	private static final String SEARCH_CAB_END_POINT = "http://localhost:9000/api/searchcab/locationcabtimedetail/search";

	private static final String GET_CAB_FOR_LOCATION_AFTER_TIME_SLOT = "/getCabForLocationAfterTimeSlot";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 */
	public SearchCabService() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gp.cabbooking.services.ICabService#getAvailableCabs(com.gp.cabbooking
	 * .model.SearchModel)
	 */
	@Override
	public SearchCabResult searchCabDetails(SearchModel searchModel) {
		String queryString = "?fromDate=" + SIMPLE_DATE_FORMAT.format(searchModel.getPickDate()) 
							+ "&locationId="+ searchModel.getPickup()
							+"&isdeleted="+0
							+"&hourforbooking="+searchModel.getPickupHour()+"&projection=searchCabProjection";
		//String endPoint = SEARCH_CAB_END_POINT + GET_CAB_FOR_LOCATION_AFTER_TIME_SLOT + queryString;
		
		;
		String endPoint = super.getServiceDetails("SEARCHCAB")+"/api/searchcab/locationcabtimedetail/search" + GET_CAB_FOR_LOCATION_AFTER_TIME_SLOT + "";
		endPoint=endPoint+queryString;
		SimpleCabDetailsModel[] simpleCabDetails=getSimpleCabDetailModels(endPoint);
		return prepareAvailableCabResult(simpleCabDetails);
	}
	
	public SearchCabResult prepareAvailableCabResult(SimpleCabDetailsModel[] simpleCabDetails){
		Map<String,SimpleCabDetailsModel> timeCabDetailsMap=new HashMap<String,SimpleCabDetailsModel>();
		String timeKey="";
		for(SimpleCabDetailsModel simpleCabDetailsModel:simpleCabDetails){
			simpleCabDetailsModel.initCabAvailability();
			timeKey=simpleCabDetailsModel.getHourforbooking()+":"+simpleCabDetailsModel.getMinutesforbooking();
			timeCabDetailsMap.put(timeKey, simpleCabDetailsModel);
		}
		List<String> timeCompList = new ArrayList<String>(timeCabDetailsMap.keySet());
		Collections.sort(timeCompList, new TimeStringComparator());
		return new SearchCabResult(timeCompList, timeCabDetailsMap);
	}
	
	
	private SimpleCabDetailsModel[] getSimpleCabDetailModels(String endPointURL) {
		RestTemplate restTemplate = new RestTemplate();
		String jsonResponse = restTemplate.getForObject(endPointURL, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleCabDetailsModel[] cabDetails = null;
		try {
			JsonNode rootNode = objectMapper.readTree(jsonResponse.getBytes());
			JsonNode embedded = rootNode.path("_embedded");
			cabDetails = objectMapper.readValue(embedded.toString(), SimpleCabDetailsModels.class)
					.getSimpleCabDetails();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cabDetails;
	}


}
