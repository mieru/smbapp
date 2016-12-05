app.config(function($routeProvider) {
	
	$routeProvider.when('/', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}else{
					$location.path('/sprzedaz');
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
	}).when('/administracja', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}else if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "administracja.html"
	}).when('/magazyn', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "obsMagazynu.html"
	}).when('/zgloszenia', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zgloszenia.html"
	}).when('/sprzedaz', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "sprzedaz.html"
	}).when('/katproduktkonf', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "katproduktow.html"
	}).when('/dodajMagazyn', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "magazynKonfDodaj.html"
	}).when('/uzytkonfprac', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "uzytKonfPrac.html"
	}).when('/uzytkonfklient', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "uzytKonfKlient.html"
	}).when('/katzglkonf', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "katZglKonf.html"
	}).when('/konfskrzynkipoczt', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "adminKonfMail.html"
	}).when('/danefirmykonf', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "daneFirmyKonf.html"
	}).when('/klientEdit', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "klientEdit.html"
	}).when('/pracEdit', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "pracEdit.html"
	}).when('/dodajPrac', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "dodajPrac.html"
	}).when('/dodajKlienta', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "dodajKlienta.html"
	}).when('/dodajKlientaFirma', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
		templateUrl : "dodajKlientaFirma.html"
	}).when('/dodajProdukt', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "dodajProdukt.html"
	}).when('/edytujProdukt', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "edytujProdukt.html"
	}).when('/zgloszenieDetail', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zgloszenieDetail.html"
	}).otherwise({
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
				
				if(!$cookieStore.get("isAdm")){
					$location.path('/');
				}
			}
		},
	});
});