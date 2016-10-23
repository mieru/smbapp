app.controller("loginController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		function($scope, $http, $location, $rootScope, $cookieStore) {
			$scope.loginSubmit = function() {

				var dataObj = {
					username : $scope.username,
					password : $scope.password,
				};

				$http.post('/smbcustsrv/rest/checkLoginData', dataObj).success(
						function(data) {
							if (data.logged) {
								$cookieStore.put("loggedIn", true);
								$cookieStore.put("loggedName", dataObj.username);
								$location.path('/welecomeUser');
							} else {
								alert('Podano nieprawidłowy login lub hasło.');
							}
						}).error(function(data) {
					alert('Blad serwera.');
				});
			};
		} ]);
