/**
 * 
 */
package com.gp.cabbooking.services;

import java.time.LocalDateTime;

import com.gp.cabbooking.enums.CabConfirmationStatusEnum;

/**
 * @author GANESH
 *
 */
public interface IConfirmCabBookingService {
	
	//CabConfirmationStatusEnum processForConfirmation(Long locationCabCapacityId,String hourMinute,Date dateForBooking,Long userId);
	
	CabConfirmationStatusEnum processForConfirmation(Long locationCabCapacityId,Long userId,LocalDateTime timeForBooking)throws Exception;

}
