package com.gp.cabbooking.services;

import com.gp.cabbooking.enums.CabCancellationStatusEnum;

public interface ICancelCabBookingService {

	CabCancellationStatusEnum processForCancellation(Long locationCabCapacityId, Long userId,Long locationcabcapacityusermap);
}
