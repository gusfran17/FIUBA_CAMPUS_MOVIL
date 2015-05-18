'use strict';

/* Services */

var AppServices = angular.module('FiubappWebAdminApp.services', []);

AppServices.service('UserService', function($http, $q) {
    
	this.getUserByName = function(userName) {
    	
    	var deferred = $q.defer();
    	
    	$http.get('api/users?userName='+ userName).success(function(data){
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
    	return deferred.promise;
    };
    
    this.addUser = function(user) {
    	
    	var deferred = $q.defer();
    	
    	$http.post('api/users', user).success(function(data) {
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	    	
    	return deferred.promise;
    };
});

AppServices.service('SecurityService', function($http, $q) {
    
	this.getLoggedUser = function() {
    	
		var logged = localStorage.getItem("token")!=null
    	var loggedUser = {"logged": logged};
    	    	   	
    	return loggedUser;
    };
    
    this.login = function(userName, password) {
    	    	
    	var credentials = {"userName": userName, "password": password, "isExchangeStudent": false};
    	
    	var deferred = $q.defer();
    	
    	$http.post('http://localhost:8080/fiubappREST/api/sessions/students', credentials).success(function(data) {
            deferred.resolve(data);
            localStorage.setItem("token", data.token);
    	}).success(function(data, status, headers, config) {
			deferred.resolve(data);
	    }).error(function(data, status, headers, config){
	    	deferred.reject("Nombre de usuario inexistente o contrasena incorrecta.");
	    });
    	    	
    	return deferred.promise;
    };
    
	this.logout = function() {
    	
		localStorage.removeItem("token")
    };
});

AppServices.service('FolderService', function($http, $q) {
    
	this.getFolder = function(id) {
    	
    	var deferred = $q.defer();
    	
    	$http.get('api/library/folders/'+id).success(function(data){
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
    	return deferred.promise;
    };
    
    this.addFolder = function(folder) {
    	
    	var deferred = $q.defer();
    	
    	$http.post('api/library/folders', folder).success(function(data) {
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	    	
    	return deferred.promise;
    };
});

AppServices.service('ContentService', function($http, $q) {
	
	this.getContent = function(id) {
    	
    	var deferred = $q.defer();
    	
    	$http.get('api/library/contents/'+ id).success(function(data){
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
    	return deferred.promise;
    };
        
    this.addContent = function(content) {
    	
    	var deferred = $q.defer();
    	
    	$http.post('api/library/contents', content).success(function(data) {
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	    	
    	return deferred.promise;
    };
    
    this.addContentResource = function(id, file) {
    	
    	var deferred = $q.defer();
     	
    	var fd = new FormData();
        fd.append("file", file);
        $http.post('api/library/contents/resources/' + id, fd, {
        		headers: { 'Content-Type': undefined},
        		transformRequest: angular.identity
        	}).success(function (data) {
        		console.log("File for content id " + id + " successfuly uploaded.");
        		deferred.resolve(data);
        	}).error(function (data) {
        		console.log("Error while uploading file for content id " + id + ". Code: " + data.code + " - Message: " + data.message);
        		deferred.reject(data.message);
        });
    	    	
    	return deferred.promise;
    };
});

AppServices.service('StudentService', function($http, $q) {
    
	this.searchStudents = function(searchPath) {
    	
    	var deferred = $q.defer();
    	
    	var req = {
    			method: 'GET',
    			url: 'http://localhost:8080/fiubappREST/api/students' + searchPath,
    			headers: {
    				'Authorization': localStorage.getItem("token")
    			}
    	}

    	$http(req).success(function(data){
    	
    	//$http.get('http://localhost:8080/fiubappREST/api/students' + searchPath, headers: { 'Authorization': localStorage.getItem("token")}).success(function(data){
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
    	return deferred.promise;
    };
});

AppServices.service('PublicationService', function($http, $q) {
    
	this.getPublicationByContentId = function(contentId) {
    	
    	var deferred = $q.defer();
    	
    	$http.get('api/publications?contentId='+contentId).success(function(data){
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
    	return deferred.promise;
    };
    
    this.updatePublication = function(action, publication) {
    	
    	var deferred = $q.defer();
    	
    	$http.post('api/publications/' + action, publication).success(function(data) {
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	    	
    	return deferred.promise;
    };
    
	this.searchPublications = function(contentType, searchPath) {
    	
    	var deferred = $q.defer();
    	
    	$http.get('api/publications/contents' + contentType + searchPath).success(function(data){
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
    	return deferred.promise;
    };
});

AppServices.service('MessageService', function($rootScope, $q) {
    
	this.setError = function(message) {
		$rootScope.error = true;
		$rootScope.errorMessage = message;    	
    };
    
    this.resetError = function() {
		$rootScope.error = false;
		$rootScope.errorMessage = '';
	};
});

AppServices.service('SearchStorageService', function($rootScope) {
	
	this.push = function(params, results) {
		var searchs = (localStorage.getItem("searchs")==null) ? [] : JSON.parse(localStorage.getItem("searchs"));
		
		var date = new Date();		
		var search = {"date": date.toLocaleTimeString(), "params": params, "results": results};
		
		searchs.unshift(search);
		localStorage.setItem("searchs", JSON.stringify(searchs));
    };
    
    this.getAll = function(){
    	return (localStorage.getItem("searchs")==null) ? [] : JSON.parse(localStorage.getItem("searchs"));
    };
});