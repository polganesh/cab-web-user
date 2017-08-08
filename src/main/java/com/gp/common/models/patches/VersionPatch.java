package com.gp.common.models.patches;


public class VersionPatch extends AuditablePatch {
	private Long version;

	public VersionPatch(Long edituserid, String editdate,Long version) {
		super(edituserid, editdate);
		this.version=version;
		// TODO Auto-generated constructor stub
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	
}
