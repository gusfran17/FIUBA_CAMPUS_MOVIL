<div ng-controller="WorkspaceController">

<form name="form" class="form-horizontal">
	<div class="control-group">
        <label class="control-label">Nombre</label>

        <div class="controls">
        	<input type="text" ng-readonly="true" ng-model="folder.name" placeholder="Nombre">
        </div>
    </div>
   	<div class="control-group">
        <label class="control-label">Cantidad de elementos</label>

        <div class="controls">
        	<input type="text" ng-readonly="true" ng-model="folder.amountOfChildren" placeholder="Cantidad de elementos">
        </div>
    </div>
    
    <div class="control-group">
    	<div class="controls">
			<a class="btn btn-primary" ng-hide="folder.root" ng-href="#workspace/{{folder.parentId}}"><i class="icon-arrow-up icon-white"></i> Arriba</a>   	
         	<a class="btn btn-primary" ng-hide="create" ng-click="createFolder()"><i class="icon-plus icon-white"></i> Carpeta</a>
         	<div style="position:relative;" ng-hide="upload">
				<a class='btn btn-primary' href='javascript:;'>
					<i class="icon-plus icon-white"></i> Archivos
					<input type="file" style='position:absolute;z-index:2;top:0;left:0;filter: alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";opacity:0;background-color:transparent;color:transparent;' name="file_source" size="40"  ng-model-instant id="fileToUpload" multiple onchange="angular.element(this).scope().setFiles(this)" >
				</a>
				<span class='label label-info' id="upload-file-info"></span>
			</div>	
        </div>
    </div>
    
    <div class="control-group" ng-show="create">
        <label class="control-label">Nombre</label>

        <div class="controls">
        	<input type="text" ng-model="newFolderName" placeholder="Nombre">
        	<a class="btn btn-mini btn-success" ng-click="addFolder(newFolderName)"><i class="icon-plus icon-white"></i></a>
        	<a class="btn btn-mini btn-danger" ng-click="cancelFolder()"><i class="icon-remove icon-white"></i></a>
        </div>
    </div>
    
    <div class="control-group" ng-show="files.length && upload">
    	<label class="control-label">Archivos</label>
    	
    	<div class="controls" ng-repeat="file in files.slice(0)">
    		<input type="text" value="{{getFileProps(file)}}" ng-switch="file.size > 1024*1024">
    		<a class="btn btn-mini btn-primary" ng-click="open($index)"><i class="icon-pencil icon-white"></i></a>
    		<a class="btn btn-mini btn-danger" ng-click="removeFile($index)"><i class="icon-remove icon-white"></i></a>
    		<br>
    		<span ng-show="!contents[$index].ready">Hay datos sin completar!</span>
        </div>
        <div class="controls">
         	<a class="btn btn-success" ng-click="uploadFiles()"><i class="icon-arrow-up icon-white"></i> Cargar</a>
         	<a class="btn btn-danger" ng-click="cancelUpload()"><i class="icon-remove icon-white"></i> Cancelar</a>
         	<br>
    		<progress percent="dynamic"></progress>
        </div>
	</div>
</form>

<hr>
<div >
	<div class="row" >
		<div class="span2" ng-show="folder.children.length > 0" ng-repeat="child in folder.children">
			<img ng-src="resources/images/{{child.type}}.png" style="height: 75px;"></br>
			<a ng-show="child.type=='Folder'" href="#workspace/{{child.id}}">{{child.name}}</a>
			<a ng-show="child.type!='Folder'" href="#contents/{{child.id}}">{{child.name}}</a><br>
			<a ng-show="child.type!='Folder'" class="btn btn-mini btn-primary" ng-click="openModalPublication($index)"><i class="icon-pencil icon-white"></i> Publicacion</a>
		</div>
	</div>
</div>

</div>