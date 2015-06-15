<!doctype html>
<html lang="es" ng-app="FiubappWebAdminApp">
<head>
    <meta charset="utf-8">
    <title>Fiubapp Administrador Web</title>
    <link rel="stylesheet" href="resources/css/app.css"/>
    <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.2.8/angular.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.2.8/angular-route.js"></script>
        
    <script src="resources/bootstrap/js/bootstrap.js"></script>
    <script src="resources/js/app.js"></script>
    <script src="resources/js/lib/ui-bootstrap-tpls-0.6.0.js"></script>
	<script src="resources/js/services.js"></script>
	<script src="resources/js/controllers/LoginController.js"></script>
	<script src="resources/js/controllers/LogoutController.js"></script>
	<script src="resources/js/controllers/StudentController.js"></script>
	<script src="resources/js/controllers/StateModalController.js"></script>
	<script src="resources/js/controllers/GroupController.js"></script>
	<script src="resources/js/controllers/GroupModalController.js"></script>
	<script src="resources/js/controllers/ReportAlumnosController.js"></script>
	<script src="resources/js/controllers/ReportDiscusionesController.js"></script>
	<script src="https://code.angularjs.org/1.0.8/i18n/angular-locale_es-ar.js"></script>
		
	<script src="resources/js/lib/ng-google-chart.js"></script>
	<script src="resources/js/lib/jquery-1.11.2.min.js"></script>
	<script src="resources/js/lib/jspdf.js"></script>
	<script src="resources/js/lib/jspdf.plugin.autotable.js"></script>
		
	<style type="text/css">
    	.bs-example{
    		margin: 20px;
    	}
	</style>
		
</head>
<body>

<div id="wrapper">
	
	<div align="center">
		<img src="resources/images/logo.png" align="middle">
	</div>
	
	<br>
	<div role="tabpanel">

		<ul class="nav nav-tabs" role="tablist" ng-show="loggedUser.logged" style="font-size : 20px;">
			<li id="studentTab" role="presentation" class="active"><a href="#/student" aria-controls="student" role="tab" data-toggle="tab">Alumnos</a></li>
		    <li id="groupTab" role="presentation"><a href="#/group" aria-controls="group" role="tab" data-toggle="tab">Grupos</a></li>
		    <li id="reportDiscusionesTab" role="presentation"><a href="#/reportDiscusiones" aria-controls="reportDiscusiones" role="tab" data-toggle="tab">Reporte Discusiones</a></li>
		    <li id="reportAlumnosTab" role="presentation"><a href="#/reportAlumnos" aria-controls="reportAlumnos" role="tab" data-toggle="tab">Reporte Alumnos</a></li>
		    <li id="logoutTab" role="presentation"><a href="#/logout" aria-controls="logout" role="tab" data-toggle="tab">Salir</a></li>
		</ul>
        
    	<div class="alert alert-error" ng-show="error">
    		{{errorMessage}}
		</div>
    
    	<div ng-view></div>
    </div>

</div>
</body>
</html>
