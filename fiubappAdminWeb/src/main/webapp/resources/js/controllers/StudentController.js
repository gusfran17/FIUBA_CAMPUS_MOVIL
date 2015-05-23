'use strict';

var StudentController = function(StudentService, MessageService, SearchStorageService, $scope) {

	$scope.previousSearchs = SearchStorageService.getAll();
	$scope.searchParams = {};
		
	$scope.states = ['Todos', 'Pendiente', 'Habilitado', 'Suspendido'];
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
		
		StudentService.searchStudents(path).then(function(data){
			$scope.results = data;
			SearchStorageService.push(searchParams, $scope.results);
			$scope.previousSearchs = SearchStorageService.getAll();				
        }, function(errorMessage){
        	MessageService.setError(errorMessage);
        });
	};
			
	$scope.getParamsPath = function(searchParams){
		var params = [];
		//$scope.addParamToArray(params, "state", searchParams.state);
		$scope.addParamToArray(params, "fileNumber", searchParams.fileNumber);
		$scope.addParamToArray(params, "passportNumber", searchParams.passportNumber);
		$scope.addParamToArray(params, "name", searchParams.name);
		$scope.addParamToArray(params, "lastName", searchParams.lastName);
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
	
	$scope.getPreviousSearch = function(selectedSearch){
		$scope.searchParams = $scope.previousSearchs[selectedSearch].params;
		$scope.results = $scope.previousSearchs[selectedSearch].results;
	}
	
	MessageService.resetError();
};