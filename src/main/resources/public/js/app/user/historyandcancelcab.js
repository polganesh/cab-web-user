(function() {
	bookingModule.controller('historyAndCancelController', function($scope, $http,$rootScope) {
		
		var historyCabFormModel = {pickupDate : ""};
		
		$scope.historyCabFormModel = historyCabFormModel;
		
		$scope.checkHistory = function() {
			console.log(" "+$scope.historyCabFormModel.pickupDate);
			var historyCabModel = {pickupDate : ""};
			historyCabModel.pickupDate=getISODate($scope.historyCabFormModel.pickupDate);
			var reqParameters={pickupDate:historyCabModel.pickupDate,userid:$rootScope.currentUser.userid};
			var jsonString = JSON.stringify(reqParameters);
			console.log("jsonString "+jsonString);
			var response = $http.post('/getCabBookingHistory.do', jsonString);
			response.success(function(data, status, headers, config) {
				console.log("date "+data);
				//var jsonString = JSON.stringify(data);
				//console.log("length "+data.length);
				if(data.length<=0){
					$scope.errMessage="We are unable to  find any records.";
				}else{
					$scope.userCabHistory=null;
					$scope.userCabHistory=data;
					
				}
			});
			
			 response.error(function(data, status, headers, config) {
				 $scope.errMessage="Oh no, We are currently unable to provide you history. Please try after some time.";
			 });
			
		};
		
		$scope.cancelCab=function(locationcabcapacityid,locationcabcapacityusermap){
			console.log(" "+locationcabcapacityid+" "+locationcabcapacityusermap);
			var locationCabCapacityMap={"locationcabcapacityid":locationcabcapacityid,"locationcabcapacityusermap":locationcabcapacityusermap,"userid":$rootScope.currentUser.userid};
			var jsonString = JSON.stringify(locationCabCapacityMap);
			var statusForCancelling = {
					"TryToCancelForPast" : "You are trying to cancel cab for past date time.",
					"TryToCancelMoreThanOnce":"You already cancelled this slot.",
					"CancelConfirmed" :"Cancelled for this slot",
				};
			var response = $http.post('/provideCabCancellation.do', jsonString);
			response.success(function(data, status, headers, config) {
				console.log(" data "+data);
				$scope.message = statusForCancelling[data];
				$scope.checkHistory();
			});
			
			response.error(function(data, status, headers, config) {
				 $scope.message="Uh Oh! We are currently unable to cancel your booking. Please try after some time.";
			 });
			
		};
		
		function  getISODate(dateObject){
			var year=dateObject.getFullYear();
			var month=dateObject.getMonth()+1;
			var day=dateObject.getDate();
			month=appendZeroIfNeeded(month);
			day=appendZeroIfNeeded(day);
			console.log(" "+year+" month "+month+" day "+day);
			return year+"-"+month+"-"+day;
		}
		
		function appendZeroIfNeeded(value){
			if(value<=9){
				return '0'+value;
			}else{
				return value;
			}
		}
		/*
		function GetFormattedDate(dateTime) {
		   // var todayTime = new Date();
		    var month = format(dateTime .getMonth() + 1);
		    var day = format(dateTime .getDate());
		    var year = format(dateTime .getFullYear());
		    return month + "-" + day + "-" + year;
		}*/
	
	});
}());