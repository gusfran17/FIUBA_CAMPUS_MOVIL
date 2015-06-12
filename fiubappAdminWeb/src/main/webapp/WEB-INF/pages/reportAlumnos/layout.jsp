<div ng-controller="ReportAlumnosController">

	<div class="input-group">
		<div google-chart chart="chartUsuariosActivos" style="{{chartUsuariosActivos.cssStyle}}" on-ready="readyChartUsuariosActivos()" />
	</div>
		
	<div class="input-group">
		<br/><br/>
		<table class="table table-striped">
		    <thead>
		        <tr>
		            <th data-align="center">Fecha</th>
		            <th data-align="center">Usuarios habilitados</th>		            	            
		        </tr>
		    </thead>
		    <tbody>
				<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>febereo-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>marzo-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>abril-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>mayo-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>junio-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
			</tbody>
		</table>
	</div>
				
	<div class="input-group">
		<div google-chart chart="chartCarrerasPorUsuario" style="{{chartCarrerasPorUsuario.cssStyle}}" on-ready="readyChartCarrerasPorUsuario()" />
	</div>
	
	<div class="input-group">
		<br/><br/>	
		<table class="table table-striped">
		    <thead>
		        <tr>
		            <th data-align="center">Carrera</th>
		            <th data-align="center">#Usuarios</th>		            	            
		        </tr>
		    </thead>
		    <tbody>
				<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
	     		<tr>
		       		<td>enero-14</td>        		
		       		<td>23</td>		       		        		
	     		</tr>
			</tbody>
		</table>	
	</div>
			
	<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
	<!-- 
	<a class="btn btn-primary" ng-show="resultsLinea.length > 0 || resultsTorta.length > 0" ng-click="exportar()">Exportar a PDF</a>
	-->
	
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
