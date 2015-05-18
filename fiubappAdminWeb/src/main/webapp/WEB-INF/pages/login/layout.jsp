<div ng-controller="LoginController">

	<form class="form-horizontal" ng-submit="login()">
	    <div class="control-group">
	        <label class="control-label">Nombre de usuario</label>
	
	        <div class="controls">
	            <input type="text" ng-model="username" placeholder="Nombre de usuario" required>
	        </div>
	    </div>
	    
	    <div class="control-group">
	        <label class="control-label">Contraseña</label>
	
	        <div class="controls">
	            <input type="password" ng-model="password" placeholder="Contraseña" required>
	        </div>
	    </div>
	                
	    <div class="control-group">
	        <hr />
	        <div class="controls">
	            <input type="submit" value="Ingresar" class="btn btn-primary"/><br>
	        </div>
	    </div>
	</form>
</div>