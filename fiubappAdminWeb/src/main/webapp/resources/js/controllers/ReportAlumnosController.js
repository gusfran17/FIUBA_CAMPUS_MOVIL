'use strict';

var ReportAlumnosController = function(ReportService, MessageService, $scope, $routeParams, $modal, TabService) {
	
	TabService.reload('reportAlumnosTab');
	
	MessageService.resetError();
	
	$scope.filasTablaReporteTorta = "";
	$scope.filasTablaReporteLineas = "";
	
	ReportService.searchAlumnosTorta().then(function(data){
		
		$scope.procesarDatosTorta(data);
		
    }, function(errorMessage){    	
    	MessageService.setError(errorMessage);
    });
	
	ReportService.searchAlumnosLinea().then(function(data){
		
		$scope.procesarDatosLinea(data);
		
    }, function(errorMessage){    	
    	MessageService.setError(errorMessage);
    });
	
	$scope.procesarDatosTorta = function(data){
		
		$scope.resultsTorta = data;
				
		var chart3 = {};		
		chart3.type = "PieChart";		    
		chart3.cssStyle = "height:500px;width:650px;";
	    
	    chart3.data = $scope.crearDatosGraficoTorta(data);
	    
		chart3.options = {
		        title: 'Usuarios por carrera'
		};
		
		chart3.formatters = {};  
		
		$scope.chartCarrerasPorUsuario = chart3;
	    $scope.noHayResultadosTorta = ($scope.resultsTorta.length == 0);
	};
	
	$scope.procesarDatosLinea = function(data){
		
		$scope.resultsLinea = data;
				
		var chart2 = {};
		chart2.type = "LineChart";	    
		chart2.cssStyle = "height:500px;width:650px;";
			
		chart2.data = data;
		chart2.data = $scope.crearDatosGraficoLineas(data);
		
		chart2.options = {
			    title: 'Cantidad usuarios activos',
			    curveType: 'function',
			    legend: { position: 'bottom' }
		 };

		chart2.formatters = {};
		
		$scope.chartUsuariosActivos = chart2;	
	    $scope.noHayResultadosLinea = ($scope.resultsLinea.length == 0);
	};
	
	$scope.crearDatosGraficoLineas = function(data){
		
		var columnas = '{"cols":[' +
							'{"label":"Mes","type":"string"},' +
							'{"label":"Usuarios","type":"number"}' +
						'],';
		
		var filas =   '"rows":[';
		
		for(var i = 0; i < data.length; i++){
			
			filas += '{"c":[{"v":"' + data[i].monthYear + '"},{"v":' + data[i].amountOfStudents + '}]}';			
			$scope.filasTablaReporteLineas += '{"fecha": "' + data[i].monthYear + '", "habilitados": "' + data[i].amountOfStudents + '"}';
			
			if(i != data.length - 1){
				filas += ',';				
				$scope.filasTablaReporteLineas += ',';
			}
		}
				
		filas += ']}';
		
		var columnasFilas = columnas + filas;
		
		return JSON.parse(columnasFilas);
	};	
	
	$scope.crearDatosGraficoTorta = function(data){
		
		var columnas = '{"cols":[' +
							'{"label":"Carrera","type":"string"},' +
							'{"label":"Cantidad usuarios","type":"number"}' +
						'],';
		
		var filas =   '"rows":[';
		
		for(var i = 0; i < data.length; i++){
			
			filas += '{"c":[{"v":"' + data[i].careerName + '"},{"v":' + data[i].amountOfStudents + '}]}';
			$scope.filasTablaReporteTorta += '{"carrera": "' + data[i].careerName + '", "usuarios": "' + data[i].amountOfStudents + '"}';
						
			if(i != data.length - 1){
				filas += ',';
				$scope.filasTablaReporteTorta += ',';				
			}
		}
				
		filas += ']}';
		
		var columnasFilas = columnas + filas;
		
		return JSON.parse(columnasFilas);
	};	
	
	
	// Reportes

	var lineas;
	var torta;

	$scope.readyChartUsuariosActivos = function readyChartUsuariosActivos() {
		if( !($scope.chartWrapperLinea === undefined))
			lineas = $scope.chartWrapperLinea.getChart().getImageURI();  
	};

	$scope.readyChartCarrerasPorUsuario = function readyChartCarrerasPorUsuario() {
		if( !($scope.chartWrapperTorta === undefined))
			torta = $scope.chartWrapperTorta.getChart().getImageURI();  
	};

	$scope.exportar = function exportar() {
	
		var columnsTorta = [
			{title: "Carrera", key: "carrera"},
			{title: "#Usuarios", key: "usuarios"}
		];
		
		var columnsLineas = [
			{title: "Fecha", key: "fecha"},
			{title: "Usuarios habilitados", key: "habilitados"}
		];
		
		var dataTorta = JSON.parse('[' + $scope.filasTablaReporteTorta + ']');
		var dataLineas = JSON.parse('[' + $scope.filasTablaReporteLineas + ']');
			
		var doc = new jsPDF('p', 'pt');
		
		doc.setFontSize(15);
		doc.text(40, 40, 'Reporte de usuarios activos de los \u00FAltimos 12 meses');
	
		doc.addImage(lineas, 'PNG', 60, 50, 480, 380);		
		doc.autoTable(columnsLineas, dataLineas, {margins: { right: 40, left: 40, top: 440, bottom: 40 }, startY: false });
		
		doc.addPage();
	
		doc.addImage(torta, 'PNG', 60, 40, 480, 360);		
		doc.autoTable(columnsTorta, dataTorta, {margins: { right: 40, left: 40, top: 400, bottom: 40 }, startY: false });
		
		doc.save('Reporte_Usuarios_Activos.pdf');
  };
  
};