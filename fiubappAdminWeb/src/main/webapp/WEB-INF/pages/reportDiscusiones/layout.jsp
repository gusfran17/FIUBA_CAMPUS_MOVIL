<style>
  .full button span {
    background-color: limegreen;
    border-radius: 32px;
    color: black;
  }
  .partially button span {
    background-color: orange;
    border-radius: 32px;
    color: black;
  }
</style>

<div ng-controller="ReportDiscusionesController">	
<form class="form-horizontal">
 

  <input type="text" class="form-control" ng-disabled="true" datepicker-popup="{{format}}" 
  										ng-model="dt" is-open="opened" min-date="minDate" max-date="'2015-06-22'" 
  										datepicker-options="dateOptions" date-disabled="disabled(date, mode)" 
  										ng-required="true" close-text="Close" />
  <span class="input-group-btn">
  	<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
  </span>
        
 	<div class="control-group">
        
		<label class="span1">Desde</label>
		
		<label class="span1">Hasta</label>
		<label class="span2">#Discusiones</label>
        <select class="span1 selectpicker" ng-model="searchParams.state" ng-options="c for c in states"></select>
       	<a class="btn btn-primary" ng-click="search(searchParams)">Generar</a>        	
        
    </div>
    
    <div class="alert alert-info" ng-show="results.length == 0">
    	No se encontró ningún resultado
	</div>

	<div google-chart chart="chartGruposMayorActividad" style="{{chartGruposMayorActividad.cssStyle}}" on-ready="readyChartGruposMayorActividad()" />
		
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
	        
</form>
</div>
