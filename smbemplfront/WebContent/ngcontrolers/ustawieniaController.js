app.controller("ustawieniaController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore, $routeParams) {
			$rootScope.pageTitle = 'Ustawienia';
			$rootScope.showKat = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
			}
			$http.post('/smbcustsrv/rest/userconf/getUserById', config).success(function(response){
				$scope.user = response;
				if(response.company_name !== undefined){
					$scope.userCompany = true;
					$scope.userPerson = false;
				}else{
					$scope.userCompany = false;
					$scope.userPerson = true;
				}
			});
			
			
			
			$scope.saveForm = function(){
				$http.post('/smbcustsrv/rest/userconf/editUser', $scope.user).success(function(response){
					alert('dane poprawione');
				});
			}
			
			
			
		}]);
