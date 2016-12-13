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
		templateUrl : "uzytKonfPrac.html"
	}).when('/magazyn', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "obsMagazynu.html"
	}).when('/zgloszeniamoje', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zgloszeniamoje.html"
	}).when('/zgloszeniawszystkie', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zgloszeniawszystkie.html"
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
	}).when('/paragonDetail', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "paragonDetail.html"
	}).when('/zamowienieDetail', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zamowienieDetail.html"
	}).when('/dodajSprzedaz', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "dodajSprzedaz.html"
	}).when('/dodajZakup', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "dodajZakup.html"
	}).when('/zamowieniamoje', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zamowieniamoje.html"
	}).when('/fakturymoje', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "fakturymoje.html"
	}).when('/paragonymoje', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "paragonymoje.html"
	}).when('/paragonywszystkie', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "paragonywszystkie.html"
	}).when('/histtranzmoje', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "histtranmoje.html"
	}).when('/histtranzwszystkie', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "histtranwszystkie.html"
	}).when('/fakturywszystkie', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "fakturywszystkie.html"
	}).when('/zamowieniawszystkie', {
		resolve : {
			"check" : function($location, $cookieStore) {
				if (!$cookieStore.get("loggedIn")) {
					$location.path('/logowanie');
				}
			}
		},
		templateUrl : "zamowieniawszystkie.html"
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