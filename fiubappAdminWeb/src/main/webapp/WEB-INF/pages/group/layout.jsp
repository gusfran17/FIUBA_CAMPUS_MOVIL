<form name="form" class="form-horizontal">
	<div class="control-group">
        <label class="control-label" style="margin-bottom: 15px; margin-left: 200px; margin-right: 15px;">Estado</label>

        <div class="controls">
            <select ng-model="searchParams.state" ng-options="c for c in states"></select><br>
        </div>
    </div>

	<div class="control-group">
        <label class="control-label" style="margin-bottom: 15px; margin-left: 200px; margin-right: 15px;">Nombre</label>

        <div class="controls">
            <input type="text" ng-model="searchParams.name" placeholder="Nombre">
        </div>
    </div>
    
    <div class="control-group" style="margin-left: 200px; margin-top:10px;">
        <div class="controls">
         	<a class="btn btn-primary" ng-click="search(searchParams)"><i class="icon-search icon-white"></i> Buscar</a>
         	<a class="btn" ng-click="resetForm()"><i class="icon-remove"></i> Limpiar</a>
        </div>
    </div>
</form>

<h3 ng-show="results.length > 0">Resultados de la búsqueda</h3>
<div class="alert alert-info" ng-show="results.length == 0">
    No se encontró ningún resultado
</div>

<div>
	<table ng-show="results.length > 0" class="table table-striped">
	    <thead>
	        <tr>
	            <th data-align="center">Nombre</th>
	            <th data-align="center">Fecha de creación</th>
	            <th data-align="center">Cantidad de miembros</th>
	            <th data-align="center">Acción</th>
	        </tr>
	    </thead>
	    <tbody>
			<tr ng-repeat="result in results">
        		<td>{{result.name}}</td>
        		<td>{{result.creationDate}}</td>        		
        		<td>{{result.amountOfMembers}}</td>
        		<td>
	    	  		<button type="button" ng-show="result.state=='Suspendido'" class="btn btn-success" ng-click="openStateModal($index)">Habilitar</button>
    	  			<button type="button" ng-show="result.state=='Habilitado'" class="btn btn-danger" ng-click="openStateModal($index)">Suspender</button>
        		</td>
      		</tr>
		</tbody>
	</table>
</div>
