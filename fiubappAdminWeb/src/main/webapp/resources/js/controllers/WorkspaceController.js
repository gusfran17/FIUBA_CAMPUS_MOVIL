'use strict';

var WorkspaceController = function(FolderService, ContentService, MessageService, $scope, $routeParams, $modal) {
	
	// Folders:
	
	$scope.folder = "";
	
	$scope.getFolder = function(id) {
		MessageService.resetError();
		
		FolderService.getFolder(id).then(function(data){
			$scope.folder = data;
        }, function(errorMessage){
        	MessageService.setError(errorMessage);
        });
	};
	
	$scope.addFolder = function(newFolderName) {
		MessageService.resetError();
		
		var newFolder = {};
		newFolder.name = newFolderName;
		newFolder.parentId = $scope.folder.id;
		newFolder.type = "Folder";
		
		FolderService.addFolder(newFolder).then(function(data){
			$scope.newFolderName = '';
			$scope.create = false;
			$scope.getFolder($scope.folder.id);
        }, function(errorMessage){
        	MessageService.setError(errorMessage);
        });
	};
		
	$scope.createFolder = function() {
		$scope.create = true;
	};
	
	$scope.cancelFolder = function() {
		$scope.create = false;
		$scope.newFolderName = '';
	};
	
	$scope.getFolder($routeParams.folderId);
	
	$scope.create = false;
	
	// Contents
	
	$scope.contents = [];
	
	$scope.upload = false;
	
	$scope.getFileProps = function(file){
		var name = file.webkitRelativePath || file.name;
		var sizeValue, sizeUnit;
		if(file.size > 1024*1024){
			sizeValue = file.size / 1024 / 1024;
			sizeUnit = "Mb";
		} else{
			sizeValue = file.size / 1024;
			sizeUnit = "Kb";
		}
		return name + " (" + Math.round(sizeValue*100)/100 + " " + sizeUnit + " )"; 
	};
	
	$scope.cancelUpload = function() {
		$scope.upload = false;
		$scope.contents = [];
		$scope.files = [];
	};
	
	$scope.open = function ($index) {	    
		var modalInstance = $modal.open({
				templateUrl: 'webapp/workspace/content-modal',
				controller: ContentModalController,
				resolve: {
					content: function () {
						if ($scope.contents[$index] == undefined){
							$scope.contents[$index] = {};
						}
						return $scope.contents[$index];
					}
				}
		});
		modalInstance.result.then(function (content) {
				content.ready = (!!content.name && !!content.description) &&
				                ((content.type=='Imagen' && !!content.labels) ||
						         (content.type=='Documento' && !!content.title && !!content.author && !!content.amountOfPages) ||
					             (content.type=='Video' && !!content.format && !!content.quality && !!content.durationInSeconds));
				$scope.contents.splice($index, 1, content);
		  	}, function () {
		  		
	    });
	};
	
	function isReady(element, index, array) {
		return (element.ready);
	}
	
	$scope.allContentsReady = function(){
		return $scope.contents.every(isReady);
	};
	
	$scope.removeFile = function(index) {
		$scope.files.splice(index,1);
		$scope.contents.splice(index,1);
		$scope.upload = ($scope.files.length != 0);
	};
	
	$scope.setFiles = function(element) {
	    $scope.$apply(function(scope) {
	        $scope.files = [];
	        for (var i = 0; i < element.files.length; i++) {
	          $scope.files.push(element.files[i]);
	          $scope.contents.push({"ready": false});
	        }
	        $scope.upload = true;
	      });
	};
	    
	$scope.buildUploadingContents = function(){
		var uploadingContents = [];
	    for ( var i = 0; i < $scope.contents.length; i++) {
		   	var uploadingContent = {};
	    	switch ($scope.contents[i].type){
	    		case "Imagen":
	    			uploadingContent.content = buildImage($scope.contents[i]);
	                break;
	            case "Documento":
	            	uploadingContent.content = buildDocument($scope.contents[i]);
	                break;
	            case "Video":
	              	uploadingContent.content = buildVideo($scope.contents[i]);
	                break;
	    	}
	    	uploadingContent.file = $scope.files[i];
	    	uploadingContents.push(uploadingContent);
	    }
	    return uploadingContents;
	};
	    
	function buildImage(content){
		var image = {};
		image.parentId = $scope.folder.id;
	    image.type = "Image";
	    image.name = content.name;
	    image.description = content.description;
	    image.labels = content.labels;
	    return image;
	};
	    
	function buildDocument(content){
		var document = {};
		document.parentId = $scope.folder.id;
		document.type = "Document";
		document.name = content.name;
		document.description = content.description;
		document.title = content.title;
		document.author = content.author;
		document.amountOfPages = content.amountOfPages;
		return document;
	};
	    
	function buildVideo(content){
		var video = {};
		video.parentId = $scope.folder.id;
		video.type = "Video";
		video.name = content.name;
		video.description = content.description;
		video.quality = content.quality;
		video.format = content.format;
		video.durationInSeconds = content.durationInSeconds;
		return video;
	};
	    
	$scope.uploadFiles = function(){	
		MessageService.resetError();
		
		if($scope.allContentsReady()){
			
    		var readyContents = $scope.buildUploadingContents();
    		$scope.counter = 0;
    	    $scope.max = readyContents.length;
    	    
    		readyContents.forEach(function(readyContent) {
    			   		    
    			ContentService.addContent(readyContent.content).then(function(data){
						readyContent.content.id = data.id;
				        ContentService.addContentResource(readyContent.content.id, readyContent.file).then(function(data){
				           		$scope.incrementProgressBar();
				        		$scope.getFolder($scope.folder.id);
				        }, function(errorMessage){
				        		MessageService.setError(errorMessage);
				        });						
				}, function(errorMessage){
						MessageService.setError(errorMessage);
	    	    });
    		});
		}
	};
	    		
	$scope.incrementProgressBar = function(){
		$scope.counter = $scope.counter + 1;
	    $scope.dynamic = $scope.counter / $scope.max * 100;
	};
	
	// Publicacion
	
	$scope.openModalPublication = function ($index) {	    
		$modal.open({
				templateUrl: 'webapp/workspace/publication-modal',
				controller: PublicationModalController,
				resolve: {
					contentId: function () {
						return $scope.folder.children[$index].id;
					}
				}
		});
	};
	
	MessageService.resetError();
};