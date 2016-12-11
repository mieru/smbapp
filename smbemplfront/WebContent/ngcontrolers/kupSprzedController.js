app.controller("kupSprzedController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore, $route, $routeParams) {
			$rootScope.pageTitle = 'Kupno/Sprzedaz';
			$rootScope.showKat = false;
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			
		}]);


app.controller("dodajZakupController", [
                               		'$scope',
                               		'$http',
                               		'$location',
                               		'$rootScope',
                               		'$cookieStore',
                               		'$route',
                               		'$routeParams',
                               		function($scope, $http, $location, $rootScope, $cookieStore, $route, $routeParams) {
                               			$rootScope.pageTitle = 'Zakup - dodaj';
                               			$rootScope.showKat = false;
                               			$rootScope.logged = $cookieStore.get("loggedIn");
                               			$rootScope.showPrac = $cookieStore.get("isPrac");
                               			$rootScope.showAdmin = $cookieStore.get("isAdm");
                               			
                               			
                               			var postData = {};
                               			
                               			$http.post('/smbemplsrv/rest/query/produkt/getProdukty', postData)
                    					.success(function(data) {
                    								$scope.towary = data;
                    							});

                               			initScopeFromSession();
                               			$scope.koszykLista = $cookieStore.get('koszykListaZakup');
                               			
                            			$scope.addToList = function(towar) {
                            				$scope.koszyk.iloscAll += 1;
                            				$scope.koszyk.wartoscAllBrutto += towar.cbrutto;
                            				$scope.koszyk.wartoscAllNetto += towar.cnetto;
                            				$scope.koszyk.sumaPodatkuVat += (towar.cnetto * (towar.stawka_vat/100));

                            				$cookieStore.put("koszykIloscAllZakup", $scope.koszyk.iloscAll);
                            				$cookieStore.put("koszykwartoscAllBruttoZakup", $scope.koszyk.wartoscAllBrutto);
                            				$cookieStore.put("koszykwartoscAllNettoZakup", $scope.koszyk.wartoscAllNetto);
                            				$cookieStore.put("koszyksumaPodatkuVatZakup", $scope.koszyk.sumaPodatkuVat);

                            				var koszykLista = $cookieStore.get('koszykListaZakup');
                            				if (koszykLista == null) {
                            					koszykLista = [];
                            				}
                            				var isContains = false;

                            				for (var i = 0; i < koszykLista.length; i++) {
                            					if (koszykLista[i].id == towar.id) {
                            						isContains = true;
                            						koszykLista[i].ilosc += 1;
                            						break;
                            					}
                            				}
                            				if (!isContains) {
                            					koszykLista.push({
                            						id : towar.id,
                            						ilosc : 1,
                            						jednostka: towar.jednostka,
                            						nazwa : towar.nazwa,
                            						cbrutto : towar.cbrutto,
                            						cnetto : towar.cnetto,
                            						stawka_vat : towar.stawka_vat
                            					});
                            				}
                            				$scope.koszykLista=koszykLista;
                            				$cookieStore.put('koszykListaZakup', koszykLista);
                            			};

                            			$scope.goToCartView = function() {
                            				$location.path("/cartView");
                            			};
                            			
                            			
                            			$scope.removeElement = function(item) {
                            				$scope.koszyk.iloscAll -= parseInt(item.ilosc);
                            				$scope.koszyk.wartoscAllBrutto -= item.cbrutto * item.ilosc;
                            				$scope.koszyk.wartoscAllNetto -= item.cnetto * item.ilosc;
                            				$scope.koszyk.sumaPodatkuVat -= (item.cnetto * (item.stawka_vat/100)) * parseInt(item.ilosc);
                            				
                            				$cookieStore.put("koszykIloscAllZakup", $scope.koszyk.iloscAll);
                            				$cookieStore.put("koszykwartoscAllBruttoZakup", $scope.koszyk.wartoscAllBrutto);
                            				$cookieStore.put("koszykwartoscAllNettoZakup", $scope.koszyk.wartoscAllNetto);
                            				$cookieStore.put("koszyksumaPodatkuVatZakup", $scope.koszyk.sumaPodatkuVat);
                            				$scope.koszykLista.splice($scope.koszykLista.indexOf(item), 1);
                            				$cookieStore.put('koszykListaZakup', $scope.koszykLista);
                            			};
                            			
                            			
                            			$scope.recalculateForm = function(item,idx) {
                             				var koszykListaOld = $cookieStore.get('koszykListaZakup');
                            				var iloscOld = koszykListaOld[idx].ilosc;
                            				var iloscNew = item.ilosc;
                            				var ilosc = iloscNew - iloscOld;
                            				
                            				$scope.koszyk.iloscAll += ilosc;
                            				$scope.koszyk.wartoscAllBrutto += item.cbrutto * ilosc;
                            				$scope.koszyk.wartoscAllNetto += item.cnetto * ilosc;
                            				$scope.koszyk.sumaPodatkuVat += (item.cnetto * (item.stawka_vat/100)) * ilosc;
                            				
                            				$cookieStore.put("koszykIloscAllZakup", $scope.koszyk.iloscAll);
                            				$cookieStore.put("koszykwartoscAllBruttoZakup", $scope.koszyk.wartoscAllBrutto);
                            				$cookieStore.put("koszykwartoscAllNettoZakup", $scope.koszyk.wartoscAllNetto);
                            				$cookieStore.put("koszyksumaPodatkuVatZakup", $scope.koszyk.sumaPodatkuVat);
                            				
                            				$scope.koszykLista[idx] = item;
                            				$cookieStore.put("koszykLista", $scope.koszykLista);
                            			};
                            			
                            			$scope.submitOrder = function() {
                            				
                            				if(!$cookieStore.get('loggedIn')){
                            					alert('Aby zamówić musisz byc zalogowany.');
                            					$location.path('/logowanie');
                            				}
                            				
                             				var zamowienieData = {
                             						lista : JSON.stringify($scope.koszykLista),
                             						czyFaktura : $scope.zamowienie_faktuaVat,
                             						wiad_do_sprz: $scope.zamowienie_wiad_do_sprzedawcy,
                             						adres_dostawy : $scope.zamowienie_adres_dostawy,
                             						dane_do_faktury: $scope.zamowienie_dane_do_faktury,
                             						koszyk_info: JSON.stringify($scope.koszyk),
                             						id_zamawiajacego : $cookieStore.get('loggedId')
                             				}
                            			
                             				$http.post('/smbcustsrv/rest/command/zamowienie/addNewOrder', zamowienieData)
                            				.success(function(data) {
                            					alert('zamowienie dodane !');
                            					clearOferta();
                            				})
                            				
                            				
                            			};
                            			
                            			
                            			$scope.clearFormAndSession = function() {
                            				clearOferta();
                            			};
                            		
                            			function initScopeFromSession(){
                            				$rootScope.logged = $cookieStore.get("loggedIn");
                            				
                            				var koszykIloscAll = $cookieStore.get('koszykIloscAllZakup');
                            				if(koszykIloscAll == null){
                            					koszykIloscAll = 0;
                            				}
                            				
                            				var koszykwartoscAllBrutto = $cookieStore.get('koszykwartoscAllBruttoZakup');
                            				if(koszykwartoscAllBrutto == null){
                            					koszykwartoscAllBrutto = 0;
                            				}
                            				
                            				var koszykwartoscAllNetto  = $cookieStore.get('koszykwartoscAllNettoZakup');
                            				if(koszykwartoscAllNetto  == null){
                            					koszykwartoscAllNetto  = 0;
                            				}
                            				
                            				var koszyksumaPodatkuVat  = $cookieStore.get('koszyksumaPodatkuVatZakup');
                            				if(koszyksumaPodatkuVat  == null){
                            					koszyksumaPodatkuVat  = 0;
                            				}
                            				
                            				$scope.koszyk = {
                            						iloscAll :  koszykIloscAll,
                            						wartoscAllBrutto : koszykwartoscAllBrutto,
                            						wartoscAllNetto: koszykwartoscAllNetto,
                            						sumaPodatkuVat: koszyksumaPodatkuVat
                            					}

                            			}
                            			$scope.clearFormAndSession = function() {
                            				clearOferta();
                            			};
                            		
                            			function initScopeFromSession(){
                            				$rootScope.logged = $cookieStore.get("loggedIn");
                            				
                            				var koszykIloscAll = $cookieStore.get('koszykIloscAllZakup');
                            				if(koszykIloscAll == null){
                            					koszykIloscAll = 0;
                            				}
                            				
                            				var koszykwartoscAllBrutto = $cookieStore.get('koszykwartoscAllBruttoZakup');
                            				if(koszykwartoscAllBrutto == null){
                            					koszykwartoscAllBrutto = 0;
                            				}
                            				
                            				var koszykwartoscAllNetto  = $cookieStore.get('koszykwartoscAllNettoZakup');
                            				if(koszykwartoscAllNetto  == null){
                            					koszykwartoscAllNetto  = 0;
                            				}
                            				
                            				var koszyksumaPodatkuVat  = $cookieStore.get('koszyksumaPodatkuVatZakup');
                            				if(koszyksumaPodatkuVat  == null){
                            					koszyksumaPodatkuVat  = 0;
                            				}
                            				
                            				$scope.koszyk = {
                            						iloscAll :  koszykIloscAll,
                            						wartoscAllBrutto : koszykwartoscAllBrutto,
                            						wartoscAllNetto: koszykwartoscAllNetto,
                            						sumaPodatkuVat: koszyksumaPodatkuVat
                            					}

                            			}
                            			
                            function clearOferta(){
                            	$scope.koszyk.iloscAll = 0;
                            	$scope.koszyk.wartoscAllBrutto = 0;
                            	$scope.koszyk.wartoscAllNetto = 0;
                            	$scope.koszyk.sumaPodatkuVat = 0;
                            	$scope.koszykLista = [];
                            	
                            	$scope.zamowienie_adres_dostawy ="";
                            	$scope.zamowienie_dane_do_faktury = "";
                            	$scope.zamowienie_faktuaVat = false;
                            	$scope.zamowienie_wiad_do_sprzedawcy = "";
                            	
                            	$cookieStore.put("koszykListaZakup", $scope.koszykLista);
                            	$cookieStore.put("koszykIloscAllZakup", $scope.koszyk.iloscAll);
                            	$cookieStore.put("koszykwartoscAllBruttoZakup", $scope.koszyk.wartoscAllBrutto);
                            	$cookieStore.put("koszykwartoscAllNettoZakup", $scope.koszyk.wartoscAllNetto);
                            	$cookieStore.put("koszyksumaPodatkuVatZakup", $scope.koszyk.sumaPodatkuVat);
                            }
                               			
                               			
                               		}]);




