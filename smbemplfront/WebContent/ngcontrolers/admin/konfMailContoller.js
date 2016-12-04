app.controller("konfMailController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		function($scope, $http, $location, $rootScope, $cookieStore) {
			$rootScope.pageTitle = 'Administracja - konfiguracja skrzynki pocztowej';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			$rootScope.showKat = false;
			
			$http.post('/smbemplsrv/rest/query/konfmail/getMailConfiguration').success(function(data){
				$scope.mailKonf = data;
			})
			
			$scope.saveMailConf = function(){
				$http.post('/smbemplsrv/rest/command/konfmail/saveMailKonfiguration',$scope.mailKonf);
			}
			
			
		} ]);

