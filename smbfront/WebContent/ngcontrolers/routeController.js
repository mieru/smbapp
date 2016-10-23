var app = angular.module('smb', [ 'ngRoute', 'ngCookies' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}else{
					alert($cookieStore.get("loggedName"));
				}
			}
		},
		templateUrl : "home.html"
	}).when('/logowanie', {
			resolve : {
				"check" : function($location, $cookieStore) {
					if ($cookieStore.get("loggedIn")) {
						$location.path('/');
					}
				}
			},
			templateUrl : "loginPage.html"
	}).otherwise({
		resolve : {
			"check" : function($location, $cookieStore) {
				if ($cookieStore.get("loggedIn")) {
					$location.path('/');
				}else{
					location.path('/logowanie');
				}
			}
		},
	});
});