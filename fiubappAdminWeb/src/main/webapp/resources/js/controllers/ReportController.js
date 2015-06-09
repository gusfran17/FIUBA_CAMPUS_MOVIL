'use strict';

var ReportController = function(ReportService, MessageService, $scope, $routeParams, $modal) {
	MessageService.resetError();
	
	var chart1 = {};
	
    chart1.type = "ColumnChart";
    
    chart1.cssStyle = "height:600px;";
    
    chart1.data = {"cols":[{"label":"Foro","type":"string"},
                           {"label":"Respuestas","type":"number"},
                           {"role":"style","type":"string","p":{"role":"style"}}],
    		"rows":[{"c":[{"v":"Foro 1"},{"v":100},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 2"},{"v":98},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 3"},{"v":80},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 4"},{"v":63},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 5"},{"v":60},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 6"},{"v":35},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 7"},{"v":30},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 8"},{"v":20},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 9"},{"v":12},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]},
    		        {"c":[{"v":"Foro 10"},{"v":3},{"v":"stroke-color: black; stroke-width: 2; fill-color: #3874FF"}]}]};

    chart1.options = {
            title: 'Foros/Grupos de mayor actividad desde <<dd/mm/aaaa>> hasta <<dd/mm/aaaa>>',        
            hAxis: {
              title: 'Nombre del foro'
            },
            vAxis: {
              title: 'Cantidad respuestas'
            },
    		chartArea: {
    			width: '60%'
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
    
    
var chart2 = {};
	
chart2.type = "LineChart";
    
chart2.cssStyle = "height:600px;";
    
chart2.data = {"cols":[{"label":"Mes","type":"string"},{"label":"Usuarios","type":"number"}],
    			   "rows":[{"c":[{"v":"julio-2014"},{"v":2}]},{"c":[{"v":"agosto-2014"},{"v":4}]},
    			           {"c":[{"v":"septiembre-2014"},{"v":6}]},{"c":[{"v":"octubre-2014"},{"v":13}]},
    			           {"c":[{"v":"noviembre-2014"},{"v":45}]},{"c":[{"v":"diciembre-2014"},{"v":60}]},
    			           {"c":[{"v":"enero-2015"},{"v":90}]},{"c":[{"v":"febrero-2015"},{"v":112}]},
    			           {"c":[{"v":"marzo-2015"},{"v":150}]},{"c":[{"v":"abril-2015"},{"v":200}]},
    			           {"c":[{"v":"mayo-2015"},{"v":567}]},{"c":[{"v":"junio-2015"},{"v":600}]}]};

chart2.options = {
	    title: 'Cantidad usuarios activos',
	    curveType: 'function',
	    legend: { position: 'bottom' }
    };

chart2.formatters = {};
  
$scope.chartUsuariosActivos = chart2;


var chart3 = {};
	
chart3.type = "PieChart";
    
chart3.cssStyle = "height:600px;";
    
chart3.data = {"cols":[{"label":"Carrera","type":"string"},{"label":"Cantidad usuarios","type":"number"}],
				"rows":[{"c":[{"v":"Ing. en Informática"},{"v":110}]},
				        {"c":[{"v":"Ing. en Alimentos"},{"v":24}]},
				        {"c":[{"v":"Ing. Civil"},{"v":26}]},
				        {"c":[{"v":"Ing. Química"},{"v":50}]},
				        {"c":[{"v":"Licenciatura en Sistemas"},{"v":34}]}]};

chart3.options = {
        title: 'Carreras por usuario'
};

chart3.formatters = {};  

$scope.chartCarrerasPorUsuario = chart3;


// Reportes

var barras;
var lineas;
var torta;


$scope.readyChartUsuariosActivos = function readyChartUsuariosActivos() {
	if( !($scope.chartWrapperLinea === undefined))
		lineas = $scope.chartWrapperLinea.getChart().getImageURI();  
};

$scope.readyChartGruposMayorActividad = function readyChartGruposMayorActividad() {
	if( !($scope.chartWrapperBarra === undefined))
		barras = $scope.chartWrapperBarra.getChart().getImageURI();  
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

$scope.exportar1 = function exportar1() {
		
		var columns = [
			{title: "Grupo", key: "id"},
			{title: "Discusión", key: "name"}, 
			{title: "Cantidad miembros", key: "country"}, 
			{title: "Cantidad respuestas", key: "email"}
		];
		
		var data = [
			{"id": "1", "name": "Grupo 1", "country": "Tanzania", "email": "abrown@avamba.info"},
			{"id": "2", "name": "Grupo 2", "country": "Kazakhstan", "email": "jjordan@agivu.com"},
			{"id": "3", "name": "Grupo 3", "country": "Madagascar", "email": "jdean@skinte.biz"}  
		];

		var doc = new jsPDF('p', 'pt');
		doc.setFontSize(20);
		doc.text(40, 40, "Reportes!!");

		doc.addImage(barras, 'PNG', -10, 60, 650, 140);
		
		doc.autoTable(columns, data, {margins: { right: 40, left: 40, top: 250, bottom: 40 }, startY: false });
		
		doc.save('Reporte.pdf');
	  };  
	  
};