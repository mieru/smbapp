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
			$rootScope.showKS = false;
			$rootScope.showZgl = false;
			$rootScope.showAdm = true;
			$rootScope.showMag = false;
			$http.post('/smbemplsrv/rest/companyinfo/getCompanyInfo').success(function(data){
				$rootScope.firma = data;
			})
			
			$scope.saveForm = function(){
				$http.post('/smbemplsrv/rest/companyinfo/saveCompanyInfo',$scope.firma);
			}
			
		} ]);

