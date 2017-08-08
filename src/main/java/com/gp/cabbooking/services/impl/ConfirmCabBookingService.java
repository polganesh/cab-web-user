/**
 * 
 */
package com.gp.cabbooking.services.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.gp.cabbooking.enums.CabConfirmationStatusEnum;
import com.gp.cabbooking.model.SimpleCabDetailsModel;
import com.gp.cabbooking.model.patches.CabBookingPatch;
import com.gp.cabbooking.services.IConfirmCabBookingService;
import com.gp.cabbooking.utils.DateTimeUtils;
import com.gp.common.rest.service.SecureRestService;

/**
 * Service for managing user confirmation for cab
 * 
 * @author ganesh pol
 *
 */
@Service
public class ConfirmCabBookingService extends SecureRestService implements IConfirmCabBookingService {

	private static final String CAB_END_POINT = "http://localhost:9050/api/confirmcab/locationcabtimedetail/search/";
	private static final String CHECK_BOOK_CAPACITY_FUNCTION = "getByLocationcabcapacityid";
	private static final String CAB_USER_MAP_END_POINT = "http://localhost:9050/api/confirmcab/locationcabcapacityusermap/search/";
	private static final String GET_LOCATION_CAB_USER_FUNCTION = "getLocationCabUserMap";

	// http://localhost:9050/api/confirmcab/locationcabcapacityusermap/search/getLocationCabUserMap?iscancelled=0&locationcabcapacityid=1&userid=1&projection=cabslotuser
	// http://localhost:9050/api/confirmcab/locationcabtimedetail/search/getByLocationcabcapacityid?locationcabcapacityid=3&isdeleted=0&projection=bookedAndCapacity

	/**
	 * This method perform following business validations before confirming cab
	 * for user.<br>
	 * Returns
	 * <ul>
	 * <li>TryToBookForPast if user trying to book for past date time</li>
	 * <li>TryTOBookMoreThanOnce if user trying to book for same slot multiple
	 * time</li>
	 * <li>FilledAlready if user trying to book for cab which is filled</li>
	 * <li>Confirmed if cab is confirmed for user</li>
	 * </ul>
	 * 
	 * 
	 * @param locationCabCapacityId:-
	 *            location cab capacity id for which to book cab
	 * @param userId
	 *            :- user for which cab needs to be booked
	 */
	@Override
	public CabConfirmationStatusEnum processForConfirmation(Long locationCabCapacityId, Long userId,
			LocalDateTime timeForBooking) throws IOException {
		CabConfirmationStatusEnum status = null;
		LocalDateTime dateTimeCurrent = LocalDateTime.now();
		if (timeForBooking.isBefore(dateTimeCurrent)) {
			status = CabConfirmationStatusEnum.TryToBookForPast;
		} else
			status = isBookedForSameSlotAndNotCancelled(locationCabCapacityId, userId)
					? CabConfirmationStatusEnum.TryTOBookMoreThanOnce
					: confirmIfSlotNotFull(locationCabCapacityId, userId);
		return status;
	}

	private CabConfirmationStatusEnum confirmIfSlotNotFull(Long locationCabCapacityId, Long userId) {
		CabConfirmationStatusEnum status = null;
		RestTemplate template = new RestTemplate();
		String endPoint = "http://localhost:9050/api/confirmcab/locationcabtimedetail/search/findByLocationcabcapacityidAndIsdeleted?isdeleted=0&projection=bookedAndCapacity&locationcabcapacityid="
				+ locationCabCapacityId;
		SimpleCabDetailsModel simpleCabDetailModel = template.getForObject(endPoint, SimpleCabDetailsModel.class);
		if (simpleCabDetailModel != null) {
			if (simpleCabDetailModel.getBooked() >= simpleCabDetailModel.getCapacity()) {
				status = CabConfirmationStatusEnum.FilledAlready;
			} else {
				applyPatchForLocationCabCapacity(simpleCabDetailModel, locationCabCapacityId, userId);
				saveLocationCabCapacityUserMap(locationCabCapacityId, userId);
				status = CabConfirmationStatusEnum.Confirmed;
			}
		}
		return status;
	}

