app.controller("paragonController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.pageTitle = 'Paragony';
			$rootScope.showKS = true;
			$rootScope.showZgl = false;
			$rootScope.showAdm = false;
			$rootScope.showMag = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");

			
			var config = {
					id_uzytkownika : $cookieStore.get('loggedId'),
				}
			
			if($location.path() == '/paragonywszystkie'){
				config = {};
			}
			
				$http.post('/smbemplsrv/rest/query/paragon/getParagony', config).success(function(response){
					$scope.paragony = response;
				});

			$scope.goToMy = function(){
				$location.path('/paragonymoje');   
			}
			
			$scope.goToAll = function(){
				$location.path('/paragonywszystkie');
			}
			
			$scope.showDetail = function(parag){
				$location.path('/paragonDetail').search({id_paragon: parag.id});;
			}
		}]);

app.controller("paragonDetailController", [
                                      		'$scope',
                                      		'$http',
                                      		'$location',
                                      		'$rootScope',
                                      		'$cookieStore',
                                      		'$routeParams',
                                      		function($scope, $http, $location, $rootScope, $cookieStore, $routeParams) {
                                      			$rootScope.pageTitle = 'Paragon';
                                      			getParagonById();
                                      			
                                      			
                                      			function getParagonById(){
                                      				var config = {
                                              				id_paragonu : $routeParams.id_paragon,
                                              			}
                                              			$http.post('/smbemplsrv/rest/query/paragon/getParagonById', config).success(function(response){
                                              				$scope.paragonDetail = response;
                                              				$scope.listaProd = JSON.parse(response.listaProd);
                                              			});
                                      			}
                                      			
                                      			
                                      		}]);
