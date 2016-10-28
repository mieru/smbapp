var app = angular.module('smb', [ 'ngRoute', 'ngCookies' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "home.html"
	}).when('/activationAccount', {
		templateUrl : "activationAccount.html"
	}).when('/logowanie', {
			resolve : {
				"check" : function($location, $cookieStore) {
					if ($cookieStore.get("loggedIn")) {
						$location.path('/');
					}
				}
			},
			templateUrl : "loginPage.html"
	}).when('/rejestracja', {
		templateUrl : "rejestracja.html"
	}).otherwise({
		resolve : {
			"check" : function($location, $cookieStore) {
				if ($cookieStore.get("loggedIn")) {
					$location.path('/');
				}else{
					$location.path('/logowanie');
				}
			}
		},
	});
});