var app = angular.module('smb', [ 'ngRoute', 'ngCookies', 'ui.bootstrap' ]);

app.controller("mainContoller", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			
			$http.post('/smbemplsrv/rest/query/katzgl/getAll').success(function(data){
				$rootScope.katZgl = data;
			})
			
			$http.post('/smbemplsrv/rest/query/magkonf/getAll').success(function(data, status, headers, config) {
				$rootScope.magazyny = data;
			})
			
				$rootScope.adminMenu = [{
					nazwa: 'Uzytkownicy',
					href : '#/uzytkonfprac'
				},{
					nazwa: 'Kategorie produktów',
					href : '#/katproduktkonf'
				},{
					nazwa: 'Kategorie zgłoszeń',
					href : '#/katzglkonf'
				},{
					nazwa: 'Magazyny',
					href : '#/dodajMagazyn'
				},{
					nazwa: 'Dane firmy',
					href : '#/danefirmykonf'
				},{
					nazwa: 'Konf. skrzynki poczt.',
					href : '#/konfskrzynkipoczt'
				}];
				
			$rootScope.kupSprzedMenu = [{
				nazwa: 'Zamówienia',
				href : '#/zamowienia'
			},{
				nazwa: 'Faktury',
				href : '#/faktury'
			},{
				nazwa: 'Paragony',
				href : '#/paragony'
			},{
				nazwa: 'Hist. tranzakcji',
				href : '#/histtranz'
			}];
			
			$scope.wyloguj = function() {
				$cookieStore.remove("loggedIn");
				$cookieStore.remove("loggedName");
				$cookieStore.remove("loggedId");
				$cookieStore.remove("isAdm");
				$cookieStore.remove("isPrac");
				$rootScope.logged = false;
				$rootScope.showAdmin = false;
				$rootScope.showPrac = false;
				$location.path('/logowanie');
			};
			
			$scope.goToLoggin = function() {
				$location.path('/logowanie');
			};
			
		}]);
app.directive('magazynmenu',["$location", function($location) {
	 return {
		    templateUrl: "magazynmenu.html"
		    };

}]);
app.directive('adminmenu',["$location", function($location) {
	 return {
		    templateUrl: "adminmenu.html"
		    };

}]);
app.directive('zglmenu',["$location", function($location) {
	 return {
		    templateUrl: "zglmenu.html"
		    };

}]);
app.directive('kupsprzedmenu',["$location", function($location) {
	 return {
		    templateUrl: "kupnosprzedazmenu.html"
		    };

}]);



