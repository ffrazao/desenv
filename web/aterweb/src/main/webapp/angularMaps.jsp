<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Teste Angular Google Maps</title>
</head>
<body data-ng-app="teste" data-ng-controller="Ctrl">
	[{{map}}]
	<style>.angular-google-map-container { height: 400px; }</style>
	<ui-gmap-google-map center='map.center' zoom='map.zoom'></ui-gmap-google-map>

	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0-beta.5/angular.min.js" charset="UTF-8"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="resources/js/lodash.js" charset="UTF-8"></script>
	<script type="text/javascript" src="resources/js/angular-google-maps.js" charset="UTF-8"></script>
	<script type="text/javascript" src="resources/js/main-script.js" charset="UTF-8"></script>

	<script>
		var teste = angular.module('teste', [ "uiGmapgoogle-maps" ]);
		teste.config(function(uiGmapGoogleMapApiProvider) {
		    uiGmapGoogleMapApiProvider.configure({
		        //    key: 'your api key',
		        v: '3.9',
		        libraries: 'weather,geometry,visualization'
		    });
		});
		teste.controller('Ctrl', function($scope, $http, uiGmapGoogleMapApi) {

			$scope.map = {
				center : {
					latitude : 45,
					longitude : -73
				},
				zoom : 8
			};

			uiGmapGoogleMapApi.then(function(maps) {

		    });

		});
	</script>
</body>
</html>