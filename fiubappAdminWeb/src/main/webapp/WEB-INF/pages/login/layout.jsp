<div ng-controller="LoginController">

	<form name="loginForm" class="form-horizontal" ng-submit="login()">
	    <div class="control-group" style="margin-left: 250px;">
	    
	        <label class="control-label">Nombre de usuario</label>
	
	        <div class="controls">
	            <input name="userName" type="text" ng-model="username" placeholder="Nombre de usuario" required oninvalid="this.setCustomValidity('Ingresá un nombre de usuario')"
    oninput="setCustomValidity('')"> 
	        </div>
	    </div>
	    
	    <div class="control-group" style="margin-left: 250px;">
	        <label class="control-label">Contraseña</label>
	
	        <div class="controls">
	            <input type="password" ng-model="password" placeholder="Contraseña" required oninvalid="this.setCustomValidity('Ingresá una contraseña')"
    oninput="setCustomValidity('')">
	        </div>
	    </div>
	                
	    <div class="control-group">
	        <hr />
	        <div class="controls" style="margin-left: 460px;">
	            <input type="submit" value="Ingresar" class="btn btn-primary"/><br>
	        </div>
	    </div>
	</form>
</div>