'use strict';

var UserController = function(UserService, SecurityService, MessageService, $scope, $routeParams, $filter) {
	
	$scope.user = {};
		
	$scope.isEditable = $routeParams.userName == undefined;
	
    $scope.getUserByName = function(userName) {
    	
    	MessageService.resetError();
    	
		UserService.getUserByName(userName).then(function(data){
			$scope.user = data;		
        }, function(errorMessage){
        	MessageService.setError(errorMessage);
        });
	};
    
    $scope.addUser = function(user) {
    	MessageService.resetError();
    	
    	$scope.user.dateOfBirth = $filter('date')($scope.dateOfBirth, "dd/MM/yyyy");
    	
    	if($scope.user.password != $scope.passwordConfirm){
    		MessageService.setError("Las contrasenas deben coincidir");
    	} else{
    		UserService.addUser(user).then(function(data){
    			$scope.user = data;
    			
    			SecurityService.login(user.userName, user.password).then(function(data){
    				window.location.href= '#workspace/' + $scope.user.homeFolder.id;
    	        },function(errorMessage){
    	        	MessageService.setError(errorMessage);
    	        });
            },function(errorMessage){
            	MessageService.setError(errorMessage);
            });    		
    	}
	};
	
	$scope.resetForm = function() {
		$scope.resetError();
		$scope.user = {};
	};	
			
	if (!$scope.isEditable){
		$scope.getUserByName($routeParams.userName);
	}
	
	MessageService.resetError();
};