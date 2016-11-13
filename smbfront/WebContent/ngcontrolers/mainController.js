var app = angular.module('smb', [ 'ngRoute', 'ngCookies', 'ui.bootstrap' ]);

app.controller("mainContoller", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.showKat = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			
			$http.post('/smbcustsrv/rest/query/kattowar/getAll').success(function(data){
				$rootScope.kategorieOferta = data;
			})
			
			$scope.wyloguj = function() {
				$cookieStore.remove("loggedIn");
				$cookieStore.remove("loggedName");
				$cookieStore.remove("loggedId");
				$location.path('/logowanie');
			};
			
			$scope.goToLoggin = function() {
				$location.path('/logowanie');
			};
			
		}]);
			
app.directive('kategorie',["$location", function($location) {
		 return {
			    templateUrl: "kategorieOfertaMenu.html"
			    };
	 
	}]);