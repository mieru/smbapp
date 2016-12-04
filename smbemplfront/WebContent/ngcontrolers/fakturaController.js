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
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");

			
			var config = {
					id_uzytkownika : $cookieStore.get('loggedId'),
					status : ''
				}
				$http.post('/smbcustsrv/rest/query/faktury/getFaktury', config).success(function(response){
					$scope.faktury = response;
				});

			$scope.downloadPdf = function(faktura){
				var url = '/smbcustsrv/rest/faktury/query/downloadPdf?';
					url += 'idFaktury=' + faktura.id;
				window.location.replace(url);
			}
			
		}]);
			
app.directive('fakturatable',[function() {
	 return {
		    templateUrl: "fakturaTable.html"
		    };

}]);