'use strict';

function LogoutController(SecurityService, MessageService, $scope, $rootScope) {
		
	$scope.success = false;
	
    $scope.logout = function() {
    	MessageService.resetError();
		SecurityService.logout();
		$scope.success = true;
    };
	
	$scope.logout();
}