app.controller("mainContoller",function($scope, AuthService, RedirectService, CommodityCategoryService) {
	CommodityCategoryService.getCommoditiesCattegory();
	
	$scope.wyloguj = function() {
		AuthService.logOut();
	};
			
	$scope.goToLoggin = function() {
		RedirectService.redirectToLogin();
	};
});

app.controller("accountActivationController", function($scope, RootScopeVariable, UserService) {
	RootScopeVariable.init('', false);
	
	$scope.activateAccount = function() {
		UserService.activateAccoint();
	};
	
})

app.controller("ustawieniaController",function($scope, RootScopeVariable, UserService) {
			RootScopeVariable.init('Ustawienia', false);
			UserService.getUserById($scope);
			
			$scope.saveForm = function(){
				UserService.saveUserData($scope.user);
			}
});

app.controller("kontaktController", function($scope, RedirectService, RootScopeVariable, NotificationService, CompanyInfoService) {
	RootScopeVariable.init('Kontakt', false);
	
	NotificationService.getZgloszeniaUzytkownika($scope);
	NotificationService.getKategorie($scope);
	
	CompanyInfoService.getCompanyInfo($scope);
	
	$scope.dodajZgloszenie = function(){
		NotificationService.addNewNotification($scope);
	}
	
	$scope.showDetail = function(zgloszenie){
		RedirectService.redirectToNotificationDetail(zgloszenie);
	}
	
});

app.controller("loginController", function($scope, $location, AuthService, RootScopeVariable, RedirectService) {
	RootScopeVariable.init('Logowanie', false);
		 
	$scope.loginSubmit = function() {
		AuthService.logIn($scope.login_username,$scope.login_password);
	};

	$scope.goToRegister = function() {
		RedirectService.redirectToRegister();
	};
});

app.controller("fakturaController", function($scope, RootScopeVariable, InvoiceService) {
	RootScopeVariable.init('Faktury',false);

	InvoiceService.getInvoices($scope);

	$scope.downloadPdf = function(faktura){
		InvoiceService.downloadPdf(faktura);
	}
	
});

app.controller("registerController", function($scope, RedirectService, RootScopeVariable, UserService) {
	RootScopeVariable.init('Rejestracja', false);
	$scope.registerPersonSubmit = function() {
		UserService.registerUser('P');
	};
	
	$scope.registerCompanySubmit = function() {
		UserService.registerUser('C');
	};
	
	$scope.goToRegPerson = function() {
		RedirectService.redirectToRegisterPerson();
	};
	
	$scope.goToRegCompany = function() {
		RedirectService.redirectToRegisterCompany();
	};
	
	$scope.goToLoggin = function() {
		RedirectService.redirectToLogin();
	};
});

app.controller("zgloszenieDetailController", function($scope, NotificationService, RootScopeVariable) {
	RootScopeVariable.init('Zgłoszenie', false);
	NotificationService.getNotificationById($scope);

	$scope.addActivity = function() {
		NotificationService.addNewMessageNotification($scope);
	}
});

app.controller("zamowienieController", function($scope, RedirectService, RootScopeVariable, OrderService) {
	RootScopeVariable.init('Zamówienia', false);
	OrderService.getOrders($scope);

	$scope.showDetail = function(zamowienie) {
		RedirectService.redirectToOrderDetail(zamowienie);
	}

});

app.controller("zamowienieDetailController", function($scope, RootScopeVariable, OrderService) {
	RootScopeVariable.init('Zamówienia', false);
	OrderService.getOrderById($scope);

	$scope.addActivity = function() {
		OrderService.addNewMessageToOrder($scope);
	}

});

app.controller("ofertaController",['$scope','$location','RootScopeVariable','CartService','RedirectService',function($scope,$location,RootScopeVariable,CartService,RedirectService) {
	if($location.path() == '/oferta'){
		RootScopeVariable.init('Oferta', true);
	}else{
		CartService.initUserData($scope);
		RootScopeVariable.init('Koszyk', false);
	}
	
	CartService.initScopeFromSession($scope);
	CartService.getCommodities($scope);


	$scope.changePage = function(idx,page) {
		$scope.currentPage = idx;
	}
			
	$scope.addToCart = function(towar) {
		CartService.addToCart($scope,towar);
	
	};

	$scope.goToCartView = function() {
		RedirectService.redirectToCartView();
		
	};
	
	$scope.removeElement = function(item) {
		CartService.removeElement(item,$scope);
	};
	
	
	$scope.recalculateForm = function(item,idx) {
			CartService.recalculateForm(item,idx,$scope);
	};
	
	$scope.submitOrder = function() {
		CartService.submitOrder($scope);
	};
	
	$scope.clearFormAndSession = function() {
		CartService.clearOferta($scope);
	};
	
}]);
