package com.gp.cabbooking.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.cabbooking.model.SimpleCabBookingHistoryModel;
import com.gp.cabbooking.services.IUserCabBookingHistoryService;
import com.gp.cabbooking.utils.DateTimeUtils;

/**
 * This controller accepts requests from logged in users for their cab history
 * 
 * @author ganeshp
 *
 */
@RestController
public class UserCabHistoryController {
	
	@Autowired
	private IUserCabBookingHistoryService cabBookingHistoryService;

//	@RequestMapping(value = "/getCabBookingHistory.do", method = RequestMethod.POST, produces = {
//			"application/json" }, headers = "Accept=application/json")
//	public @ResponseBody SimpleCabBookingHistoryModel[] getCabBookingHistory(@RequestBody Map<String,String> requestDetails)
//			throws Exception {
//		//String pickupDate=requestDetails.get("pickupDate");
//		LocalDate pickupDate=DateTimeUtils.parseISODate(requestDetails.get("pickupDate"));
//		return cabBookingHistoryService.getCabBookingHistory(1L, pickupDate);
//	}
	
	@RequestMapping(value = "/getCabBookingHistory.do", method = RequestMethod.POST, produces = {
			"application/json" }, headers = "Accept=application/json")
	public @ResponseBody SimpleCabBookingHistoryModel[] getCabBookingHistory(
			@RequestBody Map<String, String> requestDetails) throws Exception {
		// String pickupDate=requestDetails.get("pickupDate");
		LocalDate pickupDate = DateTimeUtils.parseISODate(requestDetails.get("pickupDate"));
		String userid=requestDetails.get("userid");
		SimpleCabBookingHistoryModel[] history=cabBookingHistoryService.getCabBookingHistory(Long.valueOf(userid), pickupDate);
		return history;
	}
	
	

}
