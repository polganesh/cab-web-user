(function() {
	// alert("Hi ");
	bookingModule
			.controller(
					'bookingController',
					function($scope, $http, $rootScope) {
						alert("calling book");
						var bookCabFormModel = {
							pickup : "",
							pickupDate : "",
							pickupHour : ""
						};

						$scope.bookCabFormModel = bookCabFormModel;

						// var serverDate = "";

						$scope.PartiallyAvaiable = {
							"background-color" : "#FFC300"
						};
						$scope.Available = {
							"background-color" : "#DAF7A6"
						};
						$scope.Full = {
							"background-color" : "#D76F51"
						};
						$scope.message = "";
						$scope.errMessage = "";
						$scope.checkAvailableCabs = function() {
							alert("called");
							console.log("pickup "
									+ $scope.bookCabFormModel.pickup + " date "
									+ $scope.bookCabFormModel.pickupDate + " "
									+ $scope.bookCabFormModel.pickupHour);
							if ($scope.bookCabFormModel.pickup == ""
									|| $scope.bookCabFormModel.pickupDate == ""
									|| $scope.bookCabFormModel.pickupHour == "") {
								$scope.errMessage = "All Search Parameters Required.";
								return;
							}
							var queryString="?pickup="+$scope.bookCabFormModel.pickup+"&pickDate="+getISODate($scope.bookCabFormModel.pickupDate)+"&pickupHour="+$scope.bookCabFormModel.pickupHour;
							console.log("qs "+queryString);
							
							$http({
						        method : "GET",
						   //     url : '/showAvailableCabs.json'+queryString
						        url : '/showAvailableCabs.do'+queryString
						    }).then(function mySucces(response) {
						        //$scope.myWelcome = response.data;
						    	console.log("success data "+response.data+" status "+response.status+" headers "+response.headers+" config "+response.config);
						    }, function myError(response) {
						    	console.log("errror data "+response.data+" status "+response.status+" headers "+response.headers+" config "+response.config);
						        //$scope.myWelcome = response.statusText;
						    });
							
							//var jsonString = JSON.stringify(bookCabFormModel);
							//console.log("jsonString " + jsonString);
							//var queryString="?pickup="+$scope.bookCabFormModel.pickup+"&pickupDate="+$scope.bookCabFormModel.pickupDate+"&pickupHour="+$scope.bookCabFormModel.pickupHour;
							/*
							var queryString="?pickup="+$scope.bookCabFormModel.pickup+"&pickupDate="+getISODate($scope.bookCabFormModel.pickupDate)+"&pickupHour="+$scope.bookCabFormModel.pickupHour;
							console.log("qs "+queryString);
							$http.get('/showAvailableCabs.json'+queryString).then(displayAvailableCabs, processError);;
							function displayAvailableCabs(response){
								console.log("success data "+response.data+" status "+response.status+" headers "+response.headers+" config "+response.config);
							};
							function processError(response){
								console.log("error data "+response.data+" status "+response.status+" headers "+response.headers+" config "+response.config);
							};
							
							
							response.success(function(data,status, headers, config) { 
								console.log("data "+data+" status "+status+" headers "+headers+" config "+config);
								if(data=="" ||data.timePart==""){ 
									$scope.errMessage = "Either you are trying to check for past date or system not having cabs for this slot."; 
									return; 
									}
								console.log("data " + data); 
								$scope.timeComp = data.timePart; 
								$scope.timeAvaliableCabModel = data.timeAvaliableCabModel;
								var jsonString1 = JSON.stringify($scope.timeAvaliableCabModel);
								console.log("op " + jsonString1);
								}
							);
								response.error(function(data, status, headers,config) {
									console.log("data "+data+" status "+status+" headers "+headers+" config "+config);
									$scope.errMessage = "Oops! Something went wrong! Help us improve your experience by notification to admin.";
									}
								);
							
							/*
							$http
									.get(
											'https://www.your-website.com/api/users.json',
											{
												params : {
													page : 1,
													limit : 100,
													sort : 'name',
													direction : 'desc'
												}
											}).then(function(response) {
										// Request completed successfully
									}, function(x) {
										// Request error
									});
							
							 * var response = $http.get('/showAvailableCabs',
							 * jsonString); response.success(function(data,
							 * status, headers, config) { if(data=="" ||
							 * data.timePart==""){ $scope.errMessage = "Either
							 * you are trying to check for past date or system
							 * not having cabs for this slot."; return; }
							 * console.log("data " + data); $scope.timeComp =
							 * data.timePart; $scope.timeAvaliableCabModel =
							 * data.timeAvaliableCabModel; var jsonString1 =
							 * JSON.stringify($scope.timeAvaliableCabModel);
							 * console.log("op " + jsonString1); });
							 * response.error(function(data, status, headers,
							 * config) { $scope.errMessage = "Oops! Something
							 * went wrong! Help us improve your experience by
							 * notification to admin."; });
							 */
						};

						$scope.provideConfirmation = function(
								locationcabcapacity, timepart, datepart) {
							var confirmationDetails = {
								locationcabcapacityid : locationcabcapacity,
								hourMinutes : timepart,
								dateForBooking : datepart,
								userid : $rootScope.currentUser.userid
							};
							var jsonString = JSON
									.stringify(confirmationDetails);
							console.log("jsonString " + jsonString);
							var response = $http.post(
									'/provideConfirmation.do', jsonString);
							var statusForBooking = {
								"TryToBookForPast" : "You are trying to book for past time slot.",
								"FilledAlready" : "Sorry this slot filled.",
								"TryTOBookMoreThanOnce" : "You are trying to book for same slot.",
								"Confirmed" : "Confirmed"
							};
							response
									.success(function(data, status, headers,
											config) {
										console.log("datea " + data);
										if ("TryToBookForPast" == data
												|| "TryTOBookMoreThanOnce" == data) {
											$scope.bookingMessage = null;
											$scope.bookingErrorMessage = statusForBooking[data];
										} else if ("Confirmed" == data) {
											$scope.bookingErrorMessage = null;
											$scope.bookingMessage = "Your cab booking confirmed for Date "
													+ datepart
													+ " Time "
													+ timepart;
											$scope.timeComp = null;
											$scope.timeAvaliableCabModel = null;
											$scope.checkAvailableCabs();
										}
										// $scope.message =
										// statusForBooking[data];
										// $scope.checkAvailableCabs();
										// console.log("data "+data+"
										// op"+$scope.message );
									});

							response
									.error(function(data, status, headers,
											config) {
										$scope.bookingErrorMessage = "Oops! Something went wrong! Help us improve your experience by notification to admin.";
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
					});
}());