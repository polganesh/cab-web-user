package com.gp.cabbooking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleCabBookingHistoryModels {

	@JsonProperty("locationcabcapacityusermap")
	private SimpleCabBookingHistoryModel[] simpleCabBookingHistoryModelArray;
	
	

	public SimpleCabBookingHistoryModels() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SimpleCabBookingHistoryModel[] getSimpleCabBookingHistoryModelArray() {
		return simpleCabBookingHistoryModelArray;
	}



	public void setSimpleCabBookingHistoryModelArray(SimpleCabBookingHistoryModel[] simpleCabBookingHistoryModelArray) {
		this.simpleCabBookingHistoryModelArray = simpleCabBookingHistoryModelArray;
	}

	
	

}
