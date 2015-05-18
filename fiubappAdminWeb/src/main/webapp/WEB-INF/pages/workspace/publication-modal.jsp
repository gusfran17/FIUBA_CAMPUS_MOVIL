<div class="modal-dialog">
	<div class="modal-content">
    	<div class="modal-header">
      		<button type="button" class="close" ng-click="cancel()">&times;</button>
      		<h4 class="modal-title">Publicación de contenidos</h4>
    	</div>
    	
    	<div class="alert alert-error" ng-show="error">
    		{{errorMessage}}
		</div>
    	
    	<div class="modal-body">
    		<form name="form" class="form-horizontal">
    			<div class="control-group">
        			<label class="control-label">Estado</label>

        			<div class="controls">
            			<input type="text" ng-readonly="true" ng-show="publication.state=='PUBLISHED'" value="Publicado">
            			<input type="text" ng-readonly="true" ng-show="publication.state=='UPLOADED'" value="Sin publicar">
            			<input type="text" ng-readonly="true" ng-show="publication.state=='PENDING'" value="Pendiente {{publication.publicationDate}}">
        			</div>
    			</div>

    			<div class="control-group" ng-show="publication.state=='UPLOADED'">
        			<label class="control-label">Publicación Automática</label>

        			<div class="controls">
            			<input type="checkbox" ng-model="automaticPublication.checked">
<!--             			<input type="datetime-local" style="width:190px;" ng-show="automaticPublication.checked" ng-model="automatic.Publication.publicationDate" placeholder="dd/MM/yyyy" required /> -->
							<input type="text" style="width:190px;" ng-show="automaticPublication.checked" current-text="Hoy" clear-text="Limpiar" close-text="Cerrar" toggle-weeks-text="Semana" show-weeks="false" datepicker-popup="dd/MM/yyyy" ng-model="automaticPublication.publicationDate" date-disabled="disabled(date, mode)" is-open="opened" date-disabled="disabled(date, mode)" ng-required="true" placeholder="Fecha de publicación"/>
        			</div>
    			</div>
				<div class="control-group" ng-show="publication.state=='UPLOADED'">
        			<label class="control-label">Filtro por sexo</label>

        			<div class="controls">
            			<input type="checkbox" ng-model="genderFilter.checked">
            			<input type="radio" ng-show="genderFilter.checked" ng-model="genderFilter.gender" value="MALE" required> <span ng-show="genderFilter.checked"> Masculino</span> 
   						<input type="radio" ng-show="genderFilter.checked" ng-model="genderFilter.gender" value="FEMALE" required> <span ng-show="genderFilter.checked"> Femenino</span>
        			</div>
    			</div>
    			
    			<div class="control-group" ng-show="publication.state=='UPLOADED'">
        			<label class="control-label">Filtro por rango de edad</label>

        			<div class="controls">
            			<input type="checkbox" ng-model="rangeAgeFilter.checked">
            			<span ng-show="rangeAgeFilter.checked"> Entre </span>
            			<input type="number" ng-show="rangeAgeFilter.checked" style="width:40px;" ng-model="rangeAgeFilter.minAge" placeholder="min">
            			<span ng-show="rangeAgeFilter.checked"> y </span>
            			<input type="number" ng-show="rangeAgeFilter.checked" style="width:40px;" ng-model="rangeAgeFilter.maxAge" placeholder="max">
            			<span ng-show="rangeAgeFilter.checked"> años</span>
        			</div>
    			</div>
    			
    			<div class="control-group" ng-show="publication.state=='UPLOADED'">
        			<label class="control-label">Filtro menor de edad</label>

        			<div class="controls">
            			<input type="checkbox" ng-model="underAgeFilter.checked">
            			<span ng-show="underAgeFilter.checked"> Mayores de </span>
            			<input type="number" ng-show="underAgeFilter.checked" style="width:68px;" ng-model="underAgeFilter.minAge" placeholder="min">
            			<span ng-show="underAgeFilter.checked"> años</span>
        			</div>
    			</div>
    			
				<div class="control-group" ng-show="publication.state=='UPLOADED'">
        			<label class="control-label">Filtro mayor de edad</label>

        			<div class="controls">
            			<input type="checkbox" ng-model="overAgeFilter.checked">
            			<span ng-show="overAgeFilter.checked"> Menores de </span>
            			<input type="number" ng-show="overAgeFilter.checked" style="width:68px;" ng-model="overAgeFilter.maxAge" placeholder="max">
            			<span ng-show="overAgeFilter.checked"> años</span>
        			</div>
    			</div>

			</form>
		</div>
    	
    	<div class="modal-footer">
      		<button type="button" class="btn btn-default" ng-click="cancel()">Cerrar</button>
      		<button type="button" ng-show="publication.state=='PUBLISHED' || publication.state=='PENDING'" class="btn btn-primary" ng-click="updatePublication()">Despublicar</button>
      		<button type="button" ng-show="publication.state=='UPLOADED'" class="btn btn-primary" ng-click="updatePublication()">Publicar</button>      		
    	</div>
  	</div>
</div>