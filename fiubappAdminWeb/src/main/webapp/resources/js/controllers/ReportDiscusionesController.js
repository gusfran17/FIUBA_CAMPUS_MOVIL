'use strict';

var ReportDiscusionesController = function(ReportService, MessageService, $scope, $routeParams, $modal, TabService) {
	
	TabService.reload('reportDiscusionesTab');
	
	$scope.openFechaDesde = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();
	
	    $scope.openedFechaDesde = true;
	};
	  
	$scope.openFechaHasta = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();
	
	    $scope.openedFechaHasta = true;
	};
	  
	$scope.searchParams = {};
		
	$scope.cantidades = ['10', '20', '30'];
	$scope.searchParams.cantidad = $scope.cantidades[0];
	
	var hoy = new Date();	
	var haceUnMes = new Date(hoy.getFullYear(), hoy.getMonth() - 1, hoy.getDate());
		
	$scope.searchParams.fechaDesde = haceUnMes;		
	$scope.searchParams.fechaHasta = hoy;
	
	$scope.filasTablaReporte = "";
	
	$scope.search = function(searchParams) {
		
		$scope.results = [];		
		MessageService.resetError();
		$scope.filasTablaReporte = "";
		
		if(searchParams.fechaDesde == null || searchParams.fechaHasta == null){			
			MessageService.setError("Verifique que las fechas est\u00E9n cargadas");
		}
		
		var path = $scope.getParamsPath(searchParams);
		
		ReportService.searchDiscusiones(path).then(function(data){
			
			$scope.procesarDatos(data);
			
			SearchStorageService.push(searchParams, $scope.results);
			$scope.previousSearchs = SearchStorageService.getAll();
			
        }, function(errorMessage){
        	MessageService.setError(errorMessage);
        });
	};
			
	$scope.getParamsPath = function(searchParams){
		var params = [];
		
		var diaDesde = searchParams.fechaDesde.getDate();
		var mesDesde = searchParams.fechaDesde.getMonth();
		var anioDesde = searchParams.fechaDesde.getFullYear();
		
		var diaDesdeStr = "";
		var mesDesdeStr = "";
			
		diaDesdeStr = diaDesde.toString();
		if(diaDesde < 10)
			diaDesdeStr = '0' + diaDesde.toString();
		
		mesDesdeStr = (mesDesde + 1).toString();
		if(mesDesde + 1 < 10)
			mesDesdeStr = '0' + (mesDesde + 1).toString();
		
		var fechaDesde = diaDesdeStr + "/" + mesDesdeStr + "/" + anioDesde.toString();
		$scope.fechaDesdeStr = fechaDesde;
		
		var diaHasta = searchParams.fechaHasta.getDate();
		var mesHasta = searchParams.fechaHasta.getMonth();
		var anioHasta = searchParams.fechaHasta.getFullYear();
		
		var diaHastaStr = "";
		var mesHastaStr = "";
				
		diaHastaStr = diaHasta.toString();
		if(diaHasta < 10)
			diaHastaStr = '0' + diaHasta.toString();
		
		mesHastaStr = (mesHasta + 1).toString();
		if(mesHasta + 1 < 10)
			mesHastaStr = '0' + (mesHasta + 1).toString();
		
		var fechaHasta = diaHastaStr + "/" + mesHastaStr + "/" + anioHasta.toString();
		$scope.fechaHastaStr = fechaHasta; 
		
		$scope.addParamToArray(params, "dateFrom", fechaDesde);
		$scope.addParamToArray(params, "dateTo", fechaHasta);
		$scope.addParamToArray(params, "values", searchParams.cantidad);
				
		return $scope.buildParamsPath(params);
	};
	
	$scope.addParamToArray = function(params, paramName, param){
		if(param!='' && param!=undefined){
			params.push(paramName + "=" + param);
		}
	};
	
	$scope.buildParamsPath = function(params){
		var path="";
		if(params.length !=0){
			path += "?" + params[0];
			for (var i=1; i<params.length; i++) {
				path += "&" + params[i];
			}
		}
		return path;
	};
	
	$scope.procesarDatos = function(data){
		
		$scope.results = data;
				
		var chart1 = {};
		chart1.type = "ColumnChart";    
	    chart1.cssStyle = "height:600px;";
	    
	    var ancho = "30%";
	    	
	    if(data.length >= 10)
	    	ancho = "60%";
	    	
	    chart1.data = $scope.crearDatosGrafico(data);
	    
	    chart1.options = {
							title: 'Discusiones de mayor actividad',        
							hAxis: {
								title: 'Nombre de la discusi\u00F3n'
							},
							vAxis: {
								title: 'Cantidad comentarios'
							},
							chartArea: {
								width: '' + ancho  + ''
							},
							bar: {
								groupWidth: '100%'
							},
							backgroundColor : {
								stroke: 'black'
							}
	          			};

	    chart1.formatters = {};    
	    $scope.chartGruposMayorActividad = chart1;	
	    
	    $scope.noHayResultados = ($scope.results.length == 0);
	};
	
	$scope.crearDatosGrafico = function(data){
		
		var columnas = '{"cols":[' +
							'{"label":"Discusi\u00F3n","type":"string"},' +
							'{"label":"Comentarios","type":"number"},' + 
							'{"role":"style","type":"string","p":{"role":"style"}}' +
						'],';
		
		var filas =   '"rows":[';
		
		for(var i = 0; i < data.length; i++){
			
			filas += '{"c":[{"v":"' + data[i].discussionName + '"},{"v":' + data[i].amountOfComments + '},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]}';
			$scope.filasTablaReporte += '{"discusion": "' + data[i].discussionName + '", "grupo": "' + data[i].groupName + '", "comentarios": "' + data[i].amountOfComments + '", "miembros": "' + data[i].amountOfGroupMembers + '"}';
			
			if(i != data.length - 1){
				filas += ',';
				$scope.filasTablaReporte += ','; 
			}
		}
				
		filas += ']}';
		
		var columnasFilas = columnas + filas;
		
		return JSON.parse(columnasFilas);
	};	
	
	    
	// Reporte
	var barras;

	$scope.readyChartGruposMayorActividad = function readyChartGruposMayorActividad() {
		if( !($scope.chartWrapperBarra === undefined)){
			barras = $scope.chartWrapperBarra.getChart().getImageURI();				
		}	
		
	};

	$scope.exportar = function exportar() {
			
		var columns = [
			{title: "Discusi\u00F3n", key: "discusion"},
			{title: "Grupo", key: "grupo"}, 
			{title: "#Comentarios", key: "comentarios"}, 
			{title: "#Miembros", key: "miembros"}
		];
		
		var data = JSON.parse('[' + $scope.filasTablaReporte + ']');
			
		var doc = new jsPDF('p', 'pt');
		
		doc.setFontSize(15);
		doc.text(40, 40, 'Reporte de discusiones/grupos de mayor actividad');
		
		doc.setFontSize(10);
		doc.text(40, 60, 'Filtros: Fecha desde:' + $scope.fechaDesdeStr + ', Fecha hasta:' + $scope.fechaHastaStr + ', #Discusiones: ' + $scope.searchParams.cantidad);
		
		doc.addImage(barras, 'PNG', -10, 80, 650, 340);
		
		doc.addPage();
		doc.autoTable(columns, data, {margins: { right: 40, left: 40, top: 40, bottom: 40 }, startY: false });
		
		doc.save('Reporte_Discusiones_Grupos_Mayor_Actividad.pdf');
	};  
	  
	MessageService.resetError();
};
