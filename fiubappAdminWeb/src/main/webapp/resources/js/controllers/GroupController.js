'use strict';

var GroupController = function(GroupService, SecurityService, MessageService, $scope, $routeParams, $filter, TabService) {
		
	MessageService.resetError();
	
	TabService.reload('groupTab');
};