package com.gp.cabbooking.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchCabResult {

	private List<String> timePart;

	private Map<String, SimpleCabDetailsModel> timeAvaliableCabModel = null;

	public SearchCabResult(List<String> timePart, Map<String, SimpleCabDetailsModel> timeAvaliableCabModel) {
		super();
		this.timePart = timePart;
		this.timeAvaliableCabModel = timeAvaliableCabModel;
	}

	public Map<String, SimpleCabDetailsModel> getTimeAvaliableCabModel() {
		return timeAvaliableCabModel;
	}

	public List<String> getTimePart() {
		return timePart;
	}

	public void setTimePart(List<String> timePart) {
		this.timePart = timePart;
	}

	public void setTimeAvaliableCabModel(Map<String, SimpleCabDetailsModel> timeAvaliableCabModel) {
		this.timeAvaliableCabModel = timeAvaliableCabModel;
	}

}
