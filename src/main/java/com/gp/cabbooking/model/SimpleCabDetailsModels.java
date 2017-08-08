package com.gp.cabbooking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleCabDetailsModels {
	
	 @JsonProperty("locationcabtimedetail")
	private SimpleCabDetailsModel[] simpleCabDetails;

	public SimpleCabDetailsModel[] getSimpleCabDetails() {
		return simpleCabDetails;
	}

	public void setSimpleCabDetails(SimpleCabDetailsModel[] simpleCabDetails) {
		this.simpleCabDetails = simpleCabDetails;
	}

	

}
