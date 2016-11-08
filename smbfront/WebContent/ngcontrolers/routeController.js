app.config(function($routeProvider) {
	
	$routeProvider.when('/', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}else{
					$location.path('/oferta');
				}
			}
		},
		template : ""
			
	}).when('/logowanie', {
			resolve : {
				"check" : function($location, $cookieStore) {
					if ($cookieStore.get("loggedIn")) {
						$location.path('/');
					}
				}
			},
			templateUrl : "loginPage.html"
	}).when('/activationAccount', {
		templateUrl : "activationAccount.html"
	}).when('/rejestracja_osoba', {
		templateUrl : "rejestracja_osoba.html"
	}).when('/rejestracja_firma', {
		templateUrl : "rejestracja_firma.html"
	}).when('/cartView', {
		templateUrl : "cartView.html"
	}).when('/oferta', {
		templateUrl : "oferta.html"
	}).when('/zamowienieDetail', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zamowienieDetail.html"
	}).when('/zgloszenieDetail', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zgloszenieDetail.html"
	}).when('/zamowienia', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zamowienia.html"
	}).when('/faktury', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "faktury.html"
	}).when('/ustawienia', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "ustawienia.html"
	}).when('/kontakt', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "kontakt.html"
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