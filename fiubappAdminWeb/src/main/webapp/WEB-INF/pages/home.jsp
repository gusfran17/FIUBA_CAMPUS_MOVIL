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
    <script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.6.0.js"></script>
	<script src="resources/js/services.js"></script>
	<script src="resources/js/controllers/LoginController.js"></script>
	<script src="resources/js/controllers/LogoutController.js"></script>
	<script src="resources/js/controllers/StudentController.js"></script>

	<script src="resources/js/controllers/ContentController.js"></script>
	<script src="resources/js/controllers/UserController.js"></script>
	<script src="resources/js/controllers/WorkspaceController.js"></script>
	<script src="resources/js/controllers/ContentModalController.js"></script>
	<script src="resources/js/controllers/PublicationModalController.js"></script>
	
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
        
    <ul ng-show="loggedUser.logged" class="menu" style="font-size : 23px;">
    	<li><a href="#/student">Alumnos</a></li>
    	
        <li><a href="#/users/{{loggedUser.userName}}">Grupos</a></li>
        <li><a href="#/users">Reportes</a></li>
        
        <li><a href="#/logout">Salir</a></li>
    </ul>
         
    <hr class="" />
    <div class="alert alert-error" ng-show="error">
    	{{errorMessage}}
	</div>
    
    <div ng-view></div>

</div>
</body>
</html>
