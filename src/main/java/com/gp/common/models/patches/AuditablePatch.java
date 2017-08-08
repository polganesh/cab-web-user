package com.gp.common.models.patches;

public class AuditablePatch {

	private Long edituserid;

	private String editdate;
	
	

	public AuditablePatch() {
		super();
	}

	public AuditablePatch(Long edituserid, String editdate) {
		super();
		this.edituserid = edituserid;
		this.editdate = editdate;
	}

	public Long getEdituserid() {
		return edituserid;
	}

	public void setEdituserid(Long edituserid) {
		this.edituserid = edituserid;
	}

	public String getEditdate() {
		return editdate;
	}

	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}

	
	
}
