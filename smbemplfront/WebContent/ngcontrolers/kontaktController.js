app.controller("kontaktController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		function($scope, $http, $location, $rootScope, $cookieStore, $route) {
			$rootScope.pageTitle = 'Kontakt';
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.showPrac = $cookieStore.get("isPrac");
			$rootScope.showAdmin = $cookieStore.get("isAdm");
			$rootScope.showKat = false;
			
			getZgloszeniaUzytkownika();
			$http.post('/smbcustsrv/rest/notificationcategory/getNotificationCategory').success(function(response){
				$scope.katkontakt = response;
			});
			
			
			
			$http.post('/smbcustsrv/rest/companyinfo/getCompanyInfo').success(function(response){
				initDaneFirmy(response);
			});
			
			$scope.showDetail = function(zgloszenie){
				
			}
			
			
			function initDaneFirmy(response){
				$scope.daneFirmy = {
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
					$scope.daneFirmy.adres1 = response.ulica + ' ' + response.nr_budynku;
				}
				
			}
			
			function getZgloszeniaUzytkownika(){
				var config = {
						id_uzytkownika : $cookieStore.get('loggedId'),
					}
				$http.post('/smbcustsrv/rest/notification/getNotyfication', config).success(function(response){
					$scope.zgloszenia = response;
				});
			}
			
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
                                			$rootScope.pageTitle = 'Zgłoszenie';
                                			$rootScope.logged = $cookieStore.get("loggedIn");
                                			getZgloszenieById();
                                			
                                			$scope.addActivity = function(){
                                				dodajAktywnoscDoZgloszenia();
                                			}
                                			
                                			function getZgloszenieById(){
                                				var config = {
                                        				id_zgloszenia : $routeParams.id_zgloszenia,
                                        			}
                                        			$http.post('/smbcustsrv/rest/notification/getNotificationById', config).success(function(response){
                                        				$scope.zgloszenieDetail = response;
                                        				$scope.aktywnosci = response.aktywnosci;
                                        			});
                                			}
                                			
                                			function dodajAktywnoscDoZgloszenia(){
                                				var config = {
                                        				tresc : $scope.tresc_wiadomosci,
                                        				id_zgloszenia: $routeParams.id_zgloszenia,
                                        				id_uzytkownika: $cookieStore.get('loggedId')
                                        			}
                                        			$http.post('/smbcustsrv/rest/notification/addMessageToNotification', config).success(
                                        					function(response){
                                        						getZgloszenieById();
                                        						$scope.tresc_wiadomosci = "";
                                        			});
                                			}
                                			
                                			
                                		}]);
app.directive('zgloszeniatable',[function() {
	 return {
		    templateUrl: "zgloszeniaTable.html"
		    };

}]);