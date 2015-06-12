'use strict';

var ReportAlumnosController = function(ReportService, MessageService, $scope, $routeParams, $modal) {
	MessageService.resetError();
	
	var path = "";
	var data = "";
	
	ReportService.searchAlumnosTorta(path).then(function(data){
		
		$scope.procesarDatosTorta(data);
		
    }, function(errorMessage){
    	$scope.procesarDatosTorta(data);
    	//MessageService.setError(errorMessage);
    });
	
	ReportService.searchAlumnosLinea(path).then(function(data){
		
		$scope.procesarDatosLinea(data);
		
    }, function(errorMessage){
    	$scope.procesarDatosLinea(data);
    	//MessageService.setError(errorMessage);
    });
	
	$scope.procesarDatosTorta = function(data){
		
		data = {"cols":[{"label":"Carrera","type":"string"},{"label":"Cantidad usuarios","type":"number"}],
				"rows":[{"c":[{"v":"Ing. en Informática"},{"v":110}]},
				        {"c":[{"v":"Ing. en Alimentos"},{"v":24}]},
				        {"c":[{"v":"Ing. Civil"},{"v":26}]},
				        {"c":[{"v":"Ing. Química"},{"v":50}]},
				        {"c":[{"v":"Licenciatura en Sistemas"},{"v":34}]}]};
		
		$scope.resultsTorta = data;
				
		var chart3 = {};		
		chart3.type = "PieChart";		    
		chart3.cssStyle = "height:500px;width:650px;";
	    	
		chart3.data = data;
	    //chart3.data = $scope.crearDatosGrafico(data);
	    
		chart3.options = {
		        title: 'Carreras por usuario'
		};
		
		chart3.formatters = {};  
		
		$scope.chartCarrerasPorUsuario = chart3;
	    $scope.noHayResultadosTorta = ($scope.resultsTorta.length == 0);
	};
	
	$scope.procesarDatosLinea = function(data){
		
		data = {"cols":[{"label":"Mes","type":"string"},{"label":"Usuarios","type":"number"}],
	 			   "rows":[{"c":[{"v":"julio-2014"},{"v":2}]},
	 			           {"c":[{"v":"agosto-2014"},{"v":4}]},
	 			           {"c":[{"v":"septiembre-2014"},{"v":6}]},
	 			           {"c":[{"v":"octubre-2014"},{"v":13}]},
	 			           {"c":[{"v":"noviembre-2014"},{"v":45}]},
	 			           {"c":[{"v":"diciembre-2014"},{"v":60}]},
	 			           {"c":[{"v":"enero-2015"},{"v":90}]},
	 			           {"c":[{"v":"febrero-2015"},{"v":112}]},
	 			           {"c":[{"v":"marzo-2015"},{"v":150}]},
	 			           {"c":[{"v":"abril-2015"},{"v":200}]},
	 			           {"c":[{"v":"mayo-2015"},{"v":567}]},
	 			           {"c":[{"v":"junio-2015"},{"v":600}]}]};
		
		$scope.resultsLinea = data;
				
		var chart2 = {};
		chart2.type = "LineChart";	    
		chart2.cssStyle = "height:500px;width:650px;";
			
		chart2.data = data;
		//chart2.data = $scope.crearDatosGrafico(data);
		
		chart2.options = {
			    title: 'Cantidad usuarios activos',
			    curveType: 'function',
			    legend: { position: 'bottom' }
		 };

		chart2.formatters = {};
		
		$scope.chartUsuariosActivos = chart2;	
	    $scope.noHayResultadosLinea = ($scope.resultsLinea.length == 0);
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
	
	var doc = new jsPDF('p', 'pt');
	doc.setFontSize(20);
	doc.text(40, 40, "Reportes!!");
		
	doc.addImage(lineas, 'PNG', 60, 40, 480, 380);
	doc.addImage(torta, 'PNG', 60, 450, 480, 360);
	
	doc.save('Reporte.pdf');
  };
  
};