<div class="modal-dialog">
	<div class="modal-content">
    	<div class="modal-header">
      		<button type="button" class="close" ng-click="cancel()">&times;</button>
      		<h4 class="modal-title" ng-show="student.state=='Pendiente' || student.state=='Suspendido'">Habilitar alumnos</h4>
      		<h4 class="modal-title" ng-show="student.state=='Habilitado'">Suspender alumnos</h4>
    	</div>
    	<div class="modal-body">
			<div class="controls">
            	<img src="{{student.profilePicture}}"width="100" height="100" style="float:left; PADDING-RIGHT: 20px;">
            	<h4 class="modal-title">{{student.name}} {{student.lastName}}</h4>
            	<h5 class="modal-title" ng-hide="student.isExchangeStudent">Padrón {{student.fileNumber}}</h5>
            	<h5 class="modal-title" ng-show="student.isExchangeStudent">Pasaporte {{student.passportNumber}}</h5>
            	<h5 class="modal-title" ng-hide="student.isExchangeStudent">{{student.careers[0]}}</h5>
			</div>
		</div>
		
		<div class="modal-body">
			<div class="controls" ng-show="student.state=='Pendiente'">
            	<label class="control-label">Recordá enviar un email a {{student.email}} confirmando la aprobación.</label>
			</div>
		</div>
    	
    	<div class="modal-footer">
      		<button type="button" class="btn btn-default" ng-click="cancel()">Cancelar</button>
      		<button type="button" class="btn btn-primary" ng-show="student.state=='Habilitado'" ng-click="suspendStudent()">Suspender</button>
      		<button type="button" class="btn btn-primary" ng-hide="student.state=='Habilitado'" ng-click="approveStudent()">Habilitar</button>
    	</div>
  	</div>
</div>