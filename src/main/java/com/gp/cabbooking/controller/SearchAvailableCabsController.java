package com.gp.cabbooking.controller;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.cabbooking.model.SearchCabResult;
import com.gp.cabbooking.model.SearchModel;
import com.gp.cabbooking.services.ISearchCabService;

/**
 * Controller providing search results
 * @author GANESH
 *
 */
@RestController
public class SearchAvailableCabsController  {
	
	@Autowired
	private ISearchCabService searchCabService;

	public SearchAvailableCabsController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/showAvailableCabs", method = RequestMethod.GET)
	public SearchCabResult checkAvailableCabs(@RequestBody SearchModel searchModel) {
		// to do need to convert java.util dates to java.time.localdate
		LocalDate currentDate = LocalDate.now();
//		LocalDate dateForSearch = searchModel.getPickupDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		SearchCabResult cabresult = null;
//		if (dateForSearch.isBefore(currentDate)) {
//			return cabresult;
//		} else {
//			cabresult = searchCabService.searchCabDetails(searchModel);
//
//		}
		return null;
	}

//	@RequestMapping(value = "/showAvailableCabs.do", method = RequestMethod.POST, produces = {
//			"application/json" }, headers = "Accept=application/json")
//	public @ResponseBody SearchCabResult checkAvailableCabs(@RequestBody SearchModel searchModel) {
//		//to do need to convert java.util dates to java.time.localdate
//		LocalDate currentDate=LocalDate.now();
//		LocalDate dateForSearch = searchModel.getPickupDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		SearchCabResult cabresult=null;
//		if(dateForSearch.isBefore(currentDate)){
//			return cabresult;
//		}else{
//			cabresult=searchCabService.searchCabDetails(searchModel);
//
//		}
//		return cabresult;
//	}

}
