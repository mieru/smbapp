app.controller("registerController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.pageTitle = 'Rejestracja';
			$rootScope.showKat = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			$scope.registerPersonSubmit = function() {
				var dataObj = {
					customer_type : "P",
					name : $scope.register_name,
					surname : $scope.register_surname,
					username : $scope.register_login,
					password : $scope.register_password,
					email : $scope.register_email,
					phone : $scope.register_phone,
					
					street : $scope.register_street,
					building_number : $scope.register_building_number,
					flat_number : $scope.register_flat_number,
					city : $scope.register_city,
					post_code : $scope.register_post_code,
					
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
			
			$scope.registerCompanySubmit = function() {
				var dataObj = {
					customer_type : "C",
					companyName : $scope.register_namecompany,
					nip : $scope.register_nip,
					username : $scope.register_login,
					password : $scope.register_password,
					email : $scope.register_email,
					phone : $scope.register_phone,
					
					street : $scope.register_street,
					building_number : $scope.register_building_number,
					flat_number : $scope.register_flat_number,
					city : $scope.register_city,
					post_code : $scope.register_post_code,
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
			
			
			$scope.goToRegPerson = function() {
				$location.path('/rejestracja_osoba');
			};
			
			$scope.goToRegCompany = function() {
				$location.path('/rejestracja_firma');
			};
			
			$scope.goToLoggin = function() {
				$location.path('/logowanie');
			};
		} ]);


