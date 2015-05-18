'use strict';

function LogoutController(SecurityService, MessageService, $scope, $rootScope) {
	
	$scope.success = false;
	
    $scope.logout = function() {
    	MessageService.resetError();
		SecurityService.logout().then(function(data){
			$rootScope.loggedUser.logged = false;
			$rootScope.loggedUser = SecurityService.getLoggedUser();
			$scope.success = true;
        });
	};
	
	$scope.logout();
}