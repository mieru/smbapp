app.controller("zamowienieController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.pageTitle = 'Zamówienia';
			$rootScope.showKat = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
				status : ''
			}
			$http.post('/smbcustsrv/rest/query/zamowienia/getZamowienia', config).success(function(response){
				$scope.zamowienia = response;
			});
			
			$scope.showDetail = function(zamowienie){
				$location.path('/zamowienieDetail').search({id_zamowienia: zamowienie.id});;
			}
			
		}]);

app.controller("zamowienieDetailController", [
                                		'$scope',
                                		'$http',
                                		'$location',
                                		'$rootScope',
                                		'$cookieStore',
                                		'$routeParams',
                                		function($scope, $http, $location, $rootScope, $cookieStore, $routeParams) {
                                			$rootScope.pageTitle = 'Zamówienie';
                                			getZamowienieById();
                                			
                                			$scope.addActivity = function(){
                                				getAktywnosciDoZamowienia();
                                			}
                                			
                                			function getZamowienieById(){
                                				var config = {
                                        				id_zamowienia : $routeParams.id_zamowienia,
                                        			}
                                        			$http.post('/smbcustsrv/rest/query/zamowienia/getZamowienieById', config).success(function(response){
                                        				$scope.zamowienieDetail = response;
                                        				$scope.listaProd = JSON.parse(response.listaProd);
                                        				$scope.aktywnosci = response.aktywnosci;
                                        			});
                                			}
                                			
                                			function getAktywnosciDoZamowienia(){
                                				var config = {
                                        				tresc : $scope.tresc_wiadomosci,
                                        				id_zamowienia: $routeParams.id_zamowienia,
                                        				id_zamawiajacego: $cookieStore.get('loggedId')
                                        			}
                                        			$http.post('/smbcustsrv/rest/command/zamowienie/addMessageToOrder', config).success(
                                        					function(response){
                                        						getZamowienieById();
                                        						$scope.tresc_wiadomosci = "";
                                        			});
                                			}
                                			
                                			
                                		}]);
			
app.directive('zamowienietable',[function() {
		 return {
			    templateUrl: "zamowienieTable.html"
			    };
	 
	}]);