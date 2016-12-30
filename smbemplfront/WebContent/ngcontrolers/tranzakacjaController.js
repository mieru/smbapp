app.controller("tranzakacjaController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.pageTitle = 'Historai tranzakcji';
			$rootScope.showKS = true;
			$rootScope.showZgl = false;
			$rootScope.showAdm = false;
			$rootScope.showMag = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");

			
			var config = {
					id_uzytkownika : $cookieStore.get('loggedId'),
				}
			
			if($location.path() == '/histtranzwszystkie'){
				config = {};
			}
			
				$http.post('/smbemplsrv/rest/transaction/getTransactions', config).success(function(response){
					$scope.tanzakacje = response;
				});

			$scope.goToMy = function(){
				$location.path('/histtranzmoje');   
			}
			
			$scope.goToAll = function(){
				$location.path('/histtranzwszystkie');
			}
			
		}]);

