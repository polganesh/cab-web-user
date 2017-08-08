//Define an angular module for our app
var bookingModule = angular.module('bookingApp', ['ngRoute']);
(function(){
	bookingModule.config(['$routeProvider',
	                      function($routeProvider) {
	                            $routeProvider.
	                              when('/bookcab', {
	                                templateUrl: '/html/template/user/bookcab.html',
	                            }).
	                              when('/historyandcancelcab', {
	                                templateUrl: '/html/template/user/historyandcancelcab.html',
	                              }).
	                              when('/login', {
		                                templateUrl: '/html/template/public/login.html',
		                           }).
		                           otherwise({
		                                redirectTo: '/bookcab'
		                            });
	                              
	                              /*
	                              otherwise({
	                                redirectTo: '/bookcab'
	                              });*/
	                        }]);
})();


/*--------------
(function(){
	bookingModule.config(['$stateProvider',
	                    function($stateProvider) {
							$stateProvider
								.state('welcome', {
								url: '/welcome',
								// ...
								data: {
									requireLogin: false
								}
								})
	                            
	                        }]);
})();


bookingModule.config(function ($stateProvider) {
	console.log("in state provider ");
	  $stateProvider
	    .state('login', {
	      url: '/login',
	      // ...
	      data: {
	        requireLogin: false
	      }
	    })
	    .state('bookcab', {
	      url: '/bookcab',
	      // ...
	      data: {
	        requireLogin: true
	      }
	    })
	    .state('historyandcancelcab', {
	      url: '/historyandcancelcab',
	      // ...
	      data: {
	        requireLogin: true
	      }
	    })
});


bookingModule.run(function ($rootScope) {
	$rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
	    var requireLogin = toState.data.requireLogin;
	    if (requireLogin && typeof $rootScope.currentUser === 'undefined') {
	      event.preventDefault();
	      
	    }
	  });

	});

*/

