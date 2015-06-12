<form class="form-horizontal"> 	
 	<div style="margin-left: 150px;"> 
 		 	 	
        <label class="input-group">Desde</label>
        <p class="input-group">
        	<input class="input-datepicker" type="text" ng-disabled="true" datepicker-popup="dd/MM/yyyy" show-weeks="false"
        		   ng-model="searchParams.fechaDesde" is-open="openedFechaDesde" ng-required="true" />  						     		
			<button type="button" class="btn btn-default" ng-click="openFechaDesde($event)"><i class="icon-calendar"></i></button>
		</p>
		<label class="input-group">Hasta</label>
		<p class="input-group">
			<input class="input-datepicker" type="text" ng-disabled="true" datepicker-popup="dd/MM/yyyy" show-weeks="false"
				   ng-model="searchParams.fechaHasta" is-open="openedFechaHasta" ng-required="true" />  						     		
			<button type="button" class="btn btn-default " ng-click="openFechaHasta($event)"><i class="icon-calendar"></i></button>
		</p>
		
        <label class="input-group">#Discusiones</label>	
        <select class="input-group col-md-1 selectpicker" ng-model="searchParams.cantidad" ng-options="c for c in cantidades"></select>        
       	<a class="btn btn-primary col-md-1" ng-click="search(searchParams)">Generar</a>       
    </div>
</form>   
 
<br/><br/>
 
<div ng-show="results.length > 0">
	<div google-chart chart="chartGruposMayorActividad" style="{{chartGruposMayorActividad.cssStyle}}" on-ready="readyChartGruposMayorActividad()" />
</div>
	
<br/><br/>
<div>
<table ng-show="results.length > 0" class="table table-striped">
    <thead>
        <tr>
            <th data-align="center">Discusión</th>
            <th data-align="center">Grupo</th>
            <th data-align="center">#Comentarios</th>
            <th data-align="center">#Miembros</th>	            
        </tr>
    </thead>
    <tbody>
		<tr ng-repeat="result in results">
       		<td>{{result.discussionName}}</td>        		
       		<td>{{result.groupName}}</td>
       		<td>{{result.amountOfComments}}</td>
       		<td>{{result.amountOfGroupMembers}}</td>        		
     		</tr>
	</tbody>
</table>
</div>

<br/>
<a class="btn btn-primary" ng-show="results.length > 0" ng-click="exportar()">Exportar a PDF</a>
<br/>
<div ng-show="noHayResultados && !error">
	<br/><br/><br/>
	<div class="alert alert-info">
		No se encontró ningún resultado
	</div>
</div>
<br/>