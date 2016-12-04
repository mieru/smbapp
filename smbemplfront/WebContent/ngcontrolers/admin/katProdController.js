app.controller("katProdController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		function($scope, $http, $location, $rootScope, $cookieStore) {
			$rootScope.pageTitle = 'Administracja - kategorie produktów';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			$rootScope.showKat = false;
			$http.post('/smbemplsrv/rest/query/katprod/getAll').success(function(data){
				$rootScope.kategorieOferta = data;
			})
			$scope.dodajKategorie = function(){
				var data = {
						name: $scope.name
				}
				
				$http.post('/smbemplsrv/rest/command/katprod/addNewCategory',data).success(function(resp){
					$rootScope.kategorieOferta.push(resp);
				});
				
			}
			
			$scope.removeElement = function(kat){
				$http.post('/smbemplsrv/rest/command/katprod/deleteCategory',kat).success(function(resp){
					$rootScope.kategorieOferta.splice($rootScope.kategorieOferta.indexOf(kat), 1);
				});
			}
			
		} ]);

