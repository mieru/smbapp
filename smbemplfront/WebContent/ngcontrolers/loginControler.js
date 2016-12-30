app.controller("loginController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		function($scope, $http, $location, $rootScope, $cookieStore) {
			$rootScope.pageTitle = 'Logowanie';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			$rootScope.showKat = false;
			$scope.loginSubmit = function() {

				var dataObj = {
					username : $scope.login_username,
					password : $scope.login_password
				};

				$http.post('/smbemplsrv/rest/login/checkLogin',
						dataObj).success(function(data) {
					if (data.login_result == true) {
						$cookieStore.put("loggedIn", true);
						$cookieStore.put("isAdm", data.isAdm);
						$cookieStore.put("isPrac", data.isPrac);
						$cookieStore.put("loggedName", dataObj.username);
						$cookieStore.put("loggedId", data.id_user);
						$rootScope.logged = true;
						$rootScope.showAdmin = data.isAdm;
						$rootScope.showPrac = data.isPrac;
						if(data.isAdm){
							$location.path('/uzytkonfprac');
						}else{
							$location.path('/sprzedaz');
						}
						
					} else {
						alert('Podano nieprawidłowy login lub hasło.');
					}
				}).error(function(data) {
					alert('Blad serwera.');
				});
			};
		} ]);
