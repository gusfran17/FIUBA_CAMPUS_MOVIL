<div ng-controller="ReportController">	
<form class="form-horizontal">
 
	<div google-chart chart="chartGruposMayorActividad" style="{{chartGruposMayorActividad.cssStyle}}" on-ready="readyChartGruposMayorActividad()" />
					
	<div google-chart chart="chartUsuariosActivos" style="{{chartUsuariosActivos.cssStyle}}" on-ready="readyChartUsuariosActivos()" />
	
	<div google-chart chart="chartCarrerasPorUsuario" style="{{chartCarrerasPorUsuario.cssStyle}}" on-ready="readyChartCarrerasPorUsuario()" />
	
	<button class="btn" ng-click="exportar()">Exportar a PDF</button>
	
	<button class="btn" ng-click="exportar1()">Exportar1 a PDF</button>
	
	
	
</form>
</div>
