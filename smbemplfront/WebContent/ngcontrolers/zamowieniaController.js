app.controller("zamowienieController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore, $route,$routeParams) {
			$rootScope.pageTitle = 'Zamówienia';
			$rootScope.showKS = true;
			$rootScope.showZgl = false;
			$rootScope.showAdm = false;
			$rootScope.showMag = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			var config = {
					id_kategorii : $routeParams.katId
			}
			
			
			if($location.path() == '/zamowieniamoje'){
				config = {
						id_uzytkownika : $cookieStore.get('loggedId'),
					}
			}
			
			$http.post('/smbemplsrv/rest/order/getOrders', config).success(function(response){
				$scope.zamowienia = response;
			});
			
			$scope.goToMy = function(){
				$location.path('/zamowieniamoje');   
			}
			
			$scope.goToAll = function(){
				$location.path('/zamowieniawszystkie');
			}
			
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
                                        			$http.post('/smbemplsrv/rest/order/getOrderById', config).success(function(response){
                                        				$scope.zamowienieDetail = response;
                                        				$scope.listaProd = JSON.parse(response.listaProd);
                                        				$scope.aktywnosci = response.aktywnosci;
                                        				$scope.zamowienie = response;
                                        			});
                                			}
                                			
                                			$scope.closeTask = function(){
                                				var zam = {
                                						id_uzytkownika : $cookieStore.get('loggedId'),
                                						lista: $scope.zamowienie.listaProd,
                                						czyFaktura:  $scope.zamowienie.czyFaktura,
                                						id:$routeParams.id_zamowienia,
                                						dane_do_faktury:  $scope.zamowienie.daneDoFaktury
                                				}
                                				$http.post('/smbemplsrv/rest/order/closeOrder',zam).success(function(data, status, headers, config) {
                                					getZamowienieById();
                                				});
                                			}
                                			
                                			$scope.anulujZam = function(){
                                				var zam = {
                                						id_uzytkownika : $cookieStore.get('loggedId'),
                                						lista: $scope.zamowienie.listaProd,
                                						czyFaktura: $scope.zamowienie.czyFaktura,
                                						id: $routeParams.id_zamowienia, 
                                						daneDoFakt: $scope.zamowienie.daneDoFakt
                                				}
                                				$http.post('/smbemplsrv/rest/order/cancelOrder',zam).success(function(data, status, headers, config) {
                                					getZamowienieById();
                                				});
                                			}
                                			
                                			function getAktywnosciDoZamowienia(){
                                				var config = {
                                        				tresc : $scope.tresc_wiadomosci,
                                        				id: $routeParams.id_zamowienia,
                                        				id_zamawiajacego: $cookieStore.get('loggedId')
                                        			}
                                        			$http.post('/smbemplsrv/rest/order/addMessageToOrder', config).success(
                                        					function(response){
                                        						getZamowienieById();
                                        						$scope.tresc_wiadomosci = "";
                                        			});
                                			}
                                			
                                			
                                		}]);
			
