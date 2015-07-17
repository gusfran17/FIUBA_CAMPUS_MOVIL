GroupModalController = function (GroupService, $filter, $scope, $modalInstance, group) {
 
	$scope.group = group;
	
	$scope.suspendGroup = function() {
		$scope.resetError();
		GroupService.updateGroupState($scope.group.id, 'Suspendido').then(function(data){
			$scope.group.state = 'Suspendido';
			$modalInstance.close();
        }, function(errorMessage){
        	$scope.setError(errorMessage);
        });
	};
	
	$scope.approveGroup = function() {
		$scope.resetError();
		GroupService.updateGroupState($scope.group.id, 'Habilitado').then(function(data){
			$scope.group.state = 'Habilitado';
			$modalInstance.close();
        }, function(errorMessage){
        	$scope.setError(errorMessage);
        });
	};
	
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
	
	$scope.resetError = function() {
		$scope.error = false;
		$scope.errorMessage = '';
	};

	$scope.setError = function(message) {
		$scope.error = true;
		$scope.errorMessage = message;
	};
  
};