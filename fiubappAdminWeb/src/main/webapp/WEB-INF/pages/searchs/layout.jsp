 <form name="form" class="form-horizontal">
	<!-- Todos los tipos de contenidos -->
	<div class="control-group">
        <label class="control-label">Estado</label>

        <div class="controls">
            <select ng-model="searchParams.state" ng-options="c for c in states"></select><br>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">Padrón</label>

        <div class="controls">
            <input type="text" ng-model="searchParams.fileNumber" placeholder="Padrón">
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label">Pasaporte</label>

        <div class="controls">
            <input type="text" ng-model="searchParams.passportNumber" placeholder="Pasaporte">
        </div>
    </div>
    
   	<div class="control-group">
        <label class="control-label">Nombre</label>

        <div class="controls">
            <input type="text" ng-model="searchParams.name" placeholder="Nombre">
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label">Apellido</label>

        <div class="controls">
            <input type="text" ng-model="searchParams.lastName" placeholder="Apellido">
        </div>
    </div>
    
    <div class="control-group">
        <hr />
        <div class="controls">
         	<a class="btn btn-primary" ng-click="search(searchParams)"><i class="icon-search icon-white"></i> Buscar</a>
         	<a class="btn" ng-click="resetForm()"><i class="icon-remove"></i> Limpiar</a>
        </div>
    </div>
</form>

<h3 ng-show="results.length > 0">Resultados de la búsqueda</h3>
<div class="alert alert-info" style="width:400px;" ng-show="results.length == 0">
    No se encontró ningún resultado
</div>

<div class="bs-example" ng-show="results.length > 0" ng-repeat="result in results">
	<div class="media">
	    <div class="media-body">
	        <p>{{result.userName}}</p>
	        <p>{{result.name}}</p>
	        <p>{{result.lastName}}</p>
	        <p>{{result.fileNumber}}</p>
	        <p>{{result.passportNumber}}</p>
	        <p>{{result.isExchangeStudent}}</p>
	    </div>
	</div>
</div>