'use strict';

var ContentController = function(ContentService, MessageService, $scope, $routeParams) {
	
	$scope.content = {};	
	$scope.type="";
	
	$scope.isEditable = false;
	
	$scope.getContent = function() {
		
		ContentService.getContent($routeParams.contentId).then(function(data){
			$scope.content = data;
        	if($scope.content.type=='Image'){
        		$scope.type="Imagen";
        	}
        	if($scope.content.type=='Document'){
        		$scope.type="Documento";
        	}
        	if($scope.content.type=='Video'){
        		$scope.type="Video";
        	}
        }, function(errorMessage){
        	MessageService.setError(errorMessage);
        });
	};
			
	MessageService.resetError();
	$scope.getContent();
};