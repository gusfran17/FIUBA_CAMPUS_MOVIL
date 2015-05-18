PublicationModalController = function (PublicationService, $filter, $scope, $modalInstance, contentId) {
 
	$scope.publication = {};
	
	$scope.automaticPublication = {"checked": false, "publicationDate": new Date()};
	
	$scope.genderFilter = {"checked": false, "gender": "MALE"};
	$scope.rangeAgeFilter = {"checked": false};
	$scope.underAgeFilter = {"checked": false};
	$scope.overAgeFilter = {"checked": false};
	
	$scope.getAction = function(){
		if($scope.publication.state=="PUBLISHED" || $scope.publication.state=="PENDING"){
			return "cancellations";
		}
		if($scope.automaticPublication.checked){
			return "automatic-publications";
		};
		return "publications";
	};
	
	$scope.getPublication = function(contentId) {
		$scope.resetError();
		PublicationService.getPublicationByContentId(contentId).then(function(data){
			$scope.publication = data;
        }, function(errorMessage){
        	$scope.setError(errorMessage);
        });
	};
	
	$scope.buildPublicationRequest = function(){
		var publicationRequest = {"contentId": contentId};
		if($scope.automaticPublication.checked){
			publicationRequest.publicationDate = $filter('date')($scope.automaticPublication.publicationDate, "dd/MM/yyyy");
		}
		var filters = [];
		if ($scope.genderFilter.checked){
			var filter = {"type" : "GENDER", "gender": $scope.genderFilter.gender};
			filters.push(filter);
		}
		if ($scope.rangeAgeFilter.checked){
			var filter = {"type" : "AGE", "minAge": $scope.rangeAgeFilter.minAge, "maxAge": $scope.rangeAgeFilter.maxAge};
			filters.push(filter);
		}
		if ($scope.underAgeFilter.checked){
			var filter = {"type" : "UNDER_AGE", "minAge": $scope.underAgeFilter.minAge};
			filters.push(filter);
		}
		if ($scope.overAgeFilter.checked){
			var filter = {"type" : "OVER_AGE", "maxAge": $scope.overAgeFilter.maxAge};
			filters.push(filter);
		}
		
		publicationRequest.filters = filters;
		return publicationRequest;
	};
	
	$scope.updatePublication = function() {
		$scope.resetError();
		PublicationService.updatePublication($scope.getAction(), $scope.buildPublicationRequest()).then(function(data){
			$scope.getPublication(contentId);
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
  
	$scope.getPublication(contentId);
  
};