/**
 * 
 */
package com.gp.cabbooking.model.patches;

import com.gp.common.models.patches.AuditablePatch;

/**
 * @author ganeshp
 *
 */
public class CancelCablocationUserMapPatch extends AuditablePatch {

	int iscancelled;

	/**
	 * @param edituserid
	 * @param editdate
	 */
	public CancelCablocationUserMapPatch(Long edituserid, String editdate,int iscancelled) {
		super(edituserid, editdate);
		this.iscancelled=iscancelled;
	}

	public int getIscancelled() {
		return iscancelled;
	}

	public void setIscancelled(int iscancelled) {
		this.iscancelled = iscancelled;
	}
}

