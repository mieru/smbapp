app.controller("katZglController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		function($scope, $http, $location, $rootScope, $cookieStore) {
			$rootScope.pageTitle = 'Administracja - kategorie zgłoszeń';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			$rootScope.showKat = false;
			$http.post('/smbemplsrv/rest/query/katzgl/getAll').success(
					function(data) {
						$rootScope.katZgl = data;
					})

			$scope.dodajKategorie = function() {
				var data = {
					name : $scope.name,
					czyKlient : $scope.czyKlient,
					czyMagazyn : $scope.czyMagazyn
				}

				$http.post('/smbemplsrv/rest/command/katzgl/addNewCategory',
						data).success(function(resp) {
					$rootScope.katZgl.push(resp);
				});

			}

			$scope.removeElement = function(kat) {
				$http.post('/smbemplsrv/rest/command/katzgl/deleteCategory',
						kat).success(
						function() {
							$rootScope.katZgl.splice($rootScope.katZgl
									.indexOf(kat), 1);
						});
			}

		} ]);
