<div ng-controller="ReportAlumnosController">	
<form class="form-horizontal">
				
	<div google-chart chart="chartUsuariosActivos" style="{{chartUsuariosActivos.cssStyle}}" on-ready="readyChartUsuariosActivos()" />
	
	<div google-chart chart="chartCarrerasPorUsuario" style="{{chartCarrerasPorUsuario.cssStyle}}" on-ready="readyChartCarrerasPorUsuario()" />
	
	<button class="btn" ng-click="exportar()">Exportar a PDF</button>	
	
</form>
</div>
