<div ng-controller="ReportAlumnosController">

	<div class="input-group">
		<div google-chart chart="chartUsuariosActivos" style="{{chartUsuariosActivos.cssStyle}}" on-ready="readyChartUsuariosActivos()" />
	</div>
		
	<div class="input-group">
		<br/><br/>			
		<table ng-show="resultsTorta.length > 0" class="table table-striped">
		    <thead>
		        <tr>
		            <th data-align="center">Fecha</th>
		            <th data-align="center">Usuarios habilitados</th>		            
		        </tr>
		    </thead>
		    <tbody>
				<tr ng-repeat="result in resultsTorta">
	        		<td width="150px;">{{result.careerName}}</td>        		
	        		<td width="1px;">{{result.amountOfStudents}}</td>	        		
	      		</tr>
			</tbody>
		</table>		
	</div>
				
	<div class="input-group">
		<div google-chart chart="chartCarrerasPorUsuario" style="{{chartCarrerasPorUsuario.cssStyle}}" on-ready="readyChartCarrerasPorUsuario()" />
	</div>
	
	<div class="input-group">
		<br/><br/>			
		<table ng-show="resultsTorta.length > 0" class="table table-striped">
		    <thead>
		        <tr>
		            <th data-align="center">Carrera</th>
		            <th data-align="center">#Usuarios</th>		            
		        </tr>
		    </thead>
		    <tbody>
				<tr ng-repeat="result in resultsTorta">
	        		<td width="200px;">{{result.careerName}}</td>        		
	        		<td width="1px;">{{result.amountOfStudents}}</td>	        		
	      		</tr>
			</tbody>
		</table>		
	</div>
		
	<a class="btn btn-primary" ng-click="exportar()">Exportar a PDF</a>
	
	<br/>
	<div ng-show="noHayResultadosLinea && noHayResultadosTorta && !error">
		<br/><br/><br/>
		<div class="alert alert-info">
			No se encontró ningún resultado
		</div>
	</div>
	<br/>
</div>
