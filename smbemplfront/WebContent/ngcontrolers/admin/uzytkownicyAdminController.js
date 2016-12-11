app.controller("uzytkownicyAdminController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		function($scope, $http, $location, $rootScope, $cookieStore) {
			$rootScope.pageTitle = 'Administracja - uzytkownicy';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			$rootScope.showKat = false;
			if ($location.path() == '/uzytkonfklient') {
				$scope.type = 'C'
			} else {
				$scope.type = 'E'
			}

			var data = {
				type : $scope.type
			}

			$http.post('/smbemplsrv/rest/query/uzytkonf/getUsers', data)
					.success(function(resp) {
						$rootScope.uzytkownicy = resp;
					})

			$scope.removeElement = function(uzyt) {
				$http
						.post('/smbemplsrv/rest/command/uzytkonf/deleteUser',
								uzyt);
				$rootScope.uzytkownicy.splice($rootScope.uzytkownicy
						.indexOf(uzyt), 1);
			}

			$scope.goToPracownicy = function() {
				$location.path("/uzytkonfprac");
			}

			$scope.goToKlienci = function() {
				$location.path("/uzytkonfklient");
			}

			$scope.goToAddEmployee = function() {
				$location.path("/dodajPracownika");
			}

			$scope.goToAddCustomer = function() {
				$location.path("/dodajKlienta");
			}

			$scope.editKlientForm = function(user) {
				$location.path("/klientEdit").search({
					id_user : user.id_uzytkownika
				});
			}
			
			$scope.goToAddEmployee = function() {
				$location.path("/dodajPrac");
			}
			
			$scope.editPracForm = function(user) {
				$location.path("/pracEdit").search({
					id_user : user.id_uzytkownika
				});
			}

		} ]);

app.controller("editKlientController",
		[
				'$scope',
				'$http',
				'$location',
				'$rootScope',
				'$cookieStore',
				'$routeParams',
				function($scope, $http, $location, $rootScope, $cookieStore,
						$routeParams) {
					$rootScope.pageTitle = 'Administracja - edycja klienta';

					var data = {
						id : $routeParams.id_user,
					}

					$http.post('/smbemplsrv/rest/query/uzytkonf/getUserById',
							data).success(function(resp) {
						$scope.user = resp;
						if (resp.company_name !== undefined) {
							$scope.userCompany = true;
							$scope.userPerson = false;
						} else {
							$scope.userCompany = false;
							$scope.userPerson = true;
						}
					})
					$scope.editKlientAction = function() {
						$http.post(
								'/smbemplsrv/rest/command/uzytkonf/editUser',
								$scope.user);
					}

					$scope.removeElement = function() {
						http.post(
								'/smbemplsrv/rest/command/uzytkonf/deleteUser',
								uzyt);
					}

				} ]);

app.controller("editPracController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore,
				$routeParams) {
			$rootScope.pageTitle = 'Administracja - edycja pracownika';

			var data = {
				id : $routeParams.id_user,
			}
			$scope.czyPrac = false;
			$scope.czyAdmin = false;

			$http.post('/smbemplsrv/rest/query/uzytkonf/getUserById', data)
					.success(function(resp) {
						$scope.user = resp;

						if ($scope.user.uprKod.indexOf('M') !== -1) {
							$scope.czyMagazyn = true;
						}
						if ($scope.user.uprKod.indexOf('E') !== -1) {
							$scope.czyPrac = true;
						}
						if ($scope.user.uprKod.indexOf('A') !== -1) {
							$scope.czyAdmin = true;
						}

					})

			$scope.editPracAction = function() {
				$scope.user.uprKod = "";
				if ($scope.czyPrac) {
					$scope.user.uprKod += "E";
				}
				if ($scope.czyAdmin) {
					if ($scope.user.uprKod == "") {
						$scope.user.uprKod = "A"
					} else {
						$scope.user.uprKod += ",A";
					}
				}
				if ($scope.czyMagazyn) {
					if ($scope.user.uprKod == "") {
						$scope.user.uprKod = "M"
					} else {
						$scope.user.uprKod += ",M";
					}
				}
				$http.post('/smbemplsrv/rest/command/uzytkonf/editUser',
						$scope.user);
			}

		} ]);

app.controller("dodajPracController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore,
				$routeParams) {
			$rootScope.pageTitle = 'Administracja - dodaj pracownika';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("idAdm");
		
			$scope.dodajPracAction = function() {
				$scope.newUser.status = "A";
				$scope.newUser.uprKod = "";
				if ($scope.czyPrac) {
					$scope.newUser.uprKod += "E";
				}
				if ($scope.czyAdmin) {
					if ($scope.newUser.uprKod == "") {
						$scope.newUser.uprKod = "A"
					} else {
						$scope.newUser.uprKod += ",A";
					}
				}
				if ($scope.czyMagazyn) {
					if ($scope.user.uprKod == "") {
						$scope.user.uprKod = "M"
					} else {
						$scope.user.uprKod += ",M";
					}
				}
				$http.post('/smbemplsrv/rest/command/uzytkonf/addNewUser',
						$scope.newUser);
			}
			
		} ]);

app.controller("dodajKlientaController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore,
				$routeParams) {
			$rootScope.pageTitle = 'Administracja - dodaj klienta';

			$scope.dodajKlientAction = function() {
				$scope.newUser.uprKod = "C";
				$scope.newUser.status = "A";
				
				if($scope.newUser.name !== undefined)
					$scope.newUser.customer_type = "P";
				else{
					$scope.newUser.customer_type = "C";
				}
				
				$http.post('/smbemplsrv/rest/command/uzytkonf/addNewUser',
						$scope.newUser);
			}
			
			$scope.goToRegCompany = function() {
				$location.path("/dodajKlientaFirma");
			}
			
			$scope.goToRegPerson = function() {
				$location.path("/dodajKlienta");
			}
			
			

		} ]);