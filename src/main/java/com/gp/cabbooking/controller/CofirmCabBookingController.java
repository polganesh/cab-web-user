package com.gp.cabbooking.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.cabbooking.enums.CabConfirmationStatusEnum;
import com.gp.cabbooking.services.IConfirmCabBookingService;

/**
 * Controller for confirming cab booking
 * @author GANESH
 *
 */
@RestController
public class CofirmCabBookingController {
	
	@Autowired
	private IConfirmCabBookingService confirmCabBookingService;
	

	public CofirmCabBookingController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/provideConfirmation.do", method = RequestMethod.POST, produces = {
	"application/json" }, headers = "Accept=application/json")
	public @ResponseBody CabConfirmationStatusEnum provideConfirmation(@RequestBody Map<String,String> requestDetails) throws Exception {
		String locationcabcapacityid=requestDetails.get("locationcabcapacityid");
		String timePart=requestDetails.get("hourMinutes");
		String userId=requestDetails.get("userid");
		String bookingDateTimeString=requestDetails.get("dateForBooking")+" "+timePart;
		return confirmCabBookingService.processForConfirmation(Long.valueOf(locationcabcapacityid), Long.valueOf(userId), getLocalDateTime(bookingDateTimeString));
	}
	
	private LocalDateTime getLocalDateTime(String dateTimeString){
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		final DateTimeFormatter otherFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:m");
		LocalDateTime dateTime=null;
		try{
			dateTime= LocalDateTime.parse(dateTimeString, formatter);
		}catch(DateTimeParseException dtPE){
			dateTime= LocalDateTime.parse(dateTimeString, otherFormatter);
		}
		return dateTime;
	}
}
