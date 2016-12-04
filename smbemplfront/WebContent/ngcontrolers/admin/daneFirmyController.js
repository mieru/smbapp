app.controller("daneFirmyController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		function($scope, $http, $location, $rootScope, $cookieStore) {
			$rootScope.pageTitle = 'Administracja - dane firmy';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			$rootScope.showKat = false;
			$http.post('/smbemplsrv/rest/query/danefirmy/getData').success(function(data){
				$rootScope.firma = data;
			})
			
			$scope.saveForm = function(){
				$http.post('/smbemplsrv/rest/command/danefirmy/saveData',$scope.firma);
			}
			
		} ]);

