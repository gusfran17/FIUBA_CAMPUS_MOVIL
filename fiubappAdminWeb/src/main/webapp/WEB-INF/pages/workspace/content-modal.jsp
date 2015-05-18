<div class="modal-dialog">
	<div class="modal-content">
    	<div class="modal-header">
      		<button type="button" class="close" ng-click="cancel()">&times;</button>
      		<h4 class="modal-title">Carga de contenidos</h4>
    	</div>
    	<div class="modal-body">
    		<form name="form" class="form-horizontal">

				<!-- Todos los tipos de contenidos -->
				<div class="control-group">
        			<label class="control-label">Tipo de contenido</label>

        			<div class="controls">
            			<select ng-model="content.type" ng-options="c for c in types"></select><br>
        			</div>
    			</div>

    			<div class="control-group">
        			<label class="control-label">Nombre</label>

        			<div class="controls">
            			<input type="text" ng-model="content.name" placeholder="Nombre">
        			</div>
    			</div>
    
    			<div class="control-group">
        			<label class="control-label">Descripción</label>

        			<div class="controls">
            			<input type="text" ng-model="content.description" placeholder="Descripción">
        			</div>
    			</div>
    
 				<!-- Documentos -->
   				<div class="control-group" ng-show="content.type=='Documento'">
        			<label class="control-label">Título</label>

	        		<div class="controls">
    	        		<input type="text" ng-model="content.title" placeholder="Título">
        			</div>
    			</div>
    
    			<div class="control-group" ng-show="content.type=='Documento'">
        			<label class="control-label">Autor</label>

        			<div class="controls">
	            		<input type="text" ng-model="content.author" placeholder="Autor">
    	    		</div>
    			</div>
    
    			<div class="control-group" ng-show="content.type=='Documento'">
        			<label class="control-label">Cantidad de páginas</label>

        			<div class="controls">
            			<input type="number" ng-model="content.amountOfPages" placeholder="Cantidad de páginas">
        			</div>
    			</div>
        
				<!-- Videos -->
    			<div class="control-group" ng-show="content.type=='Video'">
        			<label class="control-label">Formato</label>

        			<div class="controls">
	            		<input type="text" ng-model="content.format" placeholder="Formato">
    	    		</div>
    			</div>
    
				<div class="control-group" ng-show="content.type=='Video'">
        			<label class="control-label">Calidad</label>

        			<div class="controls">
            			<input type="text" ng-model="content.quality" placeholder="Calidad">
        			</div>
    			</div>
    
    			<div class="control-group" ng-show="content.type=='Video'">
        			<label class="control-label">Duración en segundos</label>

		        	<div class="controls">
        		    	<input type="number" ng-model="content.durationInSeconds" placeholder="Duración en segundos">
        			</div>
    			</div>
          
				<!-- Imágenes -->    		
    			<div class="control-group" ng-show="content.type=='Imagen'">
					<label class="control-label">Etiquetas</label>
    	    		<div class="controls">
        	    		<input type="text" style="width:100px;" ng-model="newLabel.name" placeholder="Nombre">
            			<input type="number" style="width:35px;" ng-model="newLabel.positionX" placeholder="X">
            			<input type="number" style="width:35px;" ng-model="newLabel.positionY" placeholder="Y">
            			<a class="btn btn-mini btn-success" ng-click="addNewLabel(newLabel)"><i class="icon-plus icon-white"></i> Agregar</a>
        			</div>
    			</div>
    
    			<div class="control-group" ng-show="content.type=='Imagen'">
        			<div class="controls" data-ng-repeat="label in content.labels">
            			<input type="text" style="width:100px;" ng-model="label.name" placeholder="Nombre">
            			<input type="number" style="width:35px;" ng-model="label.positionX" placeholder="X">
            			<input type="number" style="width:35px;" ng-model="label.positionY" placeholder="Y">
            			<a class="btn btn-mini btn-danger" ng-click="removeLabel($index)"><i class="icon-minus icon-white"></i> Quitar</a>
        			</div>
    			</div>
    		
			</form>
		</div>
    	
    	<div class="modal-footer">
      		<button type="button" class="btn btn-default" ng-click="cancel()">Cerrar</button>
      		<button type="button" class="btn btn-primary" ng-click="ok()">Guardar cambios</button>
    	</div>
  	</div>
</div>