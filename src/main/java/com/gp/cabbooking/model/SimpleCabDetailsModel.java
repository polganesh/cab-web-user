package com.gp.cabbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gp.cabbooking.enums.CabAvailabilityEnum;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleCabDetailsModel {

	private CabAvailabilityEnum cabAvailability;
	private long locationcabcapacityid;
	private long locationid;
	private String dateforbooking;
	private int hourforbooking;
	private int minutesforbooking;
	private int capacity;
	private int booked;
	private int isdeleted;
	private long adduserid;
	private long edituserid;
	private String adddate;
	private String editdate;
	private long version;

	public SimpleCabDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimpleCabDetailsModel(int capacity, int booked) {
		super();
		this.capacity = capacity;
		this.booked = booked;
		init();
	}

	public SimpleCabDetailsModel(int booked, int edituserid, String editdate, long locationcabcapacityid) {
		super();
		this.booked = booked;
		this.edituserid = edituserid;
		this.editdate = editdate;
		this.locationcabcapacityid = locationcabcapacityid;
	}

	public void initCabAvailability() {
		init();
	}

	private void init() {
		if (capacity == booked) {
			this.cabAvailability = CabAvailabilityEnum.Full;
		} else if (booked == 0) {
			this.cabAvailability = CabAvailabilityEnum.Available;
		} else if (booked > 0) {
			this.cabAvailability = CabAvailabilityEnum.PartiallyAvaiable;
		}
	}
	
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getLocationcabcapacityid() {
		return locationcabcapacityid;
	}

	public void setLocationcabcapacityid(long locationcabcapacityid) {
		this.locationcabcapacityid = locationcabcapacityid;
	}

	public void setCabAvailability(CabAvailabilityEnum cabAvailability) {
		this.cabAvailability = cabAvailability;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setBooked(int booked) {
		this.booked = booked;
	}

	public void setHourforbooking(int hourforbooking) {
		this.hourforbooking = hourforbooking;
	}

	public void setMinutesforbooking(int minutesforbooking) {
		this.minutesforbooking = minutesforbooking;
	}

	public int getHourforbooking() {
		return hourforbooking;
	}

	public int getMinutesforbooking() {
		return minutesforbooking;
	}

	public CabAvailabilityEnum getCabAvailability() {
		return cabAvailability;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getBooked() {
		return booked;
	}

	public String getDateforbooking() {
		return dateforbooking;
	}

	public void setDateforbooking(String dateforbooking) {
		this.dateforbooking = dateforbooking;
	}

	public int getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public long getLocationid() {
		return locationid;
	}

	public void setLocationid(long locationid) {
		this.locationid = locationid;
	}

	public long getAdduserid() {
		return adduserid;
	}

	public void setAdduserid(long adduserid) {
		this.adduserid = adduserid;
	}

	public long getEdituserid() {
		return edituserid;
	}

	public void setEdituserid(long edituserid) {
		this.edituserid = edituserid;
	}

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getEditdate() {
		return editdate;
	}

	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}

}
