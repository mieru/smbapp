app.controller("fakturaController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.pageTitle = 'Faktury';
			$rootScope.showKat = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			var config = {
					id_uzytkownika : $cookieStore.get('loggedId'),
					status : ''
				}
				$http.post('/smbcustsrv/rest/query/faktury/getFaktury', config).success(function(response){
					$scope.faktury = response;
				});
				
				
		}]);
			
app.directive('fakturatable',[function() {
	 return {
		    templateUrl: "fakturaTable.html"
		    };

}]);