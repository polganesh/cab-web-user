package com.gp.cabbooking.model.patches;

import com.gp.common.models.patches.AuditablePatch;
import com.gp.common.models.patches.VersionPatch;

public class CabBookingPatch extends VersionPatch {
	
	private int booked;
	
	public CabBookingPatch(Long edituserid, String editdate, Long version,int booked) {
		super(edituserid, editdate, version);
		this.booked=booked;
	}

//	public CabBookingPatch(int booked, Long edituserid, String editdate,Long version) {
//		super(edituserid,editdate);
//		this.booked = booked;
//		this.version=version;
//	}



	public int getBooked() {
		return booked;
	}

	

	public void setBooked(int booked) {
		this.booked = booked;
	}

}