app.controller("dodajSprzedController", [
                               		'$scope',
                               		'$http',
                               		'$location',
                               		'$rootScope',
                               		'$cookieStore',
                               		'$route',
                               		'$routeParams',
                               		function($scope, $http, $location, $rootScope, $cookieStore, $route, $routeParams) {
                               			$rootScope.pageTitle = 'Sprzedaz - dodaj';
                               			$rootScope.showKat = false;
                               			$rootScope.logged = $cookieStore.get("loggedIn");
                               			$rootScope.showPrac = $cookieStore.get("isPrac");
                               			$rootScope.showAdmin = $cookieStore.get("isAdm");
                               			
                               			var postData = {};
                               			
                               			$http.post('/smbemplsrv/rest/query/produkt/getProdukty', postData)
                    					.success(function(data) {
                    								$scope.towary = data;
                    							});

                               			initScopeFromSession();
                               			$scope.koszykLista = $cookieStore.get('koszykListaSprzedaz');
                               			
                            			$scope.addToList = function(towar) {
                            				$scope.koszyk.iloscAll += 1;
                            				$scope.koszyk.wartoscAllBrutto += towar.cbrutto;
                            				$scope.koszyk.wartoscAllNetto += towar.cnetto;
                            				$scope.koszyk.sumaPodatkuVat += (towar.cnetto * (towar.stawka_vat/100));

                            				$cookieStore.put("koszykIloscAllSprzedaz", $scope.koszyk.iloscAll);
                            				$cookieStore.put("koszykwartoscAllBruttoSprzedaz", $scope.koszyk.wartoscAllBrutto);
                            				$cookieStore.put("koszykwartoscAllNettoSprzedaz", $scope.koszyk.wartoscAllNetto);
                            				$cookieStore.put("koszyksumaPodatkuVatSprzedaz", $scope.koszyk.sumaPodatkuVat);

                            				var koszykLista = $cookieStore.get('koszykListaSprzedaz');
                            				if (koszykLista == null) {
                            					koszykLista = [];
                            				}
                            				var isContains = false;

                            				for (var i = 0; i < koszykLista.length; i++) {
                            					if (koszykLista[i].id == towar.id) {
                            						isContains = true;
                            						koszykLista[i].ilosc += 1;
                            						break;
                            					}
                            				}
                            				if (!isContains) {
                            					koszykLista.push({
                            						id : towar.id,
                            						ilosc : 1,
                            						jednostka: towar.jednostka,
                            						nazwa : towar.nazwa,
                            						cbrutto : towar.cbrutto,
                            						cnetto : towar.cnetto,
                            						stawka_vat : towar.stawka_vat
                            					});
                            				}
                            				$scope.koszykLista=koszykLista;
                            				$cookieStore.put('koszykListaSprzedaz', koszykLista);
                            			};

                            			$scope.goToCartView = function() {
                            				$location.path("/cartView");
                            			};
                            			
                            			
                            			$scope.removeElement = function(item) {
                            				$scope.koszyk.iloscAll -= parseInt(item.ilosc);
                            				$scope.koszyk.wartoscAllBrutto -= item.cbrutto * item.ilosc;
                            				$scope.koszyk.wartoscAllNetto -= item.cnetto * item.ilosc;
                            				$scope.koszyk.sumaPodatkuVat -= (item.cnetto * (item.stawka_vat/100)) * parseInt(item.ilosc);
                            				
                            				$cookieStore.put("koszykIloscAllSprzedaz", $scope.koszyk.iloscAll);
                            				$cookieStore.put("koszykwartoscAllBruttoSprzedaz", $scope.koszyk.wartoscAllBrutto);
                            				$cookieStore.put("koszykwartoscAllNettoSprzedaz", $scope.koszyk.wartoscAllNetto);
                            				$cookieStore.put("koszyksumaPodatkuVatSprzedaz", $scope.koszyk.sumaPodatkuVat);
                            				$scope.koszykLista.splice($scope.koszykLista.indexOf(item), 1);
                            				$cookieStore.put('koszykListaSprzedaz', $scope.koszykLista);
                            			};
                            			
                            			
                            			$scope.recalculateForm = function(item,idx) {
                             				var koszykListaOld = $cookieStore.get('koszykListaSprzedaz');
                            				var iloscOld = koszykListaOld[idx].ilosc;
                            				var iloscNew = item.ilosc;
                            				var ilosc = iloscNew - iloscOld;
                            				$scope.koszyk.wartoscAllBrutto += item.cbrutto * ilosc;
                            				$scope.koszyk.wartoscAllNetto += item.cnetto * ilosc;
                            				$scope.koszyk.sumaPodatkuVat += (item.cnetto * (item.stawka_vat/100)) * ilosc;
                            				
                            				$cookieStore.put("koszykIloscAllSprzedaz", $scope.koszyk.iloscAll);
                            				$cookieStore.put("koszykwartoscAllBruttoSprzedaz", $scope.koszyk.wartoscAllBrutto);
                            				$cookieStore.put("koszykwartoscAllNettoSprzedaz", $scope.koszyk.wartoscAllNetto);
                            				$cookieStore.put("koszyksumaPodatkuVatSprzedaz", $scope.koszyk.sumaPodatkuVat);
                            				
                            				
                            				$scope.koszykLista[idx] = item;
                            				$cookieStore.put("koszykListaSprzedaz", $scope.koszykLista);
                            			};
                            			
                            			$scope.submitSell = function() {
                             				var sprzedazData = {
                             						lista : JSON.stringify($scope.koszykLista),
                             						czyFaktura : $scope.zamowienie_faktuaVat,
                             						dane_do_faktury: $scope.zamowienie_dane_do_faktury,
                             						id_pracownika : $cookieStore.get('loggedId')
                             				}
                            			
                             				$http.post('/smbemplsrv/rest/command/sprzedaz/addNew', sprzedazData)
                            				.success(function(data) {
                            					clearOferta();
                            				})
                            				
                            				
                            			};
                            			
                            			
                            			$scope.clearFormAndSession = function() {
                            				clearOferta();
                            			};
                            		
                            			function initScopeFromSession(){
                            				$rootScope.logged = $cookieStore.get("loggedIn");
                            				
                            				var koszykIloscAll = $cookieStore.get('koszykIloscAllSprzedaz');
                            				if(koszykIloscAll == null){
                            					koszykIloscAll = 0;
                            				}
                            				
                            				var koszykwartoscAllBrutto = $cookieStore.get('koszykwartoscAllBruttoSprzedaz');
                            				if(koszykwartoscAllBrutto == null){
                            					koszykwartoscAllBrutto = 0;
                            				}
                            				
                            				var koszykwartoscAllNetto  = $cookieStore.get('koszykwartoscAllNettoSprzedaz');
                            				if(koszykwartoscAllNetto  == null){
                            					koszykwartoscAllNetto  = 0;
                            				}
                            				
                            				var koszyksumaPodatkuVat  = $cookieStore.get('koszyksumaPodatkuVatSprzedaz');
                            				if(koszyksumaPodatkuVat  == null){
                            					koszyksumaPodatkuVat  = 0;
                            				}
                            				
                            				$scope.koszyk = {
                            						iloscAll :  koszykIloscAll,
                            						wartoscAllBrutto : koszykwartoscAllBrutto,
                            						wartoscAllNetto: koszykwartoscAllNetto,
                            						sumaPodatkuVat: koszyksumaPodatkuVat
                            					}

                            			}
                            			$scope.clearFormAndSession = function() {
                            				clearOferta();
                            			};
                            		
                            			function initScopeFromSession(){
                            				$rootScope.logged = $cookieStore.get("loggedIn");
                            				
                            				var koszykIloscAll = $cookieStore.get('koszykIloscAllSprzedaz');
                            				if(koszykIloscAll == null){
                            					koszykIloscAll = 0;
                            				}
                            				
                            				var koszykwartoscAllBrutto = $cookieStore.get('koszykwartoscAllBruttoSprzedaz');
                            				if(koszykwartoscAllBrutto == null){
                            					koszykwartoscAllBrutto = 0;
                            				}
                            				
                            				var koszykwartoscAllNetto  = $cookieStore.get('koszykwartoscAllNettoSprzedaz');
                            				if(koszykwartoscAllNetto  == null){
                            					koszykwartoscAllNetto  = 0;
                            				}
                            				
                            				var koszyksumaPodatkuVat  = $cookieStore.get('koszyksumaPodatkuVatSprzedaz');
                            				if(koszyksumaPodatkuVat  == null){
                            					koszyksumaPodatkuVat  = 0;
                            				}
                            				
                            				$scope.koszyk = {
                            						iloscAll :  koszykIloscAll,
                            						wartoscAllBrutto : koszykwartoscAllBrutto,
                            						wartoscAllNetto: koszykwartoscAllNetto,
                            						sumaPodatkuVat: koszyksumaPodatkuVat
                            					}

                            			}
                            			
                            function clearOferta(){
                            	$scope.koszyk.iloscAll = 0;
                            	$scope.koszyk.wartoscAllBrutto = 0;
                            	$scope.koszyk.wartoscAllNetto = 0;
                            	$scope.koszyk.sumaPodatkuVat = 0;
                            	$scope.koszykLista = [];
                            	
                            	$scope.zamowienie_adres_dostawy ="";
                            	$scope.zamowienie_dane_do_faktury = "";
                            	$scope.zamowienie_faktuaVat = false;
                            	$scope.zamowienie_wiad_do_sprzedawcy = "";
                            	
                            	$cookieStore.put("koszykListaSprzedaz", $scope.koszykLista);
                            	$cookieStore.put("koszykIloscAllSprzedaz", $scope.koszyk.iloscAll);
                            	$cookieStore.put("koszykwartoscAllBruttoSprzedaz", $scope.koszyk.wartoscAllBrutto);
                            	$cookieStore.put("koszykwartoscAllNettoSprzedaz", $scope.koszyk.wartoscAllNetto);
                            	$cookieStore.put("koszyksumaPodatkuVatSprzedaz", $scope.koszyk.sumaPodatkuVat);
                            }
                            
                               		}]);
