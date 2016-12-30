app.controller("produktController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore, $route, $routeParams) {
			
			$rootScope.showKS = false;
			$rootScope.showZgl = false;
			$rootScope.showAdm = false;
			$rootScope.showMag = true;
			
			$rootScope.logged = $cookieStore.get("loggedIn");

			var postData = {
				id_magazynu : $routeParams.magId,
			}

			$http.post('/smbemplsrv/rest/warehouseconf/getWarehouseById',
					postData).success(function(data) {
				$scope.magazyn = data;
				$rootScope.pageTitle = 'Obsługa magazynów - ' + data.name;
			})

			$http.post('/smbemplsrv/rest/commodity/getProdukty', postData)
					.success(
							function(data) {
								$scope.itemOnPage = 5;
								$scope.currentPage = 0;
								var countOfPages = Math.ceil(data.length
										/ $scope.itemOnPage);
								var pages = [];
								for (var j = 0; j < countOfPages; j++) {
									pages.push({
										start : j * $scope.itemOnPage,
									});
								}

								$scope.pages = pages;
								$scope.towary = data;

							})

			$scope.changePage = function(idx, page) {
				$scope.currentPage = idx;
			}

			$scope.goToAddProd = function() {
				$location.path("/dodajProdukt");
			};

			$scope.goToEditProd = function(prod) {
				$location.path("/edytujProdukt").search({
					prodId : prod.id
				});
			};

		} ]);

app.controller("dodajProduktController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore, $route,
				$routeParams) {
			$rootScope.showKat = true;
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.pageTitle = 'Obsługa magazynów - dodaj produkt';
			var postData = {
				id_magazynu : $routeParams.magId,
			}

			$http.post('/smbemplsrv/rest/warehouseconf/getWarehouseById',
					postData).success(function(data) {
				$scope.magazyn = data;
			})

			$http.post('/smbemplsrv/rest/commoditycategory/getAll').success(
					function(data) {
						$scope.katprod = data;
					})

			$scope.newProd = {
				id_magazynu : $routeParams.magId,
			}
			$scope.dodajProdAction = function() {
				$http.post('/smbemplsrv/rest/commodity/addNewProd',
						$scope.newProd)
			};
		} ]);

app.controller("edytujProduktController", [
		'$scope',
		'$http',
		'$location',
		'$rootScope',
		'$cookieStore',
		'$route',
		'$routeParams',
		function($scope, $http, $location, $rootScope, $cookieStore, $route,
				$routeParams) {
			$rootScope.showKat = true;
			$rootScope.logged = $cookieStore.get("loggedIn");
			$rootScope.pageTitle = 'Obsługa magazynów - edytuj produkt';

			$http.post('/smbemplsrv/rest/commoditycategory/getAll').success(
					function(data) {
						$scope.katprod = data;
					})

			var prod = {
				id : $routeParams.prodId,
			}

				
			
			$http.post('/smbemplsrv/rest/commodity/getProduktById', prod)
					.success(function(data) {
						$scope.prod = data;
					})
					
			$scope.edytujProdAction = function() {
				$http.post('/smbemplsrv/rest/commodity/editProd',
						$scope.prod)
			};
			
			
		} ]);

app.directive("fileread", [ function() {
	return {
		scope : {
			fileread : "="
		},
		link : function(scope, element, attributes) {
			element.bind("change", function(changeEvent) {
				var reader = new FileReader();
				reader.onload = function(loadEvent) {
					scope.$apply(function() {
						scope.fileread = loadEvent.target.result;
					});
				}
				reader.readAsDataURL(changeEvent.target.files[0]);
			});
		}
	}
} ]);

app.filter('offset', function() {
	return function(input, offset) {
		return (input instanceof Array) ? input.slice(+offset) : input
	}
})
