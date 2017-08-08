package com.gp.cabbooking.services;

import java.time.LocalDate;

import com.gp.cabbooking.model.SimpleCabBookingHistoryModel;

/**
 * Interface define method for getting Cab booking history
 * @author ganeshp
 *
 */
public interface IUserCabBookingHistoryService {

	SimpleCabBookingHistoryModel[] getCabBookingHistory(Long userId,LocalDate onOrAfterThisDate);
}
