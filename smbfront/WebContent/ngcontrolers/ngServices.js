var app = angular.module('smb', [ 'ngRoute', 'ngCookies', 'ui.bootstrap' ]);

app.service('CartService',['$cookieStore','$http','$routeParams','$rootScope','$location', function($cookieStore,$http,$routeParams,$rootScope,$location){
	
	this.recalculateForm = function(item,idx,scope) {
			var koszykListaOld = $cookieStore.get('koszykLista');
			var iloscOld = koszykListaOld[idx].ilosc;
			var iloscNew = item.ilosc;
			var ilosc = iloscNew - iloscOld;
			
			scope.koszyk.iloscAll += ilosc;
			scope.koszyk.wartoscAllBrutto += item.cbrutto * ilosc;
			scope.koszyk.wartoscAllNetto += item.cnetto * ilosc;
			scope.koszyk.sumaPodatkuVat += (item.cnetto * (item.stawka_vat/100)) * ilosc;
			
			$cookieStore.put("koszykIloscAll", scope.koszyk.iloscAll);
			$cookieStore.put("koszykwartoscAllBrutto", scope.koszyk.wartoscAllBrutto);
			$cookieStore.put("koszykwartoscAllNetto", scope.koszyk.wartoscAllNetto);
			$cookieStore.put("koszyksumaPodatkuVat", scope.koszyk.sumaPodatkuVat);
			
			scope.koszykLista[idx] = item;
			$cookieStore.put("koszykLista", scope.koszykLista);
		};
	
	this.removeElement = function(item,scope) {
		scope.koszyk.iloscAll -= parseInt(item.ilosc);
		scope.koszyk.wartoscAllBrutto -= item.cbrutto * item.ilosc;
		scope.koszyk.wartoscAllNetto -= item.cnetto * item.ilosc;
		scope.koszyk.sumaPodatkuVat -= (item.cnetto * (item.stawka_vat/100)) * parseInt(item.ilosc);
		
		$cookieStore.put("koszykIloscAll", scope.koszyk.iloscAll);
		$cookieStore.put("koszykwartoscAllBrutto", scope.koszyk.wartoscAllBrutto);
		$cookieStore.put("koszykwartoscAllNetto", scope.koszyk.wartoscAllNetto);
		$cookieStore.put("koszyksumaPodatkuVat", scope.koszyk.sumaPodatkuVat);
		scope.koszykLista.splice(scope.koszykLista.indexOf(item), 1);
		$cookieStore.put('koszykLista', koszykLista);
	};
	
	this.submitOrder = function(scope) {
		
		if(!$cookieStore.get('loggedIn')){
			alert('Aby zamówić musisz byc zalogowany.');
			$location.path('/logowanie');
		}
		
			var zamowienieData = {
					lista : JSON.stringify(scope.koszykLista),
					czyFaktura : scope.zamowienie_faktuaVat,
					wiad_do_sprz: scope.zamowienie_wiad_do_sprzedawcy,
					adres_dostawy : scope.zamowienie_adres_dostawy,
					dane_do_faktury: scope.zamowienie_dane_do_faktury,
					koszyk_info: JSON.stringify(scope.koszyk),
					id_zamawiajacego : $cookieStore.get('loggedId')
			}
			alert('1');
			$http.post('/smbcustsrv/rest/zamowienie/addNewOrder', zamowienieData)
		.success(function(data) {
			alert('zamowienie dodane !');
			_clearOferta(scope);
		})
		
		
	};
	
	
	this.clearOferta = function(scope){
		_clearOferta(scope);
}
	
	var _clearOferta = function(scope){
		scope.koszyk.iloscAll = 0;
	scope.koszyk.wartoscAllBrutto = 0;
	scope.koszyk.wartoscAllNetto = 0;
	scope.koszyk.sumaPodatkuVat = 0;
	scope.koszykLista = [];
	
	scope.zamowienie_adres_dostawy ="";
	scope.zamowienie_dane_do_faktury = "";
	scope.zamowienie_faktuaVat = false;
	scope.zamowienie_wiad_do_sprzedawcy = "";
	
	$cookieStore.put("koszykLista", scope.koszykLista);
	$cookieStore.put("koszykIloscAll", scope.koszyk.iloscAll);
	$cookieStore.put("koszykwartoscAllBrutto", scope.koszyk.wartoscAllBrutto);
	$cookieStore.put("koszykwartoscAllNetto", scope.koszyk.wartoscAllNetto);
	$cookieStore.put("koszyksumaPodatkuVat", scope.koszyk.sumaPodatkuVat);
}

this.addToCart = function(scope,towar){
	scope.koszyk.iloscAll += parseInt(towar.ilosc);
	scope.koszyk.wartoscAllBrutto += towar.cbrutto * towar.ilosc;
	scope.koszyk.wartoscAllNetto += towar.cnetto * towar.ilosc;
	scope.koszyk.sumaPodatkuVat += (towar.cnetto * (towar.stawka_vat/100)) * parseInt(towar.ilosc);

$cookieStore.put("koszykIloscAll", scope.koszyk.iloscAll);
$cookieStore.put("koszykwartoscAllBrutto", scope.koszyk.wartoscAllBrutto);
$cookieStore.put("koszykwartoscAllNetto", scope.koszyk.wartoscAllNetto);
$cookieStore.put("koszyksumaPodatkuVat", scope.koszyk.sumaPodatkuVat);

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
}

	this.getCommodities = function(scope){
	var postData = { kategoria : $routeParams.katId}
		$http.post('/smbcustsrv/rest/oferta/getOferta', postData)
				.success(function(data) {
					scope.itemOnPage = 5;
					scope.currentPage = 0;
					var countOfPages = Math.ceil(data.length / scope.itemOnPage);
					var pages = [];
					for (var j = 0; j < countOfPages; j++) {
						pages.push({
							start: j * scope.itemOnPage,
						});
					}
					
					scope.pages = pages;
					scope.towary = data;
				})
	}
				
	this.initScopeFromSession = function(scope){
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
		
		scope.koszyk = {
				iloscAll :  koszykIloscAll,
				wartoscAllBrutto : koszykwartoscAllBrutto,
				wartoscAllNetto: koszykwartoscAllNetto,
				sumaPodatkuVat: koszyksumaPodatkuVat
			}
		
		scope.koszykLista = $cookieStore.get('koszykLista');
	}
	
	
	this.initUserData = function(scope){
		var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
			}
			$http.post('/smbcustsrv/rest/user/getUserById', config).success(function(response){
				initAdresDostawy(response);
				initDaneFaktury(response);
			});
		
		function initAdresDostawy(response){
			scope.zamowienie_adres_dostawy = response.street + ' ' + response.number_house;
			if(response.number_flat !== undefined){
				scope.zamowienie_adres_dostawy += ' / '+ response.number_flat;
			}
			scope.zamowienie_adres_dostawy +='\n' + response.post_code + ' ' + response.city;
		}
		
		function initDaneFaktury(response){
			if(response.company_name !== undefined){
				scope.zamowienie_dane_do_faktury = response.company_name + '\n' + response.nip + '\n';
			}else{
				scope.zamowienie_dane_do_faktury = response.name + ' ' + response.surname + '\n';
			}
			
			scope.zamowienie_dane_do_faktury += response.street + ' ' + response.number_house;
			if(response.number_flat !== undefined){
				scope.zamowienie_dane_do_faktury += ' / '+ response.number_flat;
			}
			scope.zamowienie_dane_do_faktury +='\n' + response.post_code + ' ' + response.city;
		}
		
	}

}]);


