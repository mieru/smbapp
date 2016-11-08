app.controller("loginController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.pageTitle = 'Logowanie';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showKat = false;
			$scope.loginSubmit = function() {

				var dataObj = {
					username : $scope.login_username,
					password : $scope.login_password
				};

				$http.post('/smbcustsrv/rest/query/login/checkLoginData', dataObj).success(
						function(data) {
							if (data.login_result == true) {
								$cookieStore.put("loggedIn", true);
								$cookieStore.put("loggedName", dataObj.username);
								$cookieStore.put("loggedId", data.id_user);
								$location.path('/welecomeUser');
							}else if (data.login_result == "not_active") {
								alert('Konto nie zostało aktywowane.');
							}else{
								alert('Podano nieprawidłowy login lub hasło.');
							}
						}).error(function(data) {
					alert('Blad serwera.');
				});
			};

			$scope.goToRegister = function() {
				$location.path('/rejestracja_osoba');
			};
			
		} ]);
