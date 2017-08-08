package com.gp.cabbooking.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.cabbooking.enums.CabCancellationStatusEnum;
import com.gp.cabbooking.services.ICancelCabBookingService;

@RestController
public class CancelCabBookingController {
	
	@Autowired
	ICancelCabBookingService cancelCabBookingService;
	
	@RequestMapping(value = "/provideCabCancellation.do", method = RequestMethod.POST, produces = {
	"application/json" }, headers = "Accept=application/json")
	public @ResponseBody CabCancellationStatusEnum provideCancellation(@RequestBody Map<String,String> requestDetails) throws Exception {
		String locationcabcapacityid=requestDetails.get("locationcabcapacityid");
		String locationcabcapacityusermap=requestDetails.get("locationcabcapacityusermap");
		//To DO need to add check if controller accepts only request for same logged in user for cancellation
		String userid=requestDetails.get("userid");
		return cancelCabBookingService.processForCancellation(Long.valueOf(locationcabcapacityid), Long.valueOf(userid),Long.valueOf(locationcabcapacityusermap));
	}


}
