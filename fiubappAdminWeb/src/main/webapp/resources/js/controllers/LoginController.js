'use strict';

function LoginController(SecurityService, MessageService, $scope, $location) {
			
    $scope.login = function() {
		MessageService.resetError();
		
		SecurityService.login($scope.username, $scope.password).then(function(data){
			window.location.href= '#/searchs';
        },
        function(errorMessage){
        	MessageService.setError(errorMessage);
        });
	};
}