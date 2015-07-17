StateModalController = function (StudentService, $filter, $scope, $modalInstance, student) {
 
	$scope.student = student;
	
	$scope.suspendStudent = function() {
		$scope.resetError();
		StudentService.updateStudentState($scope.student.userName, 'Suspendido').then(function(data){
			$scope.student.state = 'Suspendido';
			$modalInstance.close();
        }, function(errorMessage){
        	$scope.setError(errorMessage);
        });
	};
	
	$scope.approveStudent = function() {
		$scope.resetError();
		StudentService.updateStudentState($scope.student.userName, 'Habilitado').then(function(data){
			$scope.student.state = 'Habilitado';
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