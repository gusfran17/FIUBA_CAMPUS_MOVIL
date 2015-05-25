'use strict';

/* Services */

var AppServices = angular.module('FiubappWebAdminApp.services', []);

AppServices.service('SecurityService', function($http, $q) {
    
	this.getLoggedUser = function() {
		
		var deferred = $q.defer();
		
		var req = {
    			method: 'GET',
    			url: 'http://localhost:8080/fiubappREST/api/sessions/administrators',
    			headers: {
    				'Authorization': localStorage.getItem("token")
    			}
    	}

    	$http(req).success(function(data){
    		localStorage.setItem("token", data.token);
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
		
		var logged = localStorage.getItem("token")!=null
		var loggedUser = {"logged": logged};
    	    	   	
    	return loggedUser;
    };
    
    this.login = function(userName, password) {
    	    	
    	var credentials = {"userName": userName, "password": password};
    	
    	var deferred = $q.defer();
    	    	
    	$http.post('http://localhost:8080/fiubappREST/api/sessions/administrators', credentials).success(function(data) {
            localStorage.setItem("token", data.token);
            deferred.resolve(data);
	    }).error(function(data, status, headers, config){
	    	deferred.reject("Nombre de usuario inexistente o contrasena incorrecta.");
	    });
    	
    	return deferred.promise;
    };
    
	this.logout = function() {
		
		var deferred = $q.defer();
		
		var req = {
    			method: 'DELETE',
    			url: 'http://localhost:8080/fiubappREST/api/sessions/administrators',
    			headers: {
    				'Authorization': localStorage.getItem("token")
    			}
    	}

    	$http(req).success(function(data){
    		localStorage.removeItem("token");
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
		var logged = localStorage.getItem("token")!=null
		var loggedUser = {"logged": false};
    	    	   	
    	return loggedUser;
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
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
    	return deferred.promise;
    };
    
    this.updateStudentState = function(userName, newState) {
    	    	
    	var state = {"state": newState};
    	
    	var deferred = $q.defer();
    	
    	var req = {
    			method: 'PUT',
    			url: 'http://localhost:8080/fiubappREST/api/students/' + userName + '/state',
    			headers: {
    				'Authorization': localStorage.getItem("token"),
    				'Content-Type' : 'application/json'
    			},
    			data: state 
    	}

    	$http(req).success(function(data){
            deferred.resolve(data);
        }).error(function(data){
        	deferred.reject(data.message);
        });
    	
    	return deferred.promise;
    };
});

AppServices.service('GroupService', function($http, $q) {
    
});
		
AppServices.service('ReportService', function($http, $q) {
    
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
