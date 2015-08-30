
var MyControllers = angular.module('MyControllers', [])

.controller('CtrlBoards', ['$scope', 'BoardsFactory', function($scope, BoardsFactory) {
	
	BoardsFactory.all().$promise.then(function(data){
	$scope.boardsObj = _([data.board]).flatten();
		if(typeof($scope.boardsObj[0])=="undefined")
			$scope.boardsObj=[];
		
	});

}])

.controller('CtrlLists', ['$scope', 'ListsFactory','$routeParams', function($scope, ListsFactory ,$routeParams) {
	
	
	ListsFactory.all({id : $routeParams.id}).$promise.then(function(data){
		$scope.listsObj = _([data.list]).flatten();
			if(typeof($scope.listsObj[0])=="undefined")
				$scope.listsObj=[];
			
		});

}])


.controller('CtrlCards', ['$scope', 'CardsFactory','$routeParams', function($scope, CardsFactory ,$routeParams) {
	

	CardsFactory.all({id : $routeParams.id}).$promise.then(function(data){
		$scope.cardsObj = _([data.card]).flatten();
			if(typeof($scope.cardsObj[0])=="undefined")
				$scope.cardsObj=[];
			
		});
	

}])

.controller('CtrlDetails', ['$scope', 'DetailsFactory','$routeParams', function($scope, DetailsFactory ,$routeParams) {
	
	
	DetailsFactory.all({id : $routeParams.id}).$promise.then(function(data){
		$scope.detailsObj = _([data.card]).flatten();
			if(typeof($scope.detailsObj[0])=="undefined")
				$scope.detailsObj=[];
			
		});
	

}])


.controller('CtrlLogin', ['$scope','LoginFactory',function($scope, LoginFactory ) {

	$scope.Auth = function(){
	LoginFactory.get({ username: $scope.username, password: $scope.password })
	.$promise.then( function( data) { 
	if( data.ok == "true" ) 
	location.hash = "/boards";
	});

	};
	}]);


		
	
