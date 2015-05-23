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
<div class="table-responsive">
	<table ng-show="results.length > 0" class="table table-striped" data-sort-name="name" data-sort-order="desc">
	    <thead>
	        <tr>
	            <th data-field="fileNumber" data-align="right" data-sortable="true">Padrón/Pasaporte</th>
	            <th data-field="name" data-align="right" data-sortable="true">Nombre</th>
	            <th data-field="lastName" data-align="right" data-sortable="true">Apellido</th>
	            <th data-field="lastName" data-align="right" data-sortable="true">Email</th>
	            <th data-field="passportNumber" data-align="right" data-sortable="true">Acción</th>
	        </tr>
	    </thead>
	    <tbody>
			<tr ng-repeat="result in results">
        		<td>{{result.fileNumber}}{{result.passportNumber}}</td>        		
        		<td>{{result.name}}</td>
        		<td>{{result.lastName}}</td>
        		<td>{{result.email}}</td>
        		<td>
<!-- 	    	  		<button type="button" ng-show="publication.state=='PUBLISHED' || publication.state=='PENDING'" class="btn btn-primary" ng-click="updatePublication()">Despublicar</button> -->
    	  			<button type="button" class="btn btn-primary" ng-click="updatePublication()">Habilitar</button>
        		</td>
      		</tr>
		</tbody>
	</table>
</div>