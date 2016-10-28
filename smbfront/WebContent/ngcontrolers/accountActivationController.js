app.controller("accountActivationController", [
		'$scope',
		'$http',
		'$location',
		'$routeParams',
		'$route',
		function($scope, $http, $location, $routeParams, $route) {
			$scope.activateAccount = function() {
				var dataObj = {
					code : $routeParams.code,
				};
				$http.post('/smbcustsrv/rest/command/register/activeAccount', dataObj).success(
						function(data) {
							alert("Twoje konto jest aktywne, mozesz się zalogować.");
							$location.path("/logowanie");
						}).error(function(data) {
							alert('Blad serwera.');
				});
			};
			
		} ])