app.service('OrderService',['$cookieStore','$http','$routeParams', function($cookieStore,$http,$routeParams){
	this.getOrders = function(scope){
		var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
				status : ''
			}
			$http.post('/smbcustsrv/rest/zamowienie/getOrders', config).success(function(response) {
						scope.zamowienia = response;
					});
	}
	
	
	this.addNewMessageToOrder = function(scope){
		var config = {
				tresc : scope.tresc_wiadomosci,
				id_zamowienia : $routeParams.id_zamowienia,
				id_zamawiajacego : $cookieStore.get('loggedId')
			}
			$http.post('/smbcustsrv/rest/zamowienie/addMessageToOrder',
					config).success(function(response) {
						var config = {
								id_zamowienia : $routeParams.id_zamowienia,
							}
							$http.post('/smbcustsrv/rest/zamowienie/getOrderById', config)
									.success(function(response) {
										scope.zamowienieDetail = response;
										scope.listaProd = JSON.parse(response.listaProd);
										scope.aktywnosci = response.aktywnosci;
									});
						scope.tresc_wiadomosci = "";
			});
	}
	
	this.getOrderById = function(scope){
		var config = {
				id_zamowienia : $routeParams.id_zamowienia,
			}
			$http.post('/smbcustsrv/rest/zamowienie/getOrderById', config)
					.success(function(response) {
						scope.zamowienieDetail = response;
						scope.listaProd = JSON.parse(response.listaProd);
						scope.aktywnosci = response.aktywnosci;
					});
	}

}]);


