package com.gp.cabbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleCabBookingHistoryModel {

	private String dateOfBooking;
	
	

	private String slot;

	private int iscancelled;

	private String location;

	private Long locationcabcapacityusermap;

	public int getIscancelled() {
		return iscancelled;
	}

	public void setIscancelled(int iscancelled) {
		this.iscancelled = iscancelled;
	}

	private Long locationcabcapacityid;

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getDateOfBooking() {
		return dateOfBooking;
	}

	public void setDateOfBooking(String dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}

	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getLocationcabcapacityusermap() {
		return locationcabcapacityusermap;
	}

	public void setLocationcabcapacityusermap(Long locationcabcapacityusermap) {
		this.locationcabcapacityusermap = locationcabcapacityusermap;
	}

	public Long getLocationcabcapacityid() {
		return locationcabcapacityid;
	}

	public void setLocationcabcapacityid(Long locationcabcapacityid) {
		this.locationcabcapacityid = locationcabcapacityid;
	}

}
