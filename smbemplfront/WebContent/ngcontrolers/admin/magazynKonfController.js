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
			$rootScope.showKat = false;
			$http.post('/smbemplsrv/rest/query/magkonf/getAll').success(function(data){
				$rootScope.magazyny = data;
			})
			$scope.dodajMagazyn = function(){
				var data = {
						name: $scope.name
				}
				
				$http.post('/smbemplsrv/rest/command/magkonf/addNewMag',data).success(function(resp){
					$rootScope.magazyny.push(resp);
				});
				
			}
			
			$scope.removeElement = function(kat){
				$http.post('/smbemplsrv/rest/command/magkonf/deleteMag',kat).success(function(resp){
					$rootScope.magazyny.splice($rootScope.magazyny.indexOf(kat), 1);
				});
			}
			
		} ]);

