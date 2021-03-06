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
			$rootScope.showKS = false;
			$rootScope.showZgl = false;
			$rootScope.showAdm = true;
			$rootScope.showMag = false;
			$http.post('/smbemplsrv/rest/commoditycategory/getAll').success(function(data){
				$rootScope.kategorieOferta = data;
			})
			$scope.dodajKategorie = function(){
				var data = {
						name: $scope.name
				}
				
				$http.post('/smbemplsrv/rest/commoditycategory/addNewCommodityCategoty',data).success(function(resp){
					$rootScope.kategorieOferta.push(resp);
				});
				
			}
			
			$scope.removeElement = function(kat){
				$http.post('/smbemplsrv/rest/commoditycategory/deleteCommodityCategory',kat).success(function(resp){
					$rootScope.kategorieOferta.splice($rootScope.kategorieOferta.indexOf(kat), 1);
				});
			}
			
		} ]);

