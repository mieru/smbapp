app.controller("fakturaController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.pageTitle = 'Faktury';
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
				
				if($location.path() == '/fakturywszystkie'){
					config = {};
				}
			
				$http.post('/smbemplsrv/rest/invoice/getUsersInvoice', config).success(function(response){
					$scope.faktury = response;
				});

			$scope.goToMy = function(){
				$location.path('/fakturymoje');   
			}
			
			$scope.goToAll = function(){
				$location.path('/fakturywszystkie');
			}
			
			$scope.downloadPdf = function(faktura){
				var url = '/smbemplsrv/rest/invoice/getInvoicePdfFile?';
					url += 'idFaktury=' + faktura.id;
				window.location.replace(url);
			}
			
		}]);
			
