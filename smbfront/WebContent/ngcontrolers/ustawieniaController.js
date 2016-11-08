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
			var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
			}
			$http.post('/smbcustsrv/rest/query/uzytkownik/getUzytkownikaById', config).success(function(response){
				$scope.user = response;
			});
			
			
			
			$scope.saveForm = function(){
				$http.post('/smbcustsrv/rest/command/uzytkownik/saveUserData', $scope.user).success(function(response){
					alert('dane poprawione');
				});
			}
			
			
			
		}]);
