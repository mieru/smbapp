app.controller("registerController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$scope.registerSubmit = function() {
				var dataObj = {
					username : $scope.register_username,
					password : $scope.register_password,
					email : $scope.register_email,
					activationUri : $location.absUrl()
				};
			alert("Rejestacja...")
				$http.post('/smbcustsrv/rest/command/register/registerUser', dataObj).success(
						function(data) {
							alert("Na twój email został wysłany link aktywacyjny.");
						}).error(function(data) {
					alert('Blad serwera.');
				});
			};
			
			
			
			$scope.goToLoggin = function() {
				$location.path('/logowanie');
			};
		} ]);


