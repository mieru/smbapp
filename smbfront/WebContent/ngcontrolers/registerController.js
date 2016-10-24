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
					email : $scope.register_email
				};

				$http.post('/smbcustsrv/rest/registerUser', dataObj).success(
						function(data) {
							alert("success");
						}).error(function(data) {
					alert('Blad serwera.');
				});
			};
			
			$scope.goToLoggin = function() {
				$location.path('/logowanie');
			};
		} ]);
