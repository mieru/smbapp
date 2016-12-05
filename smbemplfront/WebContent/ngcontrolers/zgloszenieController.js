app.controller("zgloszenieController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore, $route, $routeParams) {
			$rootScope.pageTitle = 'Zgloszenia';
			$rootScope.showKat = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
				id_kategorii : $routeParams.katId
			}
			
			
			$http.post('/smbemplsrv/rest/query/zgloszenia/getZgloszenia', config).success(function(response){
				$scope.zgloszenia = response;
			});
			
			$scope.showDetail = function(zgloszenie){
				$location.path('/zgloszenieDetail').search({id_zgloszenia: zgloszenie.id});
			}
			
		}]);

app.controller("zgloszenieDetailController", [
                                		'$scope',
                                		'$http',
                                		'$location',
                                		'$rootScope',
                                		'$cookieStore',
                                		'$routeParams',
                                		function($scope, $http, $location, $rootScope, $cookieStore, $routeParams) {
                                			$rootScope.pageTitle = 'Zgloszenie';
                                			getZgloszenieById();
                                			

                                			$scope.closeTask = function(){
                                				var config = {
                                        				id_zgloszenia : $routeParams.id_zgloszenia
                                        			}
                                				$http.post('/smbemplsrv/rest/command/zgloszenie/closeZgl', config).success(function(data, status, headers, config) {
                                					getZgloszenieById();
                                				});
                                			}
                                			
                                			$scope.addActivity = function(){
                                				addAktywnosciDoZgloszenia();
                                			}
                                			
                                			function getZgloszenieById(){
                                				var config = {
                                        				id_zgloszenia : $routeParams.id_zgloszenia,
                                        			}
                                        			$http.post('/smbemplsrv/rest/query/zgloszenia/getZgloszenieById', config).success(function(response){
                                        				$scope.zgloszenieDetail = response;
                                        				$scope.aktywnosci = response.aktywnosci;
                                        			});
                                			}
                                			
                                			function addAktywnosciDoZgloszenia(){
                                				var config = {
                                        				tresc : $scope.tresc_wiadomosci,
                                        				id_zgloszenia : $routeParams.id_zgloszenia,
                                        				id_uzytkownika: $cookieStore.get('loggedId')
                                        			}
                                        			$http.post('/smbemplsrv/rest/command/zgloszenie/addNewMessage', config).success(
                                        					function(response){
                                        						getZgloszenieById();
                                        						$scope.tresc_wiadomosci = "";
                                        			});
                                			}
                                			
                                			
                                		}]);
			
