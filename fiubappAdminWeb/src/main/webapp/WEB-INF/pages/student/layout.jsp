 <form name="form" class="form-horizontal">
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

<div>
	<table ng-show="results.length > 0" class="table table-striped">
	    <thead>
	        <tr>
	            <th data-align="center">Padrón/Pasaporte</th>
	            <th data-align="center">Nombre</th>
	            <th data-align="center">Apellido</th>
	            <th data-align="center">Email</th>
	            <th data-align="center">Acción</th>
	        </tr>
	    </thead>
	    <tbody>
			<tr ng-repeat="result in results">
        		<td>{{result.fileNumber}}{{result.passportNumber}}</td>        		
        		<td>{{result.name}}</td>
        		<td>{{result.lastName}}</td>
        		<td>{{result.email}}</td>
        		<td>
	    	  		<button type="button" ng-show="result.state=='Pendiente' || result.state=='Suspendido'" class="btn btn-success" ng-click="openStateModal($index)">Habilitar</button>
    	  			<button type="button" ng-show="result.state=='Habilitado'" class="btn btn-danger" ng-click="openStateModal($index)">Suspender</button>
        		</td>
      		</tr>
		</tbody>
	</table>
</div>