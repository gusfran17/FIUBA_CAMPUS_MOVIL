<form class="form-horizontal">
    <div class="control-group">
        <label class="control-label">Nombre de usuario</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="user.userName" placeholder="Nombre de usuario" required>
        </div>
    </div>
    
    <div class="control-group" ng-show="isEditable">
        <label class="control-label">Contraseña</label>

        <div class="controls">
            <input type="password" ng-model="user.password" placeholder="Contraseña" required>
        </div>
    </div>
    
    <div class="control-group" ng-show="isEditable">
        <label class="control-label">Confirmar Contraseña</label>

        <div class="controls">
            <input type="password" ng-model="passwordConfirm" placeholder="Confirmar Contraseña" required>
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label">Nombre</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="user.firstName" placeholder="Nombre" required min="2">
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label">Apellido</label>

        <div class="controls">
            <input type="text" ng-readonly="!isEditable" ng-model="user.lastName" placeholder="Apellido" required min="2">
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label">Fecha de Nacimiento</label>

        <div class="controls">
             <input type="text" ng-show="!isEditable" ng-readonly="!isEditable" ng-model="user.dateOfBirth" required>
             <input type="text" ng-show="isEditable" current-text="Hoy" clear-text="Limpiar" close-text="Cerrar" toggle-weeks-text="Semana" show-weeks="false" datepicker-popup="dd/MM/yyyy" ng-model="dateOfBirth" is-open="opened" date-disabled="disabled(date, mode)" ng-required="true" placeholder="Fecha de Nacimiento"/>
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label">Sexo</label>
        
        <div class="controls">
            <input type="radio" ng-readonly="!isEditable" ng-model="user.gender" value="MALE" required> Masculino 
   			<input type="radio" ng-readonly="!isEditable" ng-model="user.gender" value="FEMALE" required> Femenino 
   		</div>
    </div>
            
    <div class="control-group" ng-show="isEditable">
        <hr />
        <div class="controls">
            <button type="button" class="btn btn-primary" ng-disabled="!user.userName || !user.password || !user.firstName || !user.lastName || !dateOfBirth || !user.gender" ng-hide="editMode" ng-click="addUser(user)"><i class="icon-plus icon-white"></i> Registrar</button>
            <button type="button" ng-readonly="!isEditable" class="btn" ng-click="resetForm()"><i class="icon-remove"></i> Limpiar</button>
        </div>
    </div>
</form>
