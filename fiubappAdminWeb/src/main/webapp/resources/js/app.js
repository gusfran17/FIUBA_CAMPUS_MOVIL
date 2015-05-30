'use strict';

var FiubappWebAdminApp = {};

var App = angular.module('FiubappWebAdminApp', ['FiubappWebAdminApp.services','ui.bootstrap', 'ngRoute']).
config(['$routeProvider', '$locationProvider', function($routeProvider,$locationProvider) {
	
	$routeProvider.when('/login', {
        templateUrl: 'webapp/login/layout',
        controller: LoginController
    });
    
    $routeProvider.when('/logout', {
        templateUrl: 'webapp/login/layout',
        controller: LogoutController
    });
	
	$routeProvider.when('/student', {
        templateUrl: 'webapp/student/layout',
        controller: StudentController
    });	

    $routeProvider.when('/group', {
        templateUrl: 'webapp/group/layout',
        controller: GroupController
    });
    
    $routeProvider.when('/report', {
        templateUrl: 'webapp/report/layout',
        controller: ReportController
    });

    $routeProvider.otherwise({redirectTo: '/student'});
    
 }])
.factory('authHttpResponseInterceptor',['$q','$location',function($q, $location){
    return {
        response: function(response){
            if (response.status === 403) {
                console.log("Response 403");
            }
            return response || $q.when(response);
        },
        responseError: function(rejection) {
            if (rejection.status === 403) {
            	console.log("rej; "+ rejection);
            	console.log("rej; "+ rejection.message);
            	console.log("message; "+ rejection.message);
                var previous = $location.path();
                $location.path('/login').search('returnTo', previous);
            }
            return $q.reject(rejection);
        }
    };
}])
.config(['$httpProvider',function($httpProvider) {
    $httpProvider.interceptors.push('authHttpResponseInterceptor');
}])
.run(function ($rootScope, SecurityService, MessageService) {
	
	$rootScope.error = false;
	$rootScope.errorMessage = "";
	
    $rootScope.loggedUser = {"logged": false};
    
    $rootScope.$on('$viewContentLoaded', function(){
    	
    	$rootScope.loggedUser = SecurityService.getLoggedUser();
    });
});