app.service('NotificationService',['$cookieStore','$http','$routeParams', function($cookieStore,$http,$routeParams){
	this.getKategorie = function(scope){
		$http.post('/smbcustsrv/rest/zgloszenia/getKategorie').success(function(response){
			scope.katkontakt = response;
			scope.idKategoriaZgl = '' + response[0].id
		});
	}
	
	this.getZgloszeniaUzytkownika = function(scope){
		var config = {id_uzytkownika : $cookieStore.get('loggedId')}
		$http.post('/smbcustsrv/rest/zgloszenia/getZgloszeniaUzytkownika', config).success(function(response){
			scope.zgloszenia = response;
		});
	}
	
	this.addNewNotification = function(scope){
		var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
				id_kategoria: scope.idKategoriaZgl,
				tresc: scope.tresc_zgloszenia,
				temat: scope.zgloszenie_temat
			}
		
		$http.post('/smbcustsrv/rest/zgloszenia/addNewNotification', config).success(function(response){
			this.getZgloszeniaUzytkownika(scope);
		});
	}
	
	this.addNewMessageNotification = function(scope){
		var config = {
  				tresc : scope.tresc_wiadomosci,
  				id_zgloszenia: $routeParams.id_zgloszenia,
  				id_uzytkownika: $cookieStore.get('loggedId')
  			}
  			$http.post('/smbcustsrv/rest/zgloszenia/addMessageToNotification', config).success(
  					function(response){
  						var config = {
  								id_zgloszenia : $routeParams.id_zgloszenia,
  							}
  							$http.post('/smbcustsrv/rest/zgloszenia/getZgloszenieById', config).success(function(response) {
  									scope.zgloszenieDetail = response;
  									scope.aktywnosci = response.aktywnosci;
  									});
  						scope.tresc_wiadomosci = "";
  			});
	}
	
	this.getNotificationById = function(scope){
		var config = {
				id_zgloszenia : $routeParams.id_zgloszenia,
			}
			$http.post('/smbcustsrv/rest/zgloszenia/getZgloszenieById', config).success(function(response) {
					scope.zgloszenieDetail = response;
					scope.aktywnosci = response.aktywnosci;
					});
	}

}]);

app.service('CompanyInfoService',['$http', function($http){
	this.getCompanyInfo = function(scope){
		$http.post('/smbcustsrv/rest/companyinfo/getCompanyInfo').success(function(response){
			_initDaneFirmy(scope, response);
		});
	}
	
	var _initDaneFirmy = function(scope, response){
		scope.daneFirmy = {
				nazwa: response.nazwa,
				nip: 'NIP: ' + response.nip,
				regon : 'REGON: ' + response.regon,
				krs: 'KRS: ' +  response.krs,
				adres1 : response.ulica + ' ' + response.nr_budynku + '/' + response.nr_lokalu,
				adres2 : response.kod_pocztowy + ' ' + response.miasto,
				telefon : response.telefon,
				email : response.email
		}
				if(response.nr_lokalu == undefined){
					scope.daneFirmy.adres1 = response.ulica + ' ' + response.nr_budynku;
				}
	}
}]);

app.service('InvoiceService',['$http','$cookieStore', function($http, $cookieStore){
	this.getInvoices = function(scope){
		var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
			}
			$http.post('/smbcustsrv/rest/invoice/getInvoices', config).success(function(response){
				scope.faktury = response;
			});
	}
	
	this.downloadPdf = function(faktura){
		var url = '/smbcustsrv/rest/invoice/downloadPdf?';
		url += 'idFaktury=' + faktura.id;
		window.location.replace(url);
	}
	
}]);

