/*
 ______      __   ___                      __                            
/\  _  \    /\ \ /\_ \                    /\ \              __           
\ \ \L\ \   \_\ \\//\ \      __   _ __    \ \ \      __  __/\_\  ____    
 \ \  __ \  /'_` \ \ \ \   /'__`\/\`'__\   \ \ \  __/\ \/\ \/\ \/\_ ,`\  
  \ \ \/\ \/\ \L\ \ \_\ \_/\  __/\ \ \/     \ \ \L\ \ \ \_\ \ \ \/_/  /_ 
   \ \_\ \_\ \___,_\/\____\ \____\\ \_\      \ \____/\ \____/\ \_\/\____\
    \/_/\/_/\/__,_ /\/____/\/____/ \/_/       \/___/  \/___/  \/_/\/____/                                                                       
 * 
 * @author Adler Luiz
 * @date 10/06/2014
 * @modify 11/06/2014
 */

var upload = angular.module("upload", ["angularFileUpload"]);

var UploadCtrl = function($scope, $rootScope, $http, $timeout, $upload) {    
        $scope.extensoesSuportadas = ["doc", "docx", "ppt", "pptx", "xls", "xlsx", "pdf", "txt", "jpg", "jpeg", "png", "gif", "zip", "rar"];
        
	$scope.fileReaderSupported = window.FileReader != null;
	$scope.uploadRightAway = true;
	$scope.changeAngularVersion = function() {
		window.location.hash = $scope.angularVersion;
		window.location.reload(true);
	};
	$scope.hasUploader = function(index) {
		return $scope.upload[index] != null;
	};
	$scope.abort = function(index) {
		$scope.upload[index].abort(); 
		$scope.upload[index] = null;
	};
	$scope.angularVersion = window.location.hash.length > 1 ? window.location.hash.substring(1) : '1.2.0';
	$scope.onFileSelect = function($files) {
            /*
                var tipoArquivo = $files[0].type.split('/')[1];
                console.log(tipoArquivo);
                var flag = 0;
                //console.log(tipoArquivo);
                angular.forEach($scope.extensoesSuportadas, function(data){
                    if(data == tipoArquivo) {
                        flag = 1;
                    }
                });
                
                if(!flag) {
                    console.log("Arquivo não suportado!");
                    return;
                }
              */  
		$scope.selectedFiles = [];
		$scope.progress = [];
		if ($scope.upload && $scope.upload.length > 0) {
                    for (var i = 0; i < $scope.upload.length; i++) {
                        if ($scope.upload[i] != null) {
                            $scope.upload[i].abort();
                        }
                    }
		}
		$scope.upload = [];
		$scope.uploadResult = [];
		$scope.selectedFiles = $files;
		$scope.dataUrls = [];
		for ( var i = 0; i < $files.length; i++) {
			var $file = $files[i];
			if (window.FileReader && $file.type.indexOf('image') > -1) {
				var fileReader = new FileReader();
				fileReader.readAsDataURL($files[i]);
				var loadFile = function(fileReader, index) {
                                    fileReader.onload = function(e) {
                                        $timeout(function() {
                                            $scope.dataUrls[index] = e.target.result;
                                        });
                                    }
				}(fileReader, i);
			}
			$scope.progress[i] = -1;
			if ($scope.uploadRightAway) {
				$scope.start(i);
			}
		}
	};
	
	$scope.start = function(index) {
		$scope.progress[index] = 0;
		$scope.errorMsg = null;
		//if ($scope.howToSend == 1) {
                if (1) {
			$scope.upload[index] = $upload.upload({
				url : baseUrl + 'arquivo/subir',
				//method: $scope.httpMethod,
                                method: 'POST',
				headers: {'my-header': 'my-header-value'},
				data : {
					myModel : $scope.myModel
				},
				/* formDataAppender: function(fd, key, val) {
					if (angular.isArray(val)) {
                        angular.forEach(val, function(v) {
                          fd.append(key, v);
                        });
                      } else {
                        fd.append(key, val);
                      }
				}, */
				/* transformRequest: [function(val, h) {
					console.log(val, h('my-header')); return val + 'aaaaa';
				}], */
				file: $scope.selectedFiles[index],
				fileFormDataName: 'myFile'
			}).then(function(response) {
				
                        $scope.uploadResult.push(response.data);
                            var elemento = {};
                            elemento['arquivo'] = { id: response.data.files[0].id };
                            elemento[$rootScope.fonteUpload] = { id: $rootScope.registro.id, '@class': $rootScope.registro['@class']};
                            
                            $rootScope.registro.arquivoList.push(elemento);
                            /*
                            if($rootScope.fonteUpload == "propriedadeRural") {
                                $rootScope.registro.arquivoList.push({ arquivo: { id: response.data.files[0].id }, 
                                                                       propriedadeRural: { id: $rootScope.registro.id } });
                            } else if($rootScope.fonteUpload == "pessoa") {
                                $rootScope.registro.arquivoList.push({ arquivo: { id: response.data.files[0].id }, 
                                                                       pessoa: { id: $rootScope.registro.id, 
                                                                               "@class" : $rootScope.registro['@class'] }});
                            }*/
                            
			}, function(response) {
				if (response.status > 0) $scope.errorMsg = response.status + ': ' + response.data;
			}, function(evt) {
				// Math.min is to fix IE which reports 200% sometimes
				$scope.progress[index] = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
			}).xhr(function(xhr){
				xhr.upload.addEventListener('abort', function() {console.log('Abortado ')}, false);
			});
		} else {
			var fileReader = new FileReader();
            fileReader.onload = function(e) {
		        $scope.upload[index] = $upload.http({
                            url: baseUrl + 'arquivo/subir',
                            headers: {'Content-Type': $scope.selectedFiles[index].type},
                            data: e.target.result
		        }).then(function(response) {
                                    $scope.uploadResult.push(response.data);
				}, function(response) {
                                    if (response.status > 0) $scope.errorMsg = response.status + ': ' + response.data;
				}, function(evt) {
					// Math.min is to fix IE which reports 200% sometimes
                                    $scope.progress[index] = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
				});
            }
	        fileReader.readAsArrayBuffer($scope.selectedFiles[index]);
		}
	};
	
	$scope.resetInputFile = function() {
		var elems = document.getElementsByTagName('input');
		for (var i = 0; i < elems.length; i++) {
			if (elems[i].type == 'file') {
				elems[i].value = null;
			}
		}
	};
};

upload.directive('upload', function($rootScope){
    return {
        restrict: 'E',
        templateUrl: 'resources/js/comps/upload/upload.html',
        link: function(scope, element, attrs) {
            scope.fonte = attrs.fonte;
            $rootScope.fonteUpload = scope.fonte;
        }
    };
});