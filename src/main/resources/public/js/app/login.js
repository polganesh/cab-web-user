(function() {
	bookingModule.controller('loginController', function($scope, $http,$rootScope) {
		console.log(" in login controller");
		var user={
				username:"",
				password:""
			};
		$scope.user = user;
		
		$scope.authenticate = function() {
			if($scope.user.userName==""||$scope.user.password==""){
				$scope.errMessage="You must provide both userName and Password";
			}
			//console.log(" in login controller dsdsd");
			var jsonString=JSON.stringify($scope.user);
			//console.log(" cadfjsonString "+jsonString);
			var response = $http.post('/login.do', jsonString);
			response.success(function(data, status, headers, config) {
				if(data==""){
					$scope.errMessage="Invalid Credentials";
				}else{
					console.log("data "+JSON.stringify(data));
					//$scope.global=$rootScope;
					var roleid=data.roleid;
					$rootScope.currentUser=data;
					
					//$rootScope.roleid=data.roleid;
					//console.log("data "+JSON.stringify($rootScope.currentUser));
					//html/home.html#/login
					window.location.href='/html/home.html#/bookcab'
				}
				//console.log("data "+data);
			});
			
			response.error(function(data, status, headers, config) {
				 $scope.errMessage="Oh no, We are currently unable to process your request. Please try after some time.";
			 });
		};
	});
}());