	private void applyPatchForLocationCabCapacity(SimpleCabDetailsModel simpleCabDetailModel,
			Long locationCabCapacityId, Long userid) {
		// http://stackoverflow.com/questions/10648515/using-version-in-spring-data-project
		// http://springinpractice.com/2013/09/14/optimistic-locking-with-spring-data-rest
		// CabBookingPatch patch = new
		// CabBookingPatch((simpleCabDetailModel.getBooked() + 1), userid,
		// DateTimeUtils.formatCurrentDateTimeTOISO(),(simpleCabDetailModel.getVersion()+1));
		CabBookingPatch patch = new CabBookingPatch(userid, DateTimeUtils.formatCurrentDateTimeTOISO(),
				(simpleCabDetailModel.getVersion() + 1), (simpleCabDetailModel.getBooked() + 1));
		HttpEntity<CabBookingPatch> entity = new HttpEntity<CabBookingPatch>(patch);
		String url = "http://localhost:9050/api/confirmcab/locationcabtimedetail/" + locationCabCapacityId;
		super.performPatchRequest(url, entity);
	}

	private void saveLocationCabCapacityUserMap(Long locationcabcapacityid, Long userid) {
		final String endpoint = "http://localhost:9050/api/confirmcab/locationcabcapacityusermap";
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> locationcabcapacityUserMap = new HashMap<String, Object>();
		locationcabcapacityUserMap.put("locationcabcapacityid", locationcabcapacityid.toString());
		locationcabcapacityUserMap.put("userid", userid.toString());
		locationcabcapacityUserMap.put("adduserid", userid.toString());
		locationcabcapacityUserMap.put("edituserid", userid.toString());
		locationcabcapacityUserMap.put("adddate", DateTimeUtils.formatCurrentDateTimeTOISO());
		locationcabcapacityUserMap.put("editdate", DateTimeUtils.formatCurrentDateTimeTOISO());
		locationcabcapacityUserMap.put("iscancelled", 0);
		locationcabcapacityUserMap = restTemplate.postForObject(endpoint, locationcabcapacityUserMap, Map.class);
	}

	private boolean isBookedForSameSlotAndNotCancelled(Long locationCabCapacityId, Long userId) {
		boolean isBookedForSameSlotAndNotCancelled = false;
		RestTemplate template = new RestTemplate();
		String endPoint = "http://localhost:9050/api/confirmcab/locationcabcapacityusermap/search/findByLocationcabcapacityidAndUserid?locationcabcapacityid="
				+ locationCabCapacityId + "&userid=" + userId + "&projection=cabslotuser";
		try {
			Map<String, Integer> jsonMap = template.getForObject(endPoint, Map.class);
			if (jsonMap != null && !jsonMap.keySet().isEmpty()) {
				Integer isCancelled = jsonMap.get("iscancelled");
				if (isCancelled == 0) {
					isBookedForSameSlotAndNotCancelled = true;
				}
			}
		} catch (HttpClientErrorException exception) {
			HttpStatus status = exception.getStatusCode();
			if (!status.is4xxClientError()) {
				throw exception;
			}
		}
		return isBookedForSameSlotAndNotCancelled;
	}

	// correct working
	// private CabConfirmationStatusEnum confirmIfSlotNotFull(Long
	// locationCabCapacityId,Long userId) {
	// CabConfirmationStatusEnum status=null;
	// RestTemplate template = new RestTemplate();
	// String
	// endPoint="http://localhost:9050/api/confirmcab/locationcabtimedetail/search/findByLocationcabcapacityidAndIsdeleted?isdeleted=0&locationcabcapacityid="+locationCabCapacityId;
	// SimpleCabDetailsModel
	// simpleCabDetailModel=template.getForObject(endPoint,
	// SimpleCabDetailsModel.class);
	// if(simpleCabDetailModel!=null){
	// if(simpleCabDetailModel.getBooked()>=simpleCabDetailModel.getCapacity()){
	// status = CabConfirmationStatusEnum.FilledAlready;
	// }else{
	// String
	// url="http://localhost:9050/api/confirmcab/locationcabtimedetail/{id}";
	// Map<String, Long> params = new HashMap<String, Long>();
	// params.put("id", locationCabCapacityId);
	// simpleCabDetailModel.setBooked((simpleCabDetailModel.getBooked()+1));
	// simpleCabDetailModel.setEdituserid(userId);
	// simpleCabDetailModel.setEditdate(DateTimeUtils.formatCurrentDateTimeTOISO());
	// template.put(url, simpleCabDetailModel, params);
	// status=CabConfirmationStatusEnum.Confirmed;
	// }
	//
	// }
	// return status;
	//
	// }

}
