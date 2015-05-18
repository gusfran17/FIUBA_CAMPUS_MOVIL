<form ng-hide="error" name="form" class="form-horizontal">

	<!-- Todos los tipos de contenidos -->
	<div class="control-group">
        <label class="control-label">Tipo de contenido</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="type" placeholder="Tipo">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">Nombre</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="content.name" placeholder="Nombre">
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label">Descripción</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="content.description" placeholder="Descripción">
        </div>
    </div>
    
 	<!-- Documentos -->
   	<div class="control-group" ng-show="content.type=='Document'">
        <label class="control-label">Título</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="content.title" placeholder="Título">
        </div>
    </div>
    
    <div class="control-group" ng-show="content.type=='Document'">
        <label class="control-label">Autor</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="content.author" placeholder="Autor">
        </div>
    </div>
    
    <div class="control-group" ng-show="content.type=='Document'">
        <label class="control-label">Cantidad de páginas</label>

        <div class="controls">
            <input type="number" ng-readonly="!isEditable" ng-model="content.amountOfPages" placeholder="Cantidad de páginas">
        </div>
    </div>
    
    <div class="control-group" ng-show="content.type=='Document'">
    	<embed src="{{content.url}}" height="600" width="600">
    </div>
        
	<!-- Videos -->
    <div class="control-group" ng-show="content.type=='Video'">
        <label class="control-label">Formato</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="content.format" placeholder="Formato">
        </div>
    </div>
    
	<div class="control-group" ng-show="content.type=='Video'">
        <label class="control-label">Calidad</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="content.quality" placeholder="Calidad">
        </div>
    </div>
    
    <div class="control-group" ng-show="content.type=='Video'">
        <label class="control-label">Duración en segundos</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="content.durationInSeconds" placeholder="Duración en segundos">
        </div>
    </div>
    
    <div class="control-group" ng-show="content.type=='Video'">
    	<video controls ng-src="{{content.url}}"></video>
    </div>  
          
	<!-- Imágenes -->
    <div class="control-group" ng-show="content.type=='Image'">
		<label class="control-label">Etiquetas</label>
				
        <div class="controls" data-ng-repeat="label in content.labels">
            <input type="text" ng-readonly="!isEditable" value="{{label.name +' ('+label.positionX + ', '+ label.positionY+')'}}"placeholder="Etiqueta">
        </div>
    </div>
    
    <div class="control-group" ng-show="content.type=='Image'">
	    <img src="{{content.url}}" >
    </div>
    
    <div class="control-group">
        <hr />
        <div class="controls">
         	<a class="btn btn-primary" ng-href="{{content.url}}"><i class="icon-arrow-down icon-white"></i> Descargar</a>         	
        </div>
    </div>
</form>