app.service('UserService',['$location','$http','$routeParams','$cookieStore', function($location, $http, $routeParams, $cookieStore){
	this.activateAccoint = function(){
		var dataObj = { code : $routeParams.code };
			$http.post('/smbcustsrv/rest/register/activeAccount', dataObj).success(
					function(data) {
						alert("Twoje konto jest aktywne, mozesz się zalogować.");
						$location.path("/logowanie");
					}).error(function(data) {
						alert('Blad serwera.');
			});
	}
	
	this.retObj = {};
	
	this.getUserById = function(scope){
		var config = {
				id_uzytkownika : $cookieStore.get('loggedId'),
			}
			$http.post('/smbcustsrv/rest/user/getUserById', config).success(function(response){
				if(response.company_name !== undefined){
							scope.user = response;
							scope.userCompany = true;
							scope.userPerson = false;
				}else{
					scope.user = response;
					scope.userCompany = false;
					scope.userPerson = true;
				}
			});
	}
	
	this.saveUserData = function(user){
		$http.post('/smbcustsrv/rest/user/saveUserData', user).success(function(response){
			alert('Dane poprawione');
		});
	}
	
	this.registerUser = function(customerType){
		var dataObj = _createDataObject(customerType);
		$http.post('/smbcustsrv/rest/register/registerUser', dataObj).success(
				function(data) {
					alert("Na twój email został wysłany link aktywacyjny.");
				}).error(function(data) {
					alert('Blad serwera.');
		});
	};
	
	var _createDataObject = function(customerType){
		var dataObject = {
				customer_type : customerType,
				companyName : $scope.register_namecompany,
				nip : $scope.register_nip,
				name : $scope.register_name,
				surname : $scope.register_surname,
				username : $scope.register_login,
				password : $scope.register_password,
				email : $scope.register_email,
				phone : $scope.register_phone,
				
				street : $scope.register_street,
				building_number : $scope.register_building_number,
				flat_number : $scope.register_flat_number,
				city : $scope.register_city,
				post_code : $scope.register_post_code,
				activationUri : $location.absUrl()
			};
	return dataObject;
}
	
	
}]);

app.service('CommodityCategoryService',['$rootScope','$http', function($rootScope, $http){
	this.getCommoditiesCattegory = function(){
		$http.post('/smbcustsrv/rest/commoditycategory/getAll').success(function(data){
			$rootScope.kategorieOferta = data;
		})
	}
}]);

app.service('RedirectService',['$location', function($location){
	this.redirectToRegister = function(){
		$location.path('/rejestracja_osoba');
	}
	
	this.reditectToWelcome = function(){
		$location.path('/welecomeUser');
	}
	
	this.redirectToLogin = function(){
		$location.path('/logowanie');
	}
	
	this.redirectToRegisterCompany = function(){
		$location.path('/rejestracja_firma');
	}
	
	this.redirectToRegisterPerson = function(){
		$location.path('/rejestracja_osoba');
	}
	this.redirectToNotificationDetail = function(zgloszenie){
		$location.path('/zgloszenieDetail').search({id_zgloszenia: zgloszenie.id});
	}
	this.redirectToOrderDetail = function(zamowienie) {
		$location.path('/zamowienieDetail').search({id_zamowienia : zamowienie.id});
	}
	this.redirectToCartView = function() {
		$location.path("/cartView");
	}

	
	
}]);

app.service('RootScopeVariable',['$rootScope','$cookieStore','$location', function($rootScope,$cookieStore,$location){
	this.init = function(pageTitle,showKat){
		$rootScope.pageTitle = pageTitle;
		$rootScope.logged = $cookieStore.get("loggedIn");
		$rootScope.showKat = showKat;
	}
}]);
	
app.service('AuthService',['$http','$cookieStore','$location','RedirectService', function($http, $cookieStore, $location, RedirectService){
	this.logIn = function(login, password){
		var dataObject = _createDataObject(login, password);
		$http.post('/smbcustsrv/rest/login/checkLoginData', dataObject).success(function(data) {
					if (data.login_result == true) {
						$cookieStore.put("loggedIn", true);
						$cookieStore.put("loggedName", dataObject.username);
						$cookieStore.put("loggedId", data.id_user);
						RedirectService.reditectToWelcome()
					}else if (data.login_result == "not_active") {
						alert('Konto nie zostało aktywowane.');
					}else{
						alert('Podano nieprawidłowy login lub hasło.');
					}
				}).error(function(data) {
					alert('Blad serwera.');
				});
	}
	
	this.logOut = function(){
		$cookieStore.remove("loggedIn");
		$cookieStore.remove("loggedName");
		$cookieStore.remove("loggedId");
		$location.path('/logowanie');
	}
	
	
	var _createDataObject = function(login, password){
			var dataObject = {
					username : login,
					password : password
				};
		return dataObject;
	}
	
}]);