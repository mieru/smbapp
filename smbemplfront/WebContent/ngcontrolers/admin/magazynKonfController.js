app.controller("magazynKonfController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		function($scope, $http, $location, $rootScope, $cookieStore) {
			$rootScope.pageTitle = 'Administracja - konfiguracja magazynów';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			$rootScope.showKS = false;
			$rootScope.showZgl = false;
			$rootScope.showAdm = true;
			$rootScope.showMag = false;
			$http.post('/smbemplsrv/rest/warehouseconf/getWarehouses').success(function(data){
				$rootScope.magazyny = data;
			})
			$scope.dodajMagazyn = function(){
				var data = {
						name: $scope.name
				}
				
				$http.post('/smbemplsrv/rest/warehouseconf/addWarehouse',data).success(function(resp){
					$rootScope.magazyny.push(resp);
				});
				
			}
			
			$scope.removeElement = function(kat){
				$http.post('/smbemplsrv/rest/warehouseconf/deleteWarehouse',kat).success(function(resp){
					$rootScope.magazyny.splice($rootScope.magazyny.indexOf(kat), 1);
				});
			}
			
		} ]);

