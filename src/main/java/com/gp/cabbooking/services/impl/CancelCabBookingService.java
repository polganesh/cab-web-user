/**
 * 
 */
package com.gp.cabbooking.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.gp.cabbooking.enums.CabCancellationStatusEnum;
import com.gp.cabbooking.model.SimpleCabDetailsModel;
import com.gp.cabbooking.model.patches.CabBookingPatch;
import com.gp.cabbooking.model.patches.CancelCablocationUserMapPatch;
import com.gp.cabbooking.services.ICancelCabBookingService;
import com.gp.cabbooking.utils.DateTimeUtils;
import com.gp.common.rest.service.SecureRestService;

/**
 * @author ganeshp
 *
 */
@Service
public class CancelCabBookingService extends SecureRestService implements ICancelCabBookingService {

	private static final String CAB_USER_MAP_END_POINT = "http://localhost:9050/api/confirmcab/locationcabcapacityusermap/search/";
	private static final String GET_LOCATION_CAB_USER_FUNCTION = "getLocationCabUserMap";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gp.cabbooking.services.ICancelCabBookingService#
	 * processForCancellation(java.lang.Long, java.lang.Long)
	 */
	@Override
	public CabCancellationStatusEnum processForCancellation(Long locationCabCapacityId, Long userId,Long locationcabcapacityusermap) {
		CabCancellationStatusEnum status = null;
		SimpleCabDetailsModel cabDetails = getSimpleCabDetails(locationCabCapacityId);
		if (cabDetails != null) {
			String dateTimeSlotString = cabDetails.getDateforbooking() + " " + cabDetails.getHourforbooking() + ":"
					+ cabDetails.getMinutesforbooking();
			LocalDateTime cabDateTime = getLocalDateTime(dateTimeSlotString);
			LocalDateTime currentDateTime = LocalDateTime.now();
			if (cabDateTime.isBefore(currentDateTime)) {
				status = CabCancellationStatusEnum.TryToCancelForPast;
			} else {
				Map<String, Integer> locationcabcapacityuserMap = getLocationcabCapcityuserMap(locationcabcapacityusermap);
				if(locationcabcapacityuserMap != null && !locationcabcapacityuserMap.keySet().isEmpty()){
					Integer isCancel=locationcabcapacityuserMap.get("iscancelled");
					if(isCancel==1){
						status = CabCancellationStatusEnum.TryToCancelMoreThanOnce;
					}else{
						processCancellation(userId,locationcabcapacityusermap,cabDetails,locationCabCapacityId);
						status = CabCancellationStatusEnum.CancelConfirmed;
					}
				}
			}
		}
		return status;
	}
	
	private void processCancellation(Long userId,Long locationcabcapacityusermap,SimpleCabDetailsModel simpleCabDetailModel,Long locationCabCapacityId){
		updateLocationCabCapacity(simpleCabDetailModel,userId,locationCabCapacityId);
		updateCancelInLocationcabcapacityuserMap(userId,locationcabcapacityusermap);
	}
	
	private void updateLocationCabCapacity(SimpleCabDetailsModel simpleCabDetailModel,Long userId,Long locationCabCapacityId){
		CabBookingPatch patch = new CabBookingPatch(userId, DateTimeUtils.formatCurrentDateTimeTOISO(),
				(simpleCabDetailModel.getVersion() + 1), (simpleCabDetailModel.getBooked() - 1));
		HttpEntity<CabBookingPatch> entity = new HttpEntity<CabBookingPatch>(patch);
		String url = "http://localhost:9050/api/confirmcab/locationcabtimedetail/" + locationCabCapacityId;
		super.performPatchRequest(url, entity);
	}
	
	private void updateCancelInLocationcabcapacityuserMap(Long userId,Long locationcabcapacityusermap){
		CancelCablocationUserMapPatch patch=new CancelCablocationUserMapPatch(userId, DateTimeUtils.formatCurrentDateTimeTOISO(), 1);
		HttpEntity<CancelCablocationUserMapPatch> entity = new HttpEntity<CancelCablocationUserMapPatch>(patch);
		String endPoint="http://localhost:9050/api/confirmcab/locationcabcapacityusermap/"+locationcabcapacityusermap;
		super.performPatchRequest(endPoint, entity);
	}

	private LocalDateTime getLocalDateTime(String dateTimeString) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		final DateTimeFormatter otherFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:m");
		LocalDateTime dateTime = null;
		try {
			dateTime = LocalDateTime.parse(dateTimeString, formatter);
		} catch (DateTimeParseException dtPE) {
			dateTime = LocalDateTime.parse(dateTimeString, otherFormatter);
		}
		return dateTime;
	}

	private Map<String, Integer> getLocationcabCapcityuserMap(Long locationcabcapacityusermap) {
		RestTemplate template = new RestTemplate();
//		String endPoint = CAB_USER_MAP_END_POINT + GET_LOCATION_CAB_USER_FUNCTION
//				+ "?iscancelled=0&projection=cabslotuser&locationcabcapacityid=" + locationCabCapacityId + "&userid="
//				+ userId;
		String endPoint="http://localhost:9050/api/confirmcab/locationcabcapacityusermap/"+locationcabcapacityusermap+"?projection=cabslotuser";
		Map<String, Integer> jsonMap = null;
		try {
			jsonMap = template.getForObject(endPoint, Map.class);
		} catch (HttpClientErrorException exception) {
			HttpStatus status = exception.getStatusCode();
			if (!status.is4xxClientError()) {
				throw exception;
			}

		}
		return jsonMap;
	}


	private SimpleCabDetailsModel getSimpleCabDetails(Long locationCabCapacityId) {
		RestTemplate template = new RestTemplate();
		String endPoint = "http://localhost:9050/api/confirmcab/locationcabtimedetail/search/findByLocationcabcapacityidAndIsdeleted?isdeleted=0&projection=bookedAndCapacity&locationcabcapacityid="
				+ locationCabCapacityId;
		return template.getForObject(endPoint, SimpleCabDetailsModel.class);
	}

}
