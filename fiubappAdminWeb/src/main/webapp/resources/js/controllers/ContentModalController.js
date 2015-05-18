ContentModalController = function ($scope, $modalInstance, content) {

  $scope.content = content;
  $scope.types = ['Imagen', 'Video', 'Documento'];
  
  $scope.content.type = (content.type != undefined) ? content.type : $scope.types[0];  
  $scope.content.labels = (content.labels != undefined) ? content.labels : [];
  $scope.newLabel = {};
  
  $scope.addNewLabel = function(newLabel) {
		if(newLabel!=undefined){
			var label = {"name" : newLabel.name, "positionX" : newLabel.positionX, "positionY" : newLabel.positionY};
			$scope.content.labels.push(label);
			$scope.newLabel = {};
		}
	};
	
	$scope.removeLabel = function(index) {
		$scope.content.labels.splice(index,1);
	};
    
  $scope.ok = function () {  
	  $modalInstance.close($scope.content);
  };

  $scope.cancel = function () {
	  $modalInstance.dismiss('cancel');
  };
  
};