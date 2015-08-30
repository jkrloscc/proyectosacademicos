var MyServices = angular.module('MyServices', [ 'ngResource' ]);

//Devuelve todos los Boards del Usuario Logueado
MyServices.factory('BoardsFactory', [ '$resource', function($resource) {
	return $resource('/TrelloRES/rest/boards', {}, {
		all :   {	method:'GET', params: {}},
		add :   {   method:'POST', params: {name: '@name'}},
		remove : { method: 'DELETE', params: {id: '@id'}}
	});
}]);

//Devuelve las listas de un Board específico
MyServices.factory('ListsFactory', [ '$resource', function($resource) {
	return $resource('/TrelloRES/rest/boards/:id', {}, {
		all : {	method:'GET',params: {id:'@id'}}
	});
}]);

//Devuelve todas las tarjetas de una Lista dada
MyServices.factory('CardsFactory', [ '$resource', function($resource) {
	return $resource('/TrelloRES/rest/lists/:id', {}, {
		all : {	method:'GET',params: {id:'@id'}}
	});
}]);

//Devuelve un unica Card especificada por su id
MyServices.factory('DetailsFactory', [ '$resource', function($resource) {
	return $resource('/TrelloRES/rest/cards/:id', {}, {
		all : {	method:'GET',params: {id:'@id'}}
	});
}]);

MyServices.factory('LoginFactory', ['$resource',
                            function($resource){
                            return $resource('/TrelloRES/rest/login', {}, {});
                            }]);

