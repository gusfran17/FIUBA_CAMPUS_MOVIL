'use strict';

var GroupController = function(GroupService, SecurityService, MessageService, $scope, $routeParams, $filter, TabService, $modal) {
			
	TabService.reload('groupTab');
	
	$scope.searchParams = {};
	
	$scope.states = ['Todos', 'Habilitado', 'Suspendido'];
	$scope.searchParams.state = $scope.states[0];
	
	$scope.searchParams.labels=[];
				
	$scope.addNewLabel = function(newLabel) {
		if(newLabel!=undefined){
			$scope.searchParams.labels.push(newLabel);
			$scope.newLabel = '';
		}
	};
	
	$scope.removeLabel = function(index) {
		$scope.searchParams.labels.splice(index, 1);
	};
		
	$scope.resetForm = function() {
		$scope.searchParams = {};
		$scope.searchParams.state = $scope.states[0];
		$scope.searchParams.labels=[];
	};
	
	$scope.search = function(searchParams) {
				
		MessageService.resetError();
		
		var path = $scope.getParamsPath(searchParams);
		
		GroupService.searchGroups(path).then(function(data){
			$scope.results = data;
			SearchStorageService.push(searchParams, $scope.results);
			$scope.previousSearchs = SearchStorageService.getAll();				
        }, function(errorMessage){
        	MessageService.setError(errorMessage);
        });
	};
			
	$scope.getParamsPath = function(searchParams){
		var params = [];
		$scope.addParamToArray(params, "name", searchParams.name);
		if(searchParams.state!='Todos'){
			$scope.addParamToArray(params, "state", searchParams.state);
		}
		return $scope.buildParamsPath(params);
	};
	
	$scope.addParamToArray = function(params, paramName, param){
		if(param!='' && param!=undefined){
			params.push(paramName + "=" + param);
		}
	};
	
	$scope.buildParamsPath = function(params){
		var path="";
		if(params.length !=0){
			path += "?" + params[0];
			for (var i=1; i<params.length; i++) {
				path += "&" + params[i];
			}
		}
		return path;
	};
		
	$scope.openStateModal = function ($index) {	    
		$modal.open({
				templateUrl: 'webapp/group/state-modal',
				controller: GroupModalController,
				resolve: {
			        group: function () {
			        	return $scope.results[$index];
			        }
			    }
		});
	};
	
	MessageService.resetError();
};