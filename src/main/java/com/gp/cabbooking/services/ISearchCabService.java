package com.gp.cabbooking.services;

import java.util.Map;

import com.gp.cabbooking.model.SimpleCabDetailsModel;
import com.gp.cabbooking.model.SearchCabResult;
import com.gp.cabbooking.model.SearchModel;

/**
 * Interface define all methods require for cab
 * @author ganeshp
 *
 */
public interface ISearchCabService {
	
	/**
	 * provide List of available cabs bases on parameter passed as argument
	 * 
	 * @param searchModel
	 * @return
	 */
	SearchCabResult searchCabDetails(SearchModel searchModel);

}
