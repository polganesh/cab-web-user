package com.gp.cabbooking.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.cabbooking.model.AppConfig;

@RestController
public class PageLoadController {

	public PageLoadController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/getSystemDate.do", method = RequestMethod.GET, produces = {
	"application/json" }, headers = "Accept=application/json")
	public AppConfig getSystemDate(){
		Date date=new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		return new AppConfig(dateFormat.format(date));
	}
	
	

}
