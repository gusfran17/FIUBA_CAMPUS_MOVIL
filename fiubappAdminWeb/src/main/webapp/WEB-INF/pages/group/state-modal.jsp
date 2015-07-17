<div class="modal-dialog">
	<div class="modal-content">
    	<div class="modal-header">
      		<button type="button" class="close" ng-click="cancel()">&times;</button>
      		<h4 class="modal-title" ng-show="group.state=='Suspendido'">Habilitar grupos</h4>
      		<h4 class="modal-title" ng-show="group.state=='Habilitado'">Suspender grupos</h4>
    	</div>
    	<div class="modal-body">
			<div class="controls">
            	<img src="{{group.groupPicture}}"width="100" height="100" style="float:left; PADDING-RIGHT: 20px;">
            	<h4 class="modal-title">{{group.name}}</h4>
            	<h5 class="modal-title">Fecha de creación {{group.creationDate}}</h5>
            	<h5 class="modal-title">{{group.amountOfMembers}} miembros</h5>
			</div>
		</div>
		
    	<div class="modal-footer">
      		<button type="button" class="btn btn-default" ng-click="cancel()">Cancelar</button>
      		<button type="button" class="btn btn-primary" ng-show="group.state=='Habilitado'" ng-click="suspendGroup()" style="width:94px;">Suspender</button>
      		<button type="button" class="btn btn-primary" ng-show="group.state=='Suspendido'" ng-click="approveGroup()" style="width:94px;">Habilitar</button>
    	</div>
  	</div>
</div>