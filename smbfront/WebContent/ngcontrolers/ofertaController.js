app.controller("ofertaController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore, $route, $routeParams) {
			if($location.path() == '/oferta'){
				$rootScope.pageTitle = 'Oferta';
				$rootScope.showKat = true;
			}else{
				var config = {
						id_uzytkownika : $cookieStore.get('loggedId'),
					}
					$http.post('/smbcustsrv/rest/query/uzytkownik/getUzytkownikaById', config).success(function(response){
						initAdresDostawy(response);
						initDaneFaktury(response);
					});
				
				$rootScope.pageTitle = 'Koszyk';
				$rootScope.showKat = false;
			}
			
			initScopeFromSession();
			
			$scope.koszykLista = $cookieStore.get('koszykLista');

			var postData = {
				kategoria : $routeParams.katId,
			}
			$http.post('/smbcustsrv/rest/query/oferta/getOferta', postData)
					.success(function(data) {
						$scope.itemOnPage = 5;
						$scope.currentPage = 0;
						var countOfPages = Math.ceil(data.length / $scope.itemOnPage);
						var pages = [];
						for (var j = 0; j < countOfPages; j++) {
							pages.push({
								start: j * $scope.itemOnPage,
							});
						}
						
						$scope.pages = pages;
						$scope.towary = data;
						
					})

			$scope.changePage = function(idx,page) {
				$scope.currentPage = idx;
			}
					
			$scope.addToCart = function(towar) {
				$scope.koszyk.iloscAll += parseInt(towar.ilosc);
				$scope.koszyk.wartoscAllBrutto += towar.cbrutto * towar.ilosc;
				$scope.koszyk.wartoscAllNetto += towar.cnetto * towar.ilosc;
				$scope.koszyk.sumaPodatkuVat += (towar.cnetto * (towar.stawka_vat/100)) * parseInt(towar.ilosc);

				$cookieStore.put("koszykIloscAll", $scope.koszyk.iloscAll);
				$cookieStore.put("koszykwartoscAllBrutto", $scope.koszyk.wartoscAllBrutto);
				$cookieStore.put("koszykwartoscAllNetto", $scope.koszyk.wartoscAllNetto);
				$cookieStore.put("koszyksumaPodatkuVat", $scope.koszyk.sumaPodatkuVat);

				var koszykLista = $cookieStore.get('koszykLista');
				if (koszykLista == null) {
					koszykLista = [];
				}
				var isContains = false;

				for (var i = 0; i < koszykLista.length; i++) {
					if (koszykLista[i].id == towar.id) {
						isContains = true;
						koszykLista[i].ilosc += towar.ilosc;
						break;
					}
				}
				if (!isContains) {
					koszykLista.push({
						id : towar.id,
						ilosc : towar.ilosc,
						nazwa : towar.nazwa,
						jednostka : towar.jednostka,
						cbrutto : towar.cbrutto,
						cnetto : towar.cnetto,
						stawka_vat : towar.stawka_vat
					});
				}

				$cookieStore.put('koszykLista', koszykLista);
			};

			$scope.goToCartView = function() {
				$location.path("/cartView");
			};
			
			
			$scope.removeElement = function(item) {
				$scope.koszyk.iloscAll -= parseInt(item.ilosc);
				$scope.koszyk.wartoscAllBrutto -= item.cbrutto * item.ilosc;
				$scope.koszyk.wartoscAllNetto -= item.cnetto * item.ilosc;
				$scope.koszyk.sumaPodatkuVat -= (item.cnetto * (item.stawka_vat/100)) * parseInt(item.ilosc);
				
				$cookieStore.put("koszykIloscAll", $scope.koszyk.iloscAll);
				$cookieStore.put("koszykwartoscAllBrutto", $scope.koszyk.wartoscAllBrutto);
				$cookieStore.put("koszykwartoscAllNetto", $scope.koszyk.wartoscAllNetto);
				$cookieStore.put("koszyksumaPodatkuVat", $scope.koszyk.sumaPodatkuVat);
				$scope.koszykLista.splice($scope.koszykLista.indexOf(item), 1);
				$cookieStore.put('koszykLista', koszykLista);
			};
			
			
			$scope.recalculateForm = function(item,idx) {
 				var koszykListaOld = $cookieStore.get('koszykLista');
				var iloscOld = koszykListaOld[idx].ilosc;
				var iloscNew = item.ilosc;
				var ilosc = iloscNew - iloscOld;
				
				$scope.koszyk.iloscAll += ilosc;
				$scope.koszyk.wartoscAllBrutto += item.cbrutto * ilosc;
				$scope.koszyk.wartoscAllNetto += item.cnetto * ilosc;
				$scope.koszyk.sumaPodatkuVat += (item.cnetto * (item.stawka_vat/100)) * ilosc;
				
				$cookieStore.put("koszykIloscAll", $scope.koszyk.iloscAll);
				$cookieStore.put("koszykwartoscAllBrutto", $scope.koszyk.wartoscAllBrutto);
				$cookieStore.put("koszykwartoscAllNetto", $scope.koszyk.wartoscAllNetto);
				$cookieStore.put("koszyksumaPodatkuVat", $scope.koszyk.sumaPodatkuVat);
				
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
				
				var koszykIloscAll = $cookieStore.get('koszykIloscAll');
				if(koszykIloscAll == null){
					koszykIloscAll = 0;
				}
				
				var koszykwartoscAllBrutto = $cookieStore.get('koszykwartoscAllBrutto');
				if(koszykwartoscAllBrutto == null){
					koszykwartoscAllBrutto = 0;
				}
				
				var koszykwartoscAllNetto  = $cookieStore.get('koszykwartoscAllNetto');
				if(koszykwartoscAllNetto  == null){
					koszykwartoscAllNetto  = 0;
				}
				
				var koszyksumaPodatkuVat  = $cookieStore.get('koszyksumaPodatkuVat');
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
			
			function initAdresDostawy(response){
				$scope.zamowienie_adres_dostawy = response.street + ' ' + response.number_house;
				if(response.number_flat !== undefined){
					$scope.zamowienie_adres_dostawy += ' / '+ response.number_flat;
				}
				$scope.zamowienie_adres_dostawy +='\n' + response.post_code + ' ' + response.city;
			}
			
			function initDaneFaktury(response){
				if(response.company_name !== undefined){
					$scope.zamowienie_dane_do_faktury = response.company_name + '\n' + response.nip + '\n';
				}else{
					$scope.zamowienie_dane_do_faktury = response.name + ' ' + response.surname + '\n';
				}
				
				$scope.zamowienie_dane_do_faktury += response.street + ' ' + response.number_house;
				if(response.number_flat !== undefined){
					$scope.zamowienie_dane_do_faktury += ' / '+ response.number_flat;
				}
				$scope.zamowienie_dane_do_faktury +='\n' + response.post_code + ' ' + response.city;
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
	
	$cookieStore.put("koszykLista", $scope.koszykLista);
	$cookieStore.put("koszykIloscAll", $scope.koszyk.iloscAll);
	$cookieStore.put("koszykwartoscAllBrutto", $scope.koszyk.wartoscAllBrutto);
	$cookieStore.put("koszykwartoscAllNetto", $scope.koszyk.wartoscAllNetto);
	$cookieStore.put("koszyksumaPodatkuVat", $scope.koszyk.sumaPodatkuVat);
}			
		} ]);


app.filter('offset', function () {
    return function (input, offset) {
        return (input instanceof Array) 
          ? input.slice(+offset) 
          : input
    }
})


