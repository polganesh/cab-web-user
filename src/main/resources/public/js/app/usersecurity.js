(function() {
	bookingModule.controller('userAuthorizationController', function($scope) {
		console.log("called security controller");
		$scope.authenticatedUser = {
				userId : "",
				username : "",
				firstname:"",
				lastname:"",
				role : ""			
			};
	});